package raiffeisen.ecom.payment.sdk;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.client.EcomClient;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.in.RefundInfo;
import raiffeisen.ecom.payment.sdk.model.out.RefundRequest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RequestRefundTest {
    // order was payed beforehand
    private static final String ORDER_ID = "f857ca7a-bf6c-43be-b73b-33a2bfd3ea73";

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(0.01);

    private static final String TEST_SECRET_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
            "eyJzdWIiOiIwMDAwMDE2ODAyMDAwMDItODAyMDAwMDIiLCJqdGkiOiIwNTA4MjJlMi1kOTliLTQ" +
            "wYmEtYTU1Ny01NDZiYmYzN2FjNGUifQ.WVZSirCVgl1QdncZ8qZOrpGoB97qnRh7RT2f5UrNlko";

    private static final EcomClient client = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY);

    private static String getRefundId() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void refundRequestTest() {
        RefundRequest request = RefundRequest.creator().
                orderId(ORDER_ID).
                refundId(getRefundId()).
                amount(AMOUNT).create();

        try {
            RefundInfo refund = client.requestRefund(request);
            assertEquals(AMOUNT, refund.getAmount());
            assertEquals("SUCCESS", refund.getCode());
            String status = refund.getRefundStatus();
            assertTrue(status.equals("IN_PROGRESS") || status.equals("COMPLETED"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void badRefundRequestTest() {
        RefundRequest badRequest = RefundRequest.creator(). // without amount
                orderId(ORDER_ID).
                refundId(getRefundId()).
                create();

        try {
            RefundInfo refund = client.requestRefund(badRequest);
            fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof EcomException);
        }
    }

}
