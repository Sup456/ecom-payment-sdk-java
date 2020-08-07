package raiffeisen.ecom.payment.sdk.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import raiffeisen.ecom.payment.sdk.json.JsonParser;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;

@Getter
@RequiredArgsConstructor
public class PaymentNotification {
    private String event;

    private Transaction transaction;

    public static PaymentNotification fromJson(String body) throws JsonProcessingException {
        return JsonParser.objectFromJson(body, PaymentNotification.class);
    }
}
