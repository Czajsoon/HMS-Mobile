package hotel.management.system.be.application.account.infrastructure;

import hotel.management.system.be.application.account.dto.AccountDetailDTO;
import hotel.management.system.be.application.account.dto.AccountDetailsPageDTO;
import hotel.management.system.be.application.account.dto.AccountDetailsRequest;
import hotel.management.system.be.application.account.repository.AccountRepository;
import hotel.management.system.be.configuration.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;

/**
 * Serwis dla kont uzytkowników.
 */
@Service
@AllArgsConstructor
class AccountService {
    private final AccountRepository accountRepository;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Pobiera szczegóły konta.
     *
     * @param username login konta
     * @return szczegóły konta
     */
    AccountDetailDTO getAccountDetails(String username) {
        return ACCOUNT_MAPPER.mapAccountDetail(accountRepository.findAccountByUsername(username));
    }

    /**
     * Wyszukiwanie kont użytkowników.
     *
     * @param request  request z filtrami
     * @param username login aktualnego użytkownika
     * @return dane użytkowników podzielone na strony
     */
    AccountDetailsPageDTO findAccounts(AccountDetailsRequest request, String username) {
        return accountRepository.findAccountDetails(request, username);
    }

    /**
     * Kasowanie konta.
     *
     * @param accountId identyfikator użytkownika
     */
    void deleteAccount(String accountId) {
        accountRepository.delete(accountId);
    }

    /**
     * Pobiera secret 2FA dla konta uzytkownika.
     *
     * @param token token użytkownika
     * @return secret 2FA
     */
    public String getAccountSecret(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return accountRepository.getAccountSecret(username);
    }
}
