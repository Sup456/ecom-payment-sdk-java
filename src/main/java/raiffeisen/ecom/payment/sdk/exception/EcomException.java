package raiffeisen.ecom.payment.sdk.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class EcomException extends Exception {
    String code;

    String message;

    @Override
    public String getMessage() {
        return code + ", " + message;
    }
}
