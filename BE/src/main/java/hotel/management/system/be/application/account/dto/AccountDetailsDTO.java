package hotel.management.system.be.application.account.dto;


import hotel.management.system.be.entities.AccountRole;
import lombok.*;

/**
 * Szczegóły konta uzytkownika.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsDTO {
    private String id;
    private String username;
    private String firstName;
    private String secondName;
    private AccountRole role;
    private Boolean isUsing2FA;
}
