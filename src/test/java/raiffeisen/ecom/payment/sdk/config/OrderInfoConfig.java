package raiffeisen.ecom.payment.sdk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OrderInfoConfig implements PropertiesConfiguration {
    private final Properties properties = new Properties();

    public OrderInfoConfig() {
        try {
            InputStream propertiesFile = getClass().getClassLoader().getResourceAsStream("test.properties");
            properties.load(propertiesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSecretKey() {
        return properties.getProperty("secret.key");
    }

    @Override
    public String getOrderId() {
        return properties.getProperty("order.info.order.id");
    }

    @Override
    public String getBadOrderId() {
        return "badId";
    }
}
