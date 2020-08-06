package raiffeisen.ecom.payment.sdk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.client.EcomClient;
import raiffeisen.ecom.payment.sdk.config.RefundInfoConfig;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.in.RefundInfo;
import raiffeisen.ecom.payment.sdk.model.out.RefundRequest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetRefundInfoTest {
    // order was payed beforehand
    private static String ORDER_ID;

    private static String REFUND_ID;

    private static String BAD_REFUND_ID;

    private static BigDecimal AMOUNT;

    private static String TEST_SECRET_KEY;

    private static EcomClient client;

    private static String getRefundId() {
        return UUID.randomUUID().toString();
    }

    private static final RefundInfoConfig config = new RefundInfoConfig();

    @BeforeAll
    public static void getConfig() {
        ORDER_ID = config.getOrderId();
        BAD_REFUND_ID = config.getBadOrderId();
        AMOUNT = config.getAmount();
        TEST_SECRET_KEY = config.getSecretKey();
        client = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY);

        assertNotEquals(null, ORDER_ID);
        assertNotEquals(null, BAD_REFUND_ID);
        assertNotEquals(null, TEST_SECRET_KEY);
        assertNotEquals(null, AMOUNT);
        assertNotEquals(null, client);

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
