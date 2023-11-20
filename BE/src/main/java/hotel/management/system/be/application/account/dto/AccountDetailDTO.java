package hotel.management.system.be.application.account.dto;

import lombok.*;

/**
 * Szczegóły konta uzytkownika.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailDTO {
    private String firstName;
    private String secondName;
    private String role;
    private Boolean isUsing2FA;
}
