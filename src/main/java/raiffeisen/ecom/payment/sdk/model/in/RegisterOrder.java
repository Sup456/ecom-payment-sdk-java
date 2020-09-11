package raiffeisen.ecom.payment.sdk.model.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import raiffeisen.ecom.payment.sdk.model.in.additional.ExtensionAttributes;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class RegisterOrder {
    private BigDecimal amount;

    private String comment;

    private ExtensionAttributes extensionAttributes;

    private String OrderNumber;

    private String publicId;
}
