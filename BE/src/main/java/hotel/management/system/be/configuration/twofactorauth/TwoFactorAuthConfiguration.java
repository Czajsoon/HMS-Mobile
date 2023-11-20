package hotel.management.system.be.configuration.twofactorauth;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.IGoogleAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Two factor auth configuration.
 */
@Configuration
public class TwoFactorAuthConfiguration {

    /**
     * Google authenticator google authenticator.
     *
     * @return the google authenticator
     */
    @Bean
    public IGoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }
}
