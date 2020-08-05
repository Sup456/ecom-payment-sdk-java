package raiffeisen.ecom.payment.sdk.client;

import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.Response;
import raiffeisen.ecom.payment.sdk.web.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class PostRequester extends Requester {
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

    public Response request(String url, final Vector<String> pathParameters, String body, final String secretKey) throws EcomException, IOException {

        String preparedUrl = prepareUrl(url, pathParameters);
        HashMap<String, String> headers = prepareHeaders(secretKey);

        Response response = webClient.request("POST", preparedUrl, headers, body);
        return responseOrThrow(response);
    }
}
