package hotel.management.system.be.configuration.security.infrastructure;

import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The interface Account details service port.
 */
public interface AccountDetailsServicePort extends UserDetailsService {
    AccountPrincipal loadUserByUsername(String username) throws UsernameNotFoundException;
}
