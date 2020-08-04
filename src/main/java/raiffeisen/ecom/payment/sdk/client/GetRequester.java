package raiffeisen.ecom.payment.sdk.client;

import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.Response;
import raiffeisen.ecom.payment.sdk.web.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class GetRequester {
    private WebClient webClient;

    private static final String AUTHORIZATION_ERROR = "Unauthorized";

    public GetRequester(WebClient client) {
        webClient = client;
    }

    public void setWebClient(WebClient client) {
        webClient = client;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public Response request(String url, final Vector<String> pathParameters, final String secretKey) throws EcomException, IOException {
        url = url.replace("?", pathParameters.get(0));
        if (url.contains("!")) {
            url = url.replace("!", pathParameters.get(1));
        }

        HashMap<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("charset", "UTF-8");
        headers.put("Authorization", "Bearer " + secretKey);

        Response response = webClient.request("GET", url, headers, null);
        if(response.getBody().equals(AUTHORIZATION_ERROR)) {
            EcomException e = new EcomException();
            e.setCode("AUTHORIZATION_FAILED");
            e.setMessage("Ответ сервера: " + AUTHORIZATION_ERROR);
            throw e;
        }
        return response;
    }
}
