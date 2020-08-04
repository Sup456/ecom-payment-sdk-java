package raiffeisen.ecom.payment.sdk.model;

import lombok.Getter;
import lombok.Setter;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;

@Getter
@Setter
public class PaymentNotification {
    String event;

    Transaction transaction;
}
