package raiffeisen.ecom.payment.sdk;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.client.EcomClient;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.in.OrderInfo;
import raiffeisen.ecom.payment.sdk.model.in.additional.PaymentParams;
import raiffeisen.ecom.payment.sdk.model.in.additional.Status;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;
import raiffeisen.ecom.payment.sdk.model.out.OrderId;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetOrderInfoTest {
    // order was payed beforehand (amount=1000)
    private static final String ORDER_ID = "06572ff4-a357-41d1-a1a9-6eb2ebb506ab";

    private static final String BAD_ORDER_ID = "notExistingId";

    private static final String TEST_SECRET_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
            "eyJzdWIiOiIwMDAwMDE2ODAyMDAwMDItODAyMDAwMDIiLCJqdGkiOiIwNTA4MjJlMi1kOTliLTQ" +
            "wYmEtYTU1Ny01NDZiYmYzN2FjNGUifQ.WVZSirCVgl1QdncZ8qZOrpGoB97qnRh7RT2f5UrNlko";

    private static final EcomClient client = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY);

    private static final EcomClient clientUnauthorized = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY + "-");

    @Test
    public void orderInfoTest() {
        OrderId orderId = new OrderId(ORDER_ID);

        try {
            OrderInfo orderInfo = client.getOrderInfo(orderId);
            Transaction transaction = orderInfo.getTransaction();
            Status status = transaction.getStatus();
            assertNotNull(transaction.getPaymentParams());
            assertEquals(ORDER_ID, transaction.getOrderId());
            assertEquals("acquiring", transaction.getPaymentMethod());
            assertEquals("SUCCESS", orderInfo.getCode());
            assertEquals(new BigDecimal(1000), transaction.getAmount());
            assertEquals("SUCCESS", status.getValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void unauthorizedTest() {
        OrderId orderId = new OrderId(ORDER_ID);

        try {
            clientUnauthorized.getOrderInfo(orderId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof EcomException);
            assertTrue(e.getMessage().contains("Unauthorized"));
        }
    }

    @Test
    public void getBadOrderInfoTest() {
        OrderId orderId = new OrderId(BAD_ORDER_ID);

        try {
            OrderInfo orderInfo = client.getOrderInfo(orderId);
            assertEquals("NOT_FOUND", orderInfo.getTransaction().getStatus().getValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
