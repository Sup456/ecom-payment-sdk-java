package raiffeisen.ecom.payment.sdk.model.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import raiffeisen.ecom.payment.sdk.model.in.additional.ExtensionAttributes;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterOrder {
    private final BigDecimal amount;

    private final String comment;

    private Map<String, String> extensionAttributes;

    private final String OrderNumber;

    private final String publicId;
}
