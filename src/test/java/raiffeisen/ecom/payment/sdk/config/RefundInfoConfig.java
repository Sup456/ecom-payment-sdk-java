package raiffeisen.ecom.payment.sdk.config;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class RefundInfoConfig implements PropertiesConfiguration {
    private final Properties properties = new Properties();

    public RefundInfoConfig() {
        try {
            InputStream propertiesFile = getClass().getClassLoader().getResourceAsStream("test.properties");
            properties.load(propertiesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getAmount() {
        return new BigDecimal(properties.getProperty("refund.info.amount"));
    }

    @Override
    public String getSecretKey() {
        return properties.getProperty("secret.key");
    }

    @Override
    public String getOrderId() {
        return properties.getProperty("refund.info.order.id");
    }

    @Override
    public String getBadOrderId() {
        return "badId";
    }
}
