package raiffeisen.ecom.payment.sdk.model.in.additional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class Transaction {
    private long id;

    private String orderId;

    private Status status;

    private String paymentMethod;

    private PaymentParams paymentParams;

    private BigDecimal amount;

    private String comment;

    private Object extra;
}
