package raiffeisen.ecom.payment.sdk.model.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

@Getter
@Value
@Builder(buildMethodName = "create", builderMethodName = "creator")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundRequest {
    String orderId;

    String refundId;

    @JsonProperty
    BigDecimal amount;
}
