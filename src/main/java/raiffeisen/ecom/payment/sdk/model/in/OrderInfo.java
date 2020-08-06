package raiffeisen.ecom.payment.sdk.model.in;

import lombok.Getter;
import lombok.Setter;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;

@Getter
@Setter
public class OrderInfo {
    String code;

    Transaction transaction;
}
