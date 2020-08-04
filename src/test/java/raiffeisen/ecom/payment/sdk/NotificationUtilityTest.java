package raiffeisen.ecom.payment.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.model.PaymentNotification;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;
import raiffeisen.ecom.payment.sdk.utils.EcomUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificationUtilityTest {
    private final String API_SIGNATURE =
            "85f6af2b5edd3995f44c369397c90d1b320ea276c24731f6c36a8d7036deb1fb";

    private static final String BODY = "{\"event\":\"payment\",\"transaction\":{\"id\":58703,\"orderId\":\"ab9541ab-d083-495f-a3de-cb0fbd288739\",\"status\":{\"value\":\"SUCCESS\",\"date\":\"2020-08-05T00:38:11+03:00\"},\"paymentMethod\":\"acquiring\",\"paymentParams\":{\"rrn\":\"021800779531\",\"authCode\":\"072051\"},\"amount\":7,\"comment\":\"privetMir\",\"extra\":{\"lol\":\"qweqwe\"}}}";

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(7);

    private static final String PUBLIC_ID = "000001680200002-80200002";

    private static final String ORDER_ID = "ab9541ab-d083-495f-a3de-cb0fbd288739";

    private static final String STATUS_VALUE = "SUCCESS";

    private static final String STATUS_DATE = "2020-08-05T00:38:11+03:00";

    private static final String TEST_SECRET_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
            "eyJzdWIiOiIwMDAwMDE2ODAyMDAwMDItODAyMDAwMDIiLCJqdGkiOiIwNTA4MjJlMi1kOTliLTQ" +
            "wYmEtYTU1Ny01NDZiYmYzN2FjNGUifQ.WVZSirCVgl1QdncZ8qZOrpGoB97qnRh7RT2f5UrNlko";

    private static PaymentNotification notification;

    @BeforeAll
    public static void PaymentNotificationTest() {
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
