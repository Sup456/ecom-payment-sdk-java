package raiffeisen.ecom.payment.sdk.client;

import raiffeisen.ecom.payment.sdk.model.Response;
import raiffeisen.ecom.payment.sdk.web.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class PostRequester {
    private WebClient webClient;

    public PostRequester(WebClient client) {
        webClient = client;
    }

    public void setWebClient(WebClient client) {
        webClient = client;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public Response request(String url, final Vector<String> pathParameters, String body, final String secretKey) throws IOException {
        url = url.replace("?", pathParameters.get(0));
        if (url.contains("!")) {
            url = url.replace("!", pathParameters.get(1));
        }

        HashMap<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("charset", "UTF-8");
        headers.put("Authorization", "Bearer " + secretKey);

        return webClient.request("POST", url, headers, body);
    }
}
