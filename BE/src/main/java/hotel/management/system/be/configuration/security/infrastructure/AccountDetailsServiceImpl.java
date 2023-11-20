package hotel.management.system.be.configuration.security.infrastructure;

import hotel.management.system.be.application.account.repository.AccountRepository;
import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@AllArgsConstructor
class AccountDetailsServiceImpl implements AccountDetailsServicePort {
    private final AccountRepository accountRepository;

    @Override
    public AccountPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, "Username cannot be null!");
        return accountRepository.findUserPrincipalByUsername(username);
    }
}
