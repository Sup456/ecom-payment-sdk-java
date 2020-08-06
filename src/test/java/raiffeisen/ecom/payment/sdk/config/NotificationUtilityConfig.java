package raiffeisen.ecom.payment.sdk.config;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class NotificationUtilityConfig implements PropertiesConfiguration {
    private final Properties properties = new Properties();

    public NotificationUtilityConfig() {
        try {
            InputStream propertiesFile = getClass().getClassLoader().getResourceAsStream("test.properties");
            properties.load(propertiesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getApiSignature() {
        return properties.getProperty("notification.utility.api.signature");
    }

    public String getBody() {
        return properties.getProperty("notification.utility.json.body");
    }

    public BigDecimal getAmount() {
        return new BigDecimal(properties.getProperty("notification.utility.amount"));
    }

    public String getPublicId() {
        return properties.getProperty("notification.utility.public.id");
    }

    public String getStatusValue() {
        return properties.getProperty("notification.utility.status.value");
    }

    public String getStatusDate() {
        return properties.getProperty("notification.utility.status.date");
    }

    @Override
    public String getSecretKey() {
        return properties.getProperty("secret.key");
    }

    @Override
    public String getOrderId() {
        return properties.getProperty("notification.utility.order.id");
    }

    @Override
    public String getBadOrderId() {
        return "badId";
    }
}
