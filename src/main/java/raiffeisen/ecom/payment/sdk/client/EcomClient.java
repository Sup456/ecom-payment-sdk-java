package raiffeisen.ecom.payment.sdk.client;

import raiffeisen.ecom.payment.sdk.model.Response;
import raiffeisen.ecom.payment.sdk.model.out.OrderId;
import raiffeisen.ecom.payment.sdk.model.out.RefundInfo;
import raiffeisen.ecom.payment.sdk.web.ApacheWebClient;
import raiffeisen.ecom.payment.sdk.web.WebClient;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
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

    public Response getOrderInfo(final OrderId orderId) throws IOException {
        Vector<String> pathParameters = new Vector<>();
        pathParameters.add(orderId.getOrderId());
        return getRequester.request(ORDER_INFO_PATH, pathParameters, secretKey);
    }

    public Response requestRefund(final RefundInfo refundInfo) throws IOException {
        Vector<String> pathParameters = new Vector<>();
        pathParameters.add(refundInfo.getOrderId());
        pathParameters.add(refundInfo.getRefundId());
        return postRequester.request(REFUND_PATH, pathParameters, refundInfo.getAmount().toString(), secretKey);
    }

    public Response getRefundInfo(final RefundInfo refundInfo) throws IOException {
        Vector<String> pathParameters = new Vector<>();
        pathParameters.add(refundInfo.getOrderId());
        pathParameters.add(refundInfo.getRefundId());
        return postRequester.request(REFUND_INFO_PATH, pathParameters, refundInfo.getAmount().toString(), secretKey);
    }

    @Override
    public void close() throws IOException {
        webClient.close();
    }
}
