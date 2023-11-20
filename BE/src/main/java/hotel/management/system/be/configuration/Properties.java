package hotel.management.system.be.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Properties.
 */
@Component
@Getter
public class Properties {

    @Value("${app.name:}")
    private String appName;
}
