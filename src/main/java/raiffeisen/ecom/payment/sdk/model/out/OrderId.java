package raiffeisen.ecom.payment.sdk.model.out;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class OrderId {
    String orderId;

    public OrderId(String orderId) {
        this.orderId = orderId;
    }
}
