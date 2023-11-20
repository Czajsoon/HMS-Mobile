package hotel.management.system.be.configuration.security;

import hotel.management.system.be.application.exception.BadRequestException;
import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import hotel.management.system.be.configuration.security.infrastructure.AccountDetailsServicePort;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * The type Account authentication provider.
 */
@Component
@AllArgsConstructor
public class AccountAuthenticationProvider implements AuthenticationProvider {

    private static final String USERNAME_CANNOT_BE_NULL = "Username cannot be null!";
    private static final String CREDENTIALS_CANNOT_BE_NULL = "Credentials cannot be null!";

    private final AccountDetailsServicePort accountDetailsServicePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();
        validNameAndCredentials(username, credentials);
        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();
        AccountPrincipal accountPrincipal = accountDetailsServicePort.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, accountPrincipal.getPassword()))
            throw new BadRequestException("Niepoprawne dane logowania.");

        return new UsernamePasswordAuthenticationToken(username, password, accountPrincipal.getAuthorities());
    }

    /**
     * Authenticate boolean.
     *
     * @param authentication the authentication
     * @param password       the password
     * @return the boolean
     */
    public boolean authenticate(Authentication authentication, String password) {
        String username = authentication.getName();
        AccountPrincipal accountPrincipal = accountDetailsServicePort.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, accountPrincipal.getPassword()))
            throw new BadRequestException("Niepoprawne dane logowania.");
        else
            return true;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void validNameAndCredentials(String username, Object credentials) {
        Assert.notNull(username, USERNAME_CANNOT_BE_NULL);
        Assert.notNull(credentials, CREDENTIALS_CANNOT_BE_NULL);
    }
}
