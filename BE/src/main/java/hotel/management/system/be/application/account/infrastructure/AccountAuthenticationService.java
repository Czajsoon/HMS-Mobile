package hotel.management.system.be.application.account.infrastructure;

import hotel.management.system.be.application.account.dto.ChangePasswordRequest;
import hotel.management.system.be.application.account.dto.LoginRequest;
import hotel.management.system.be.application.account.dto.LoginResponse;
import hotel.management.system.be.application.account.dto.RegisterRequest;
import hotel.management.system.be.application.account.repository.AccountRepository;
import hotel.management.system.be.configuration.security.AccountAuthenticationProvider;
import hotel.management.system.be.configuration.security.JwtTokenUtil;
import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import hotel.management.system.be.configuration.security.infrastructure.AccountDetailsServicePort;
import hotel.management.system.be.configuration.security.shared.TokenUtils;
import hotel.management.system.be.entities.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;

/**
 * Klasa serwisowa dla Kont użytkowników.
 */
@Service
@AllArgsConstructor
class AccountAuthenticationService {

    private final AccountAuthenticationProvider accountAuthenticationProvider;
    private final AccountDetailsServicePort accountDetailsServicePort;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * logowanie uzytkownika.
     *
     * @param request obiekt logowania
     * @return obiekt zawierający token
     */
    LoginResponse login(LoginRequest request) {
        authenticate(request.getLogin(), request.getPassword());
        final AccountPrincipal accountPrincipal = accountDetailsServicePort
                .loadUserByUsername(request.getLogin());

        final String token = jwtTokenUtil.generateToken(accountPrincipal);

        return new LoginResponse(token);
    }

    /**
     * Rejestracja nowego uzytkownika.
     *
     * @param request  obiekt zawierający dane rejestracji
     * @param response odpowiedz HTTP
     */
    void register(RegisterRequest request, HttpServletResponse response) {
        accountRepository.validateAccountExistsWithUsername(request.getUsername());
        accountRepository.save(ACCOUNT_MAPPER.map(request, passwordEncoder));
        response.setStatus(SC_CREATED);
    }

    /**
     * Zmiana hasła.
     *
     * @param changePasswordRequest obiekt zawierający dane zmiany hasła
     * @param rawToken              token użytkownika
     */
    void changePassword(ChangePasswordRequest changePasswordRequest, String rawToken) {
        String token = TokenUtils.getTokenFromRawHeader(rawToken);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (authenticateForPasswordChanging(username, changePasswordRequest.getPassword())) {
            AccountEntity account = accountRepository.findAccountByUsername(username);
            account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            accountRepository.save(account);
        } else {
            throw new BadCredentialsException("Błąd, złe hasło");
        }
    }

    private void authenticate(String username, String password) {
        accountAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private boolean authenticateForPasswordChanging(String username, String password) {
        AccountEntity account = accountRepository.findAccountByUsername(username);
        if(passwordEncoder.matches(password,account.getPassword()))
            return true;
        else
            return false;
    }
}
