package controllers;
import Entites.MoyenTransport;
import Entites.Reservation;
import Service.ServiceMoyenTransport;
import Service.ServiceReservation;
import com.itextpdf.layout.element.Table;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfDocument;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Cell;
import javafx.scene.control.TableColumn;

import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
//import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.property.HorizontalAlignment;

//import javax.swing.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Phrase;
//import com.itextpdf.text.Document;
//import com.itextpdf.layout.Document;
import java.io.FileOutputStream;
import java.io.FileOutputStream;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class AfficherReservationController {
    @FXML
    private TableColumn<Reservation, Date> coldate;

    @FXML
    private TableColumn<Reservation, String> colheure;

    @FXML
    private TableColumn<Reservation, Integer> colid;

    @FXML
    private TableColumn<Reservation, Integer> colid_transport;

    @FXML
    private TableColumn<Reservation, Integer> colid_user;

    @FXML
    private TableColumn<Reservation, String> colprix;
    @FXML
    private TableView<Reservation> tableview;
    private final ServiceReservation serr=new ServiceReservation();
    @FXML
    void  initialize()
    {

        try {
            List<Reservation> list=serr.readAll();
            ObservableList<Reservation> obers= FXCollections.observableList(list);
            tableview.setItems(obers);
            colid.setCellValueFactory(new PropertyValueFactory<>("id_reservation"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
            colheure.setCellValueFactory(new PropertyValueFactory<>("heure_reservation"));
            colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            colid_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            colid_transport.setCellValueFactory(new PropertyValueFactory<>("id_transport"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void supprimerreservation(javafx.event.ActionEvent event) throws SQLException {
        Reservation reservationSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (reservationSelectionne != null) {
            // Appel de votre service ou méthode pour supprimer le parking
            serr.delete(reservationSelectionne);
            // Rafraîchissez la table après la suppression
            initialize();
        } else {
            // Aucun parking sélectionné, affichez un message d'erreur ou avertissement
            System.out.println("Aucun Reservation sélectionné.");
        }

    }
    private final ServiceReservation serrr = new ServiceReservation();
    @FXML
    void modifierRes(javafx.event.ActionEvent event) throws SQLException  {
        Reservation reservationSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (reservationSelectionne!= null) {
            ouvrirInterfaceModificationn(reservationSelectionne);

        } else {
            System.out.println("Aucun reservation sélectionné.");
        }
    }
    private void ouvrirInterfaceModificationn(Reservation reservation) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReservation.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierReservationController modifierController = loader.getController();
            modifierController.setObservableListt(tableview.getItems());
            modifierController.initDonneesReservationn(reservation);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Reservation getreservationSelectionne() {
        return tableview.getSelectionModel().getSelectedItem();
    }

    @FXML
    void formatpdf(javafx.event.ActionEvent event) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            float y = 700; // Position verticale initiale
            float leading = -1.5f * 12; // Espacement entre les lignes (12 est la taille de la police)

            // Ajouter un titre au document
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y);
            contentStream.showText("Liste des réservations");
            contentStream.endText();
            y -= 20; // Espacement après le titre

            // Parcourir les réservations dans le TableView
            for (Reservation reservation : tableview.getItems()) {
                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("ID de la réservation: " + reservation.getId_reservation());
                contentStream.endText();
                y += leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Date de réservation: " + reservation.getDate_reservation());
                contentStream.endText();
                y += leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Heure: " + reservation.getHeure_reservation());
                contentStream.endText();
                y += leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("Prix: " + reservation.getPrix());
                contentStream.endText();
                y += leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("ID de l'utilisateur: " + reservation.getId_user());
                contentStream.endText();
                y += leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
                contentStream.showText("ID du transport: " + reservation.getId_transport());
                contentStream.endText();
                y += leading;
            }

            contentStream.close();

            document.save("C:/Users/user/IdeaProjects/TestConnexion/Reservations.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // Reservation reservation = new Reservation();
       // Reservation reservation = new Reservation(java.sql.Date.valueOf("2024-02-01"), "18:00", "1DT", 7,5);
       // Reservation reservation1 = new Reservation(java.sql.Date.valueOf("2024-02-01"), "19:00", "1DT", 7,5);
        // Appeler votre méthode pour générer le PDF
       // generatePDF(reservation);
    }

    /*private void generatePDF(Reservation reservation) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
           contentStream.setFont(PDType1Font.HELVETICA, 12);
           contentStream.beginText();
           contentStream.newLineAtOffset(100, 700);


            // Remplacer les champs avec les valeurs de votre objet Reservation
            contentStream.showText("ID de la réservation: " + reservation.getId_reservation());
            contentStream.newLine();
            contentStream.showText("Date de réservation: " + reservation.getDate_reservation());
            contentStream.newLine();
            contentStream.showText("Heure: " + reservation.getHeure_reservation());
            contentStream.newLine();
            contentStream.showText("Prix: " + reservation.getPrix());
            contentStream.newLine();
            contentStream.showText("ID de l'utilisateur: " + reservation.getId_user());
            contentStream.newLine();
            contentStream.showText("ID du transport: " + reservation.getId_transport());

            contentStream.endText();
            contentStream.close();

            document.save("C:/Users/user/IdeaProjects/TestConnexion/Reservation.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
  /*  private void generatePDF(Reservation reservation) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            float y = 700; // Position verticale initiale
            float leading = -1.5f * 12; // Espacement entre les lignes (12 est la taille de la police)

            // Afficher les informations de la réservation
            showText(contentStream, "ID de la réservation: " + reservation.getId_reservation(), 100, y);
            y += leading;
            showText(contentStream, "Date de réservation: " + reservation.getDate_reservation(), 100, y);
            y += leading;
            showText(contentStream, "Heure: " + reservation.getHeure_reservation(), 100, y);
            y += leading;
            showText(contentStream, "Prix: " + reservation.getPrix(), 100, y);
            y += leading;
            showText(contentStream, "ID de l'utilisateur: " + reservation.getId_user(), 100, y);
            y += leading;
            showText(contentStream, "ID du transport: " + reservation.getId_transport(), 100, y);

            contentStream.close();

            document.save("C:/Users/user/IdeaProjects/TestConnexion/Reservation.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

*/

/*
        //String filePath = "C:/Reservation.pdf";
         String filePath="C:/Users/user/IdeaProjects/TestConnexion/Reservation.pdf";
        // Vérifier si le répertoire parent existe et est accessible en écriture
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            System.out.println("Le répertoire parent n'existe pas.");
            return;
        }
        if (parentDir != null && !parentDir.canWrite()) {
            System.out.println("Le répertoire parent n'est pas accessible en écriture.");
            return;
        }*/
/*
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.open();

            PdfPTable pdfTable = new PdfPTable(tableview.getColumns().size());
            for (TableColumn<Reservation, ?> column : tableview.getColumns()) {
                PdfPCell cell = new PdfPCell(new Phrase(column.getText()));
                pdfTable.addCell(cell);
            }

            for (Reservation reservation : tableview.getItems()) {
                pdfTable.addCell(String.valueOf(reservation.getId_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getDate_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getHeure_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getPrix()));
                pdfTable.addCell(String.valueOf(reservation.getId_user()));
                pdfTable.addCell(String.valueOf(reservation.getId_transport()));
            }

            pdf.add(pdfTable);
            pdf.close();

            System.out.println("PDF généré avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
       /* String filePath = "C:/Users/user/IdeaProjects/TestConnexion/Reservation.pdf" ;
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            System.out.println("Le répertoire parent n'existe pas.");
            return;
        }
        if (parentDir != null && !parentDir.canWrite()) {
            System.out.println("Le répertoire parent n'est pas accessible en écriture.");
            return;
        }*/

       /* try {
           // PdfWriter writer = new PdfWriter(filePath);
         //   PdfDocument pdf = new PdfDocument(writer);
         //   Document document = new Document(pdf);

            OutputStream outputStream = new FileOutputStream(filePath);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            // pdf.open();
            Table pdfTable = new Table(tableview.getColumns().size());
            for (TableColumn<Reservation, ?> column : tableview.getColumns()) {
                Cell headerCell = new Cell().add(new Paragraph(column.getText()));
                pdfTable.addCell(headerCell);
            }

            for (Reservation reservation : tableview.getItems()) {
                pdfTable.addCell(String.valueOf(reservation.getId_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getDate_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getHeure_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getPrix()));
                pdfTable.addCell(String.valueOf(reservation.getId_user()));
                pdfTable.addCell(String.valueOf(reservation.getId_transport()));
            }
            pdf.open();
            document.add(pdfTable);
            pdf.close();
            System.out.println("PDF généré avec succès.");
           // pdf.open();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       /* try {
            OutputStream outputStream = new FileOutputStream(filePath);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
           // pdf.open();
            Table pdfTable = new Table(tableview.getColumns().size());
            for (TableColumn<Reservation, ?> column : tableview.getColumns()) {
                Cell headerCell = new Cell().add(new Paragraph(column.getText()));
                pdfTable.addCell(headerCell);
            }

            for (Reservation reservation : tableview.getItems()) {
                pdfTable.addCell(String.valueOf(reservation.getId_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getDate_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getHeure_reservation()));
                pdfTable.addCell(String.valueOf(reservation.getPrix()));
                pdfTable.addCell(String.valueOf(reservation.getId_user()));
                pdfTable.addCell(String.valueOf(reservation.getId_transport()));
            }

            document.add(pdfTable);
            document.close();
            System.out.println("PDF généré avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

