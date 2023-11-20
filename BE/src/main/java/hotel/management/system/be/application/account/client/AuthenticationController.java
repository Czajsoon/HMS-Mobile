package hotel.management.system.be.application.account.client;

import com.google.zxing.WriterException;
import hotel.management.system.be.application.account.dto.*;
import hotel.management.system.be.application.account.infrastructure.AccountFacade;
import hotel.management.system.be.application.account.infrastructure.TwoFactorAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Kontroler stworzony do obsługi authentykacji użytkowników.
 */
@RestController
@RequestMapping("services-rest/authenticate")
@CrossOrigin("http://192.168.0.12:19000")
@AllArgsConstructor
public class AuthenticationController {
    private final AccountFacade accountFacade;
    private final TwoFactorAuthenticationService twoFactorAuthentication;

    /**
     * odpowiedź logowania czyli jwt token.
     *
     * @param request obiekt zawierający login i hasło do zalogowania
     * @return the login response
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return accountFacade.login(request);
    }

    /**
     * Rejestracja użytkowników.
     *
     * @param request  Obiekt posiadający wszystkie potrzebne pola do rejestracji nowego użytkownika
     * @param response odpowiedź HTTP
     */
    @PostMapping("/admin/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        accountFacade.register(request, response);
    }

    /**
     * Pobiera konfigurację 2FA uzytkownika .
     *
     * @param rawToken token użytkownika
     * @return Konfiguracja 2FA użytkowanika
     */
    @GetMapping("/two-factor/config")
    public TwoFactorAccountConfig getTwoFactorConfig(@RequestHeader("Authorization") String rawToken) {
        return twoFactorAuthentication.getTwoFaAccountConfig(rawToken);
    }

    /**
     * Reset konfiguracji 2FA dla uzytkownika.
     *
     * @param accountId identyfikator użytkownika
     */
    @PutMapping("/admin/reset-two-factor/{accountId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void resetTwoFactor(@PathVariable String accountId) {
        twoFactorAuthentication.resetTwoFactor(accountId);
    }

    /**
     * Zmiana używania 2FA przez użytkownika.
     *
     * @param accountId identyfikator konta
     * @param value     wartośc do ustawienia true/false
     */
    @PutMapping("/admin/change-two-factor/{accountId}/{value}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void changeTwoFactor(@PathVariable String accountId, @PathVariable Boolean value) {
        twoFactorAuthentication.changeTwoFactor(accountId, value);
    }

    /**
     * Generowanie kodu QR do 2FA.
     *
     * @param rawToken token użytkownika
     * @return obrazek w tablicy byte[]
     * @throws WriterException Może wystapić wyjątek WriterException
     * @throws IOException     Może wystapić wyjątek IOException
     */
    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQRCode(@RequestHeader("Authorization") String rawToken) throws WriterException, IOException {
        return twoFactorAuthentication.generateQRCode(rawToken);
    }


    /**
     * Sprawdzanie poprawności kodu z aplikacji mobilnej.
     *
     * @param rawToken token uzytkownika
     * @param code     kod z aplikacji GoogleAuthenticator
     */
    @PutMapping(value = "/validate/{code}")
    public void validateCode(@RequestHeader("Authorization") String rawToken, @PathVariable int code) {
        twoFactorAuthentication.validateCode(rawToken, code);
    }

    /**
     * Zmiana hasła.
     *
     * @param rawToken              token użytkownika
     * @param changePasswordRequest obiekt z informacjami nowego hasła
     */
    @PostMapping("/changePassword")
    public void changePassword(@RequestHeader("Authorization") String rawToken, @RequestBody ChangePasswordRequest changePasswordRequest) {
        accountFacade.changePassword(changePasswordRequest, rawToken);
    }
}
