package raiffeisen.ecom.payment.sdk.model.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;

@Getter
@RequiredArgsConstructor
public class OrderInfo {
    private String code;

    private Transaction transaction;
}
