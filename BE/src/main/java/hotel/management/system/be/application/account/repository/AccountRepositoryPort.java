package hotel.management.system.be.application.account.repository;

import hotel.management.system.be.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repozytorium bazodanowe.
 */
@Repository
interface AccountRepositoryPort extends JpaRepository<AccountEntity, UUID>, JpaSpecificationExecutor<AccountEntity> {
    /**
     * Znajdz użytkownika po loginie.
     *
     * @param username login użytkownika
     * @return opcjonalna encja bazodanowa
     */
    Optional<AccountEntity> findAccountEntityByUsername(String username);

    /**
     * Sprawdza czy konto o podanym loginie istnieje.
     *
     * @param username login
     * @return rezultat sprawdzenia
     */
    boolean existsAccountEntityByUsername(String username);

}
