package Apis;

import Entites.Facture;
import Entites.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KonnectNetworkApi {
    private static final String key = "65e067f8fe0b604507660b93:XMPVTDe2I11V67An5TU9gR";
    private static final String walletID = "65e067f8fe0b604507660b97";
    private static final String initPaymentEndpoint = "https://api.preprod.konnect.network/api/v2/payments/init-payment";

    private final String getPaymentDetailsEndpoint = "https://api.preprod.konnect.network/api/v2/payments/";

    private static KonnectNetworkApi instance;

    private KonnectNetworkApi() {}

    public static KonnectNetworkApi getInstance() {
        if (instance == null) {
            instance = new KonnectNetworkApi();
        }
        return instance;
    }

    public String initPayment(Utilisateur utilisateur, Facture facture) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("receiverWalletId", walletID);
        requestData.put("token", "TND");
        requestData.put("amount", (int) Math.ceil(facture.getMontant_Facture() * 1000));
        requestData.put("type", "immediate");
        requestData.put("description", "payment description");
        requestData.put("acceptedPaymentMethods", new String[]{"wallet", "bank_card", "e-DINAR"});
        requestData.put("lifespan", 10);
        requestData.put("checkoutForm", true);
        requestData.put("addPaymentFeesToAmount", true);
        requestData.put("firstName", utilisateur.getNom());
        requestData.put("lastName", utilisateur.getPrenom());
        requestData.put("email", utilisateur.getMail_Util());
        requestData.put("theme", "light");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(initPaymentEndpoint))
                .header("Content-Type", "application/json")
                .header("x-api-key", key)
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(requestData)))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) {
                Map<String, Object> responseMap = new Gson().fromJson(httpResponse.body(), new TypeToken<Map<String, Object>>() {}.getType());
                return responseMap.containsKey("payUrl") ? responseMap.get("payUrl").toString() : "";
            } else {
                System.out.println("Error: " + httpResponse.statusCode() + ", " + httpResponse.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isPaymentSuccessful(String paymentUrl) {
        String paymentRef = null;
        String[] queryParams = paymentUrl.split("\\?")[1].split("&");

        for (String queryParam : queryParams) {
            String[] keyValue = queryParam.split("=");
            if (keyValue.length == 2 && "payment_ref".equals(keyValue[0])) {
                paymentRef = keyValue[1];
                break;
            }
        }3

        if (paymentRef == null || paymentRef.isEmpty()) {
            return false;
        }

        String paymentDetailsEndpoint = getPaymentDetailsEndpoint + paymentRef;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(paymentDetailsEndpoint))
                .header("Content-Type", "application/json")
                .header("x-api-key", key)
                .GET()
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) {
                Map<String, Object> paymentDetails = new Gson().fromJson(httpResponse.body(), new TypeToken<Map<String, Object>>() {}.getType());
                if (paymentDetails.containsKey("payment")) {
                    Map<String, Object> paymentInfo = (Map<String, Object>) paymentDetails.get("payment");
                    if (paymentInfo.containsKey("status") && paymentInfo.containsKey("transactions")) {
                        String paymentStatus = paymentInfo.get("status").toString();
                        List<Map<String, Object>> transactions = (List<Map<String, Object>>) paymentInfo.get("transactions");

                        for (Map<String, Object> transaction : transactions) {
                            if (transaction.containsKey("status")) {
                                String transactionStatus = transaction.get("status").toString();
                                if ("completed".equals(paymentStatus) && "success".equals(transactionStatus)) {
                                    return true;
                                }
                                else if ("pending".equals(paymentStatus) && "failed_payment".equals(transactionStatus)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}