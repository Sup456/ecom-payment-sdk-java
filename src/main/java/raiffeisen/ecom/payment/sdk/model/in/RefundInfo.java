package raiffeisen.ecom.payment.sdk.model.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class RefundInfo {
    private String code;

    private BigDecimal amount;

    private String refundStatus;
}
