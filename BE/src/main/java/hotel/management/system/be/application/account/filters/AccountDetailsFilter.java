package hotel.management.system.be.application.account.filters;

import hotel.management.system.be.entities.AccountRole;
import lombok.Builder;
import lombok.Value;

/**
 * Klasa zawierająca filtry dla wyszukiwania użytkowników.
 */
@Value
@Builder
public class AccountDetailsFilter {
    String id;
    String username;
    String firstName;
    String secondName;
    AccountRole role;
}
