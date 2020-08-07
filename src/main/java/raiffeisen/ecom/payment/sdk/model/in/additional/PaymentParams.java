package raiffeisen.ecom.payment.sdk.model.in.additional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentParams {
    private String rrn;

    private String authCode;
}
