package raiffeisen.ecom.payment.sdk.model.out;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

@Getter
@Value
@Builder (buildMethodName = "create", builderMethodName = "creator")
public class CreatePaymentForm {
    String publicId;

    BigDecimal amount;

    String orderId;

    String successUrl;

    String failUrl;
}
