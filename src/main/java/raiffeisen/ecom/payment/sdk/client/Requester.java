package raiffeisen.ecom.payment.sdk.client;

import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.Response;

import java.util.HashMap;
import java.util.Vector;

public class Requester {

    public static String prepareUrl(String url, Vector<String> pathParameters) {
        String preparedUrl = url.replace("?", pathParameters.get(0));
        if (url.contains("!")) {
            preparedUrl = preparedUrl.replace("!", pathParameters.get(1));
        }
        return preparedUrl;
    }

    public static HashMap<String, String> prepareHeaders(String secretKey) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("charset", "UTF-8");
        headers.put("Authorization", "Bearer " + secretKey);
        return headers;
    }

    public static Response responseOrThrow(Response response) throws EcomException {
        if(response.getBody() == null || response.getBody().length() == 0 || response.getBody().charAt(0) != '{') {
            EcomException e = new EcomException();
            e.setCode("HttpCode = " + response.getCode());
            e.setMessage("Ответ сервера: " + response.getBody());
            throw e;
        }
        return response;
    }
}
