package hotel.management.system.be.application.account.dto;

import hotel.management.system.be.entities.AccountRole;
import lombok.*;

/**
 * Obiekt przeznaczony do rejestracji nowych uzytkowników.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private AccountRole role;
    private String firstName;
    private String secondName;
    private String email;
    @Builder.Default
    private Boolean isUsing2FA = false;
}
