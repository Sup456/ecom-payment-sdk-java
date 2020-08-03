package raiffeisen.ecom.payment.sdk.model.out;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

@Getter
@Value
@Builder(buildMethodName = "create", builderMethodName = "creator")
public class RefundInfo {
    String orderId;

    String refundId;

    BigDecimal amount;
}
