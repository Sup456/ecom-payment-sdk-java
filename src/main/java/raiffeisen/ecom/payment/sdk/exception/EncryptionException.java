package raiffeisen.ecom.payment.sdk.exception;

public class EncryptionException extends RuntimeException {
    public EncryptionException(Throwable cause) {
        super(cause);
    }

    public EncryptionException(String cause) {
        super(cause);
    }
}
