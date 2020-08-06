package raiffeisen.ecom.payment.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.config.NotificationUtilityConfig;
import raiffeisen.ecom.payment.sdk.model.PaymentNotification;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;
import raiffeisen.ecom.payment.sdk.utils.EcomUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NotificationUtilityTest {
    private static String API_SIGNATURE;

    private static String BODY;

    private static BigDecimal AMOUNT;

    private static String PUBLIC_ID;

    private static String ORDER_ID;

    private static String STATUS_VALUE;

    private static String STATUS_DATE;

    private static String TEST_SECRET_KEY;

    private static PaymentNotification notification;

    private static final NotificationUtilityConfig config = new NotificationUtilityConfig();

    @BeforeAll
    public static void getConfig() {
        API_SIGNATURE = config.getApiSignature();
        BODY = config.getBody();
        AMOUNT = config.getAmount();
        PUBLIC_ID = config.getPublicId();
        ORDER_ID = config.getOrderId();
        STATUS_VALUE = config.getStatusValue();
        STATUS_DATE = config.getStatusDate();
        TEST_SECRET_KEY = config.getSecretKey();

        assertNotEquals(null, API_SIGNATURE);
        assertNotEquals(null, BODY);
        assertNotEquals(null, AMOUNT);
        assertNotEquals(null, PUBLIC_ID);
        assertNotEquals(null, ORDER_ID);
        assertNotEquals(null, STATUS_VALUE);
        assertNotEquals(null, STATUS_DATE);
        assertNotEquals(null, TEST_SECRET_KEY);

        try {
            notification = PaymentNotification.fromJson(BODY);
            Transaction transaction = notification.getTransaction();
            assertEquals(AMOUNT, transaction.getAmount());
            assertEquals(ORDER_ID, transaction.getOrderId());
            assertEquals(STATUS_VALUE, transaction.getStatus().getValue());
            assertEquals(STATUS_DATE, transaction.getStatus().getDate());
        }
        catch (JsonProcessingException e) {
            assert false;
        }
    }

    @Test
    public void checkSignatureFromJson() {
        assertTrue(EcomUtils.checkNotificationSignature(BODY, PUBLIC_ID, API_SIGNATURE, TEST_SECRET_KEY));
    }

    @Test
    public void checkSignatureFromPaymentNotification() {
        assertTrue(EcomUtils.checkNotificationSignature(notification, PUBLIC_ID, API_SIGNATURE, TEST_SECRET_KEY));
    }

    @Test
    public void checkSignatureFromFields() {
        assertTrue(EcomUtils.checkNotificationSignature(AMOUNT, PUBLIC_ID, ORDER_ID, STATUS_VALUE, STATUS_DATE,
                API_SIGNATURE, TEST_SECRET_KEY));
    }
}
