package hotel.management.system.be.application.account.infrastructure;

import hotel.management.system.be.application.account.dto.*;
import hotel.management.system.be.configuration.security.JwtTokenUtil;
import hotel.management.system.be.configuration.security.shared.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * Implementacja fasady Kont uzytkowników.
 */
@Service
@AllArgsConstructor
class AccountFacadeImpl implements AccountFacade {
    private final AccountAuthenticationService accountAuthenticationService;
    private final AccountService accountService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        return accountAuthenticationService.login(request);
    }

    @Override
    public void register(RegisterRequest request, HttpServletResponse response) {
        accountAuthenticationService.register(request, response);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, String rawToken) {
        accountAuthenticationService.changePassword(changePasswordRequest, rawToken);
    }

    @Override
    public AccountDetailDTO getAccountDetails(String token) {
        return accountService.getAccountDetails(
                jwtTokenUtil.getUsernameFromToken(TokenUtils.getTokenFromRawHeader(token))
        );
    }

    @Override
    public AccountDetailsPageDTO getAccountDetailsPage(AccountDetailsRequest request, String token) {
        return accountService.findAccounts(
                request,
                jwtTokenUtil.getUsernameFromToken(TokenUtils.getTokenFromRawHeader(token))
        );
    }

    @Override
    public void deleteAccount(String accountId) {
        accountService.deleteAccount(accountId);
    }


}
