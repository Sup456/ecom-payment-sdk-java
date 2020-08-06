package raiffeisen.ecom.payment.sdk.model.in.additional;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Transaction {
    long id;

    String orderId;

    Status status;

    String paymentMethod;

    PaymentParams paymentParams;

    BigDecimal amount;

    String comment;

    Object extra;
}
