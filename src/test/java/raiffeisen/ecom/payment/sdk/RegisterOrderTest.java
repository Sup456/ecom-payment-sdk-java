package raiffeisen.ecom.payment.sdk;

import org.junit.jupiter.api.Test;
import raiffeisen.ecom.payment.sdk.client.EcomClient;
import raiffeisen.ecom.payment.sdk.exception.EcomException;
import raiffeisen.ecom.payment.sdk.model.Response;
import raiffeisen.ecom.payment.sdk.model.in.RegisterOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class RegisterOrderTest {

    private static String getOrderNumber() { return UUID.randomUUID().toString();}

    @Test
    public void basicTest() {
        HashMap<String, String> map = new HashMap<String,String>();
        map.put("prop1", "string");
        map.put("prop2", "string");

        RegisterOrder registerOrder = new RegisterOrder(BigDecimal.valueOf(5),
                "basicComment",
                map,
                getOrderNumber(),
                "000001680200002-80200002");

        EcomClient client = new EcomClient(EcomClient.TEST_DOMAIN, "");

        try {
            Response response = client.registerOrder(registerOrder);
            System.out.println(response.getBody());
        }
        catch (EcomException a) {
            assert false;
        }
        catch (IOException b) {
            System.out.println(b.getMessage());
            assert false;
        }

    }
}
