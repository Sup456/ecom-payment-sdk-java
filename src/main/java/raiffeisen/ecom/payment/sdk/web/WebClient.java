package raiffeisen.ecom.payment.sdk.web;

import raiffeisen.ecom.payment.sdk.model.Response;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

public interface WebClient extends Closeable {
    Response request(String method, String url, Map<String, String> headers, String entity) throws IOException;
}
