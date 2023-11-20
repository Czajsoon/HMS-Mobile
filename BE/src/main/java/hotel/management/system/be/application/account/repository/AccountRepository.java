package hotel.management.system.be.application.account.repository;

import hotel.management.system.be.application.account.dto.AccountDetailsPageDTO;
import hotel.management.system.be.application.account.dto.AccountDetailsRequest;
import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import hotel.management.system.be.entities.AccountEntity;
import org.springframework.stereotype.Repository;

/**
 * Repozytorium dostępne dla serwisów.
 */
@Repository
public interface AccountRepository {
    /**
     * Wyszukuje AccountPrincipal.
     *
     * @param username login użytkownika
     * @return obiekt AccountPrincipal
     */
    AccountPrincipal findUserPrincipalByUsername(String username);

    /**
     * wyszukuje użytkownika po loginie.
     *
     * @param username login użytkownika
     * @return encja bazodanowa AccountEntity
     */
    AccountEntity findAccountByUsername(String username);

    /**
     * Sprawdza czy konto o podanym loginie istnieje w systemie.
     *
     * @param username login uzytkownika
     */
    void validateAccountExistsWithUsername(String username);

    /**
     * Wyszukuje konto po Id.
     *
     * @param Id identyfikator użytkownika
     * @return encja bazodanowa AccountEntity
     */
    AccountEntity findAccountById(String Id);

    /**
     * Zapisuje konto do bazy danych.
     *
     * @param account encja bazodanowa do zapisu
     * @return zapisana encja bazodanowa AccountEntity
     */
    AccountEntity save(AccountEntity account);

    /**
     * Pobiera secret dla 2FA konta.
     *
     * @param username logijn użytkownika
     * @return secret 2FA
     */
    String getAccountSecret(String username);

    /**
     * Wyszukuje strone z szczegułami uzytkowników.
     *
     * @param accountDetailsRequest request z filtrami wyszukiwania
     * @param username              login
     * @return strona z danymi użytkowników
     */
    AccountDetailsPageDTO findAccountDetails(AccountDetailsRequest accountDetailsRequest, String username);

    /**
     * usuwanie konta.
     *
     * @param accountId identyfikator konta
     */
    void delete(String accountId);
}
