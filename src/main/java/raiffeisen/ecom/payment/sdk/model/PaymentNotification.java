package raiffeisen.ecom.payment.sdk.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import raiffeisen.ecom.payment.sdk.json.JsonParser;
import raiffeisen.ecom.payment.sdk.model.in.additional.Transaction;

@Getter
@Setter
public class PaymentNotification {
    String event;

    Transaction transaction;

    public static PaymentNotification fromJson(String body) throws JsonProcessingException {
        return JsonParser.objectFromJson(body, PaymentNotification.class);
    }
}
