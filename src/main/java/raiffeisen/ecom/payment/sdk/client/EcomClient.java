package raiffeisen.ecom.payment.sdk.client;

import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.json.JsonBuilder;
import raiffeisen.ecom.payment.sdk.json.JsonParser;
import raiffeisen.ecom.payment.sdk.model.Response;
import raiffeisen.ecom.payment.sdk.model.in.OrderInfo;
import raiffeisen.ecom.payment.sdk.model.in.RefundInfo;
import raiffeisen.ecom.payment.sdk.model.out.OrderId;
import raiffeisen.ecom.payment.sdk.model.out.RefundRequest;
import raiffeisen.ecom.payment.sdk.web.ApacheWebClient;
import raiffeisen.ecom.payment.sdk.web.WebClient;

import java.io.Closeable;
import java.io.IOException;
import java.util.Vector;

public class EcomClient implements Closeable {
    public static final String TEST_DOMAIN = "https://test.ecom.raiffeisen.ru";
    public static final String PRODUCTION_DOMAIN = "https://e-commerce.raiffeisen.ru";

    private static final String ORDER_INFO_PATH = "/api/payments/v1/orders/?/transaction";
    private static final String REFUND_PATH = "/api/payments/v1/orders/?/refunds/!";
    private static final String REFUND_INFO_PATH = "/api/payments/v1/orders/?/refunds/!";

    private final String domain;

    private final String secretKey;

    private WebClient webClient;

    private final PostRequester postRequester;
    private final GetRequester getRequester;

    public void setWebClient(WebClient client) {
        webClient = client;
        postRequester.setWebClient(webClient);
        getRequester.setWebClient(webClient);
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public EcomClient(String domain, String secretKey) {
        this.domain = domain;
        this.secretKey = secretKey;
        this.webClient = new ApacheWebClient();
        this.postRequester = new PostRequester(this.webClient);
        this.getRequester = new GetRequester(this.webClient);
    }

    public EcomClient(String domain, String secretKey, WebClient customWebClient) {
        this.domain = domain;
        this.secretKey = secretKey;
        this.webClient = customWebClient;
        this.postRequester = new PostRequester(this.webClient);
        this.getRequester = new GetRequester(this.webClient);
    }

    public OrderInfo getOrderInfo(final OrderId orderId) throws EcomException, IOException {
        Vector<String> pathParameters = new Vector<>();
        pathParameters.add(orderId.getOrderId());
        Response tempResponse = getRequester.request(domain + ORDER_INFO_PATH, pathParameters, secretKey);
        return JsonParser.getObjectOrThrow(tempResponse.getBody(), OrderInfo.class, EcomException.class);
    }

    public RefundInfo requestRefund(final RefundRequest refundRequest) throws EcomException, IOException {
        Vector<String> pathParameters = new Vector<>();
        pathParameters.add(refundRequest.getOrderId());
        pathParameters.add(refundRequest.getRefundId());
        Response tempResponse = postRequester.request(domain + REFUND_PATH, pathParameters, JsonBuilder.fromObject(refundRequest), secretKey);
        return JsonParser.getObjectOrThrow(tempResponse.getBody(), RefundInfo.class, EcomException.class);
    }

    public RefundInfo getRefundInfo(final RefundRequest refundRequest) throws EcomException, IOException {
        Vector<String> pathParameters = new Vector<>();
        pathParameters.add(refundRequest.getOrderId());
        pathParameters.add(refundRequest.getRefundId());
        Response tempResponse = postRequester.request(domain + REFUND_INFO_PATH, pathParameters, null, secretKey);
        return JsonParser.getObjectOrThrow(tempResponse.getBody(), RefundInfo.class, EcomException.class);
    }

    @Override
    public void close() throws IOException {
        webClient.close();
    }
}
