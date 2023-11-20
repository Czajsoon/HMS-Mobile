package hotel.management.system.be.application.account.infrastructure;

import hotel.management.system.be.application.account.dto.*;

import javax.servlet.http.HttpServletResponse;

/**
 * interfejs fasady dla Kont użytkowników.
 */
public interface AccountFacade {
    /**
     * logowanie uzytkownika.
     *
     * @param request obiekt logowania
     * @return obiekt zawierający token
     */
    LoginResponse login(LoginRequest request);

    /**
     * Rejestracja nowego uzytkownika.
     *
     * @param request  obiekt zawierający dane rejestracji
     * @param response odpowiedz HTTP
     */
    void register(RegisterRequest request, HttpServletResponse response);

    /**
     * Gets account details.
     *
     * @param token the token
     * @return the account details
     */
    AccountDetailDTO getAccountDetails(String token);

    /**
     * Gets account details page.
     *
     * @param request the request
     * @param token   the token
     * @return the account details page
     */
    AccountDetailsPageDTO getAccountDetailsPage(AccountDetailsRequest request, String token);

    /**
     * Delete account.
     *
     * @param accountId the account id
     */
    void deleteAccount(String accountId);

    /**
     * Zmiana hasła.
     *
     * @param changePasswordRequest obiekt zawierający dane zmiany hasła
     * @param rawToken              token użytkownika
     */
    void changePassword(ChangePasswordRequest changePasswordRequest, String rawToken);
}
