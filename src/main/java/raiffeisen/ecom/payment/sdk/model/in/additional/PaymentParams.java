package raiffeisen.ecom.payment.sdk.model.in.additional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentParams {
    String rrn;

    String authCode;
}
