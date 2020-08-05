package raiffeisen.ecom.payment.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Hex;
import raiffeisen.ecom.payment.sdk.exception.EncryptionException;
import raiffeisen.ecom.payment.sdk.json.JsonParser;
import raiffeisen.ecom.payment.sdk.model.PaymentNotification;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EcomUtils {

    private static final String SHA_256_ALGORITHM = "HmacSHA256";
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final String SEPARATOR = "|";

    public static boolean checkNotificationSignature(String jsonBody, String publicId, String headerSignature, String secretKey) throws EncryptionException {
        String hash = encrypt(joinFields(jsonBody, publicId),secretKey);
        return hash.equals(headerSignature);
    }

    public static boolean checkNotificationSignature(PaymentNotification notification, String publicId, String headerSignature, String secretKey) throws EncryptionException {
        String data = joinFields(notification.getTransaction(), publicId);
        String hash = encrypt(data, secretKey);
        return hash.equals(headerSignature);
    }

    public static boolean checkNotificationSignature(
            BigDecimal amount,
            String publicId,
            String orderId,
            String statusValue,
            String statusDate,
            String headerSignature,
            String secretKey) throws EncryptionException {

        String data = joinFields(amount.toString(), publicId, orderId, statusValue, statusDate);
        String hash = encrypt(data, secretKey);
        return hash.equals(headerSignature);
    }


    public static String encrypt(String data, String key) throws EncryptionException {
        if (data.isEmpty()) {
            return "";
        }
        try {
            SecretKeySpec secret = new SecretKeySpec(key.getBytes(ENCODING), SHA_256_ALGORITHM);
            Mac mac = Mac.getInstance(SHA_256_ALGORITHM);
            mac.init(secret);
            byte[] encoded = mac.doFinal(data.getBytes(ENCODING));
            return Hex.encodeHexString(encoded);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new EncryptionException(e);
        }

    }

    public static String joinFields(String jsonString, String publicId) {
        try {
            Transaction transaction = JsonParser.objectFromJson(jsonString, PaymentNotification.class).getTransaction();
            return joinFields(transaction, publicId);
        }
        catch (JsonProcessingException e) {
            return "";
        }
    }

    public static String joinFields(String amount, String publicId, String orderId, String statusValue, String statusDate) {
        return amount +
                SEPARATOR +
                publicId +
                SEPARATOR +
                orderId +
                SEPARATOR +
                statusValue +
                SEPARATOR +
                statusDate;
    }

    public static String joinFields(Transaction transaction, String publicId) {
        return joinFields(transaction.getAmount().toString(),
                publicId,
                transaction.getOrderId(),
                transaction.getStatus().getValue(),
                transaction.getStatus().getDate());
    }
}
