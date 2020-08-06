package raiffeisen.ecom.payment.sdk.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EcomException extends Exception {
    String code;

    String message;

    @Override
    public String getMessage() {
        return code + ", " + message;
    }
}
