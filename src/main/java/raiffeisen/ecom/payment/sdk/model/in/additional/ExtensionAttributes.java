package raiffeisen.ecom.payment.sdk.model.in.additional;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ExtensionAttributes {
    String additionalProp1;

    String additionalProp2;

    String additionalProp3;
}
