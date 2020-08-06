package raiffeisen.ecom.payment.sdk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.client.EcomClient;
import raiffeisen.ecom.payment.sdk.config.RequestRefundConfig;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.in.RefundInfo;
import raiffeisen.ecom.payment.sdk.model.out.OrderId;
import raiffeisen.ecom.payment.sdk.model.out.RefundRequest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RequestRefundTest {
    // order was payed beforehand
    private static String ORDER_ID;

    private static BigDecimal AMOUNT;

    private static String TEST_SECRET_KEY;

    private static EcomClient client;

    private static String getRefundId() {
        return UUID.randomUUID().toString();
    }

    private static RequestRefundConfig config = new RequestRefundConfig();

    @BeforeAll
    public static void getConfig() {
        ORDER_ID = config.getOrderId();
        AMOUNT = config.getAmount();
        TEST_SECRET_KEY = config.getSecretKey();
        client = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY);

        assertNotEquals(null, ORDER_ID);
        assertNotEquals(null, AMOUNT);
        assertNotEquals(null, TEST_SECRET_KEY);
        assertNotEquals(null, client);
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
            client.requestRefund(badRequest);
            fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof EcomException);
        }
    }

}
