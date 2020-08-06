package raiffeisen.ecom.payment.sdk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.client.EcomClient;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.in.RefundInfo;
import raiffeisen.ecom.payment.sdk.model.out.RefundRequest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetRefundInfoTest {
    // order was payed beforehand
    private static final String ORDER_ID = "834d6957-ff10-4850-b298-cf1f1aaf0a27";

    private static String REFUND_ID;

    private static final String BAD_REFUND_ID = "notExistingRefund";

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(0.01);

    private static final String TEST_SECRET_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
            "eyJzdWIiOiIwMDAwMDE2ODAyMDAwMDItODAyMDAwMDIiLCJqdGkiOiIwNTA4MjJlMi1kOTliLTQ" +
            "wYmEtYTU1Ny01NDZiYmYzN2FjNGUifQ.WVZSirCVgl1QdncZ8qZOrpGoB97qnRh7RT2f5UrNlko";

    private static final EcomClient client = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY);

    private static String getRefundId() {
        return UUID.randomUUID().toString();
    }

    @BeforeAll
    public static void refundPayment() {
        REFUND_ID = getRefundId();
        RefundRequest request = RefundRequest.creator().
                orderId(ORDER_ID).
                refundId(REFUND_ID).
                amount(AMOUNT).
                create();

        try {
            RefundInfo refund = client.requestRefund(request);
            assertEquals("SUCCESS", refund.getCode());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void getRefundInfoTest() {
        RefundRequest request = RefundRequest.creator().
                orderId(ORDER_ID).
                refundId(REFUND_ID).
                create();

        try {
            RefundInfo refund = client.getRefundInfo(request);
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
    public void getRefundInfoExceptionTest() {
        RefundRequest badRequest = RefundRequest.creator().
                orderId(ORDER_ID).
                refundId(BAD_REFUND_ID).
                create();

        try {
            client.getRefundInfo(badRequest);
            fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof EcomException);
        }
    }

}
