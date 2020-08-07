package raiffeisen.ecom.payment.sdk.client;

import org.codehaus.plexus.util.StringUtils;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Requester {

    protected static String prepareUrl(String url, Vector<String> pathParameters) {
        String preparedUrl = url.replace("?", pathParameters.get(0));
        if (url.contains("!")) {
            preparedUrl = preparedUrl.replace("!", pathParameters.get(1));
        }
        return preparedUrl;
    }

    protected static Map<String, String> prepareHeaders(String secretKey) {
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("charset", "UTF-8");
        headers.put("Authorization", "Bearer " + secretKey);
        return headers;
    }

    protected static Response responseOrThrow(Response response) throws EcomException {
        String body = response.getBody();
        if(StringUtils.isEmpty(body) || body.charAt(0) != '{') {
            throw new EcomException("HttpCode = " + response.getCode(),
                    "Ответ сервера: " + response.getBody());
        }
        return response;
    }
}
