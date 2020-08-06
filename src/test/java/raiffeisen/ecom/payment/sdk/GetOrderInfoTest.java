package raiffeisen.ecom.payment.sdk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.client.EcomClient;
import raiffeisen.ecom.payment.sdk.config.OrderInfoConfig;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.in.OrderInfo;
import raiffeisen.ecom.payment.sdk.model.in.additional.Status;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;
import raiffeisen.ecom.payment.sdk.model.out.OrderId;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class GetOrderInfoTest {
    // order was payed beforehand (amount=1000)
    private static String ORDER_ID;

    private static String BAD_ORDER_ID;

    private static String TEST_SECRET_KEY;

    private static EcomClient client;

    private static EcomClient clientUnauthorized;

    private static final OrderInfoConfig config = new OrderInfoConfig();

    @BeforeAll
    public static void getConfig() {
        ORDER_ID = config.getOrderId();
        BAD_ORDER_ID = config.getBadOrderId();
        TEST_SECRET_KEY = config.getSecretKey();
        client = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY);
        clientUnauthorized = new EcomClient(EcomClient.TEST_DOMAIN, TEST_SECRET_KEY + "-");
        assertNotEquals(null, ORDER_ID);
        assertNotEquals(null, BAD_ORDER_ID);
        assertNotEquals(null, TEST_SECRET_KEY);
        assertNotEquals(null, client);
        assertNotEquals(null, clientUnauthorized);
    }

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
