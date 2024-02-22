package controllers;

import Entites.Parking;
import Entites.Reservation;
import Service.ServiceReservation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
public class FaireReservationController {
    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private Spinner<Integer> spinnerheuredebut;

    @FXML
    private TextField txtadresse;

    @FXML
    private TextField txtnom;
    private final ServiceReservation ser = new ServiceReservation();

    @FXML
    public void initialize() {

        spinnerheuredebut.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
    }

    private Parking parkingSelectionne;
    private Reservation reservation;
    public void initDonneesReservation(Parking parking) {
        parkingSelectionne = parking;
        txtnom.setText(parking.getNompark());
        txtadresse.setText(parking.getAdressepark());

    }

    @FXML
    void debutReservation(ActionEvent event) {
        try {
            int heuredebut = spinnerheuredebut.getValue();
            int idUtilisateur = 3;
            if (parkingSelectionne != null) {
                Reservation nouvelleReservation = new Reservation(idUtilisateur, parkingSelectionne, heuredebut, 0);
                ser.ajouter(nouvelleReservation);
                reservation = nouvelleReservation;
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Confirmation");
                alert1.setContentText("Réservation effectuée");
                alert1.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Aucun parking sélectionné.");
                alert.showAndWait();
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void finReservation(ActionEvent event) {
        try {

            int idUtilisateur = 3;
            int heuredebut = spinnerheuredebut.getValue();
            int idres = reservation.getIdres();
            int heuredebutReservation = reservation.getHeuredebut();
            int heurefinReservation = reservation.getHeurefin();

            int differenceHeures=0;

            if (heuredebutReservation > heurefinReservation) {
                differenceHeures = (heurefinReservation - heuredebutReservation + 24) % 24;
            } else if (heuredebutReservation < heurefinReservation){
                differenceHeures = heurefinReservation - heuredebutReservation;
            }
            String montant = differenceHeures + "D";
            if (parkingSelectionne != null) {
                LocalDateTime heureFinSysteme = LocalDateTime.now();
                int heureFin = heureFinSysteme.getHour();
                Reservation nouvelleReservation = new Reservation(idres,idUtilisateur, parkingSelectionne, heuredebut, heureFin);
                ser.update(nouvelleReservation);
                if(generateQRCode(nouvelleReservation, montant)){
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Confirmation");
                alert1.setContentText("Fin Réservation");
                alert1.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Aucun parking sélectionné.");
                alert.showAndWait();
            }
        } }catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean generateQRCode(Reservation reservation, String montant) {
        String reservationInfo = "ID: " + reservation.getIdres() +
                "\nUtilisateur: " + reservation.getIdutilisateur() +
                "\nParking: " + reservation.getParking().getIdpark() +
                "\nHeure debut: " + reservation.getHeuredebut() +
                "\nHeure fin: " + reservation.getHeurefin() +
                "\nMontant: " + montant;
        System.out.println("Contenu de reservationInfo : " + reservationInfo);
        try {
            reservationInfo = new String(reservationInfo.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width = 300;
        int height = 300;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(reservationInfo, BarcodeFormat.QR_CODE, width, height);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Image qrCodeImage = matrixToImage(bitMatrix);
        qrCodeImageView.setImage(qrCodeImage);

        System.out.println("Le QR code a été généré avec succès.");

        return true;
    }

    private Image matrixToImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setArgb(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return writableImage;
    }
}
