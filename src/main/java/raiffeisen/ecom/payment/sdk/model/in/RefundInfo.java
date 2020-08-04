package raiffeisen.ecom.payment.sdk.model.in;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RefundInfo {
    String code;

    BigDecimal amount;

    String refundStatus;
}
