package hotel.management.system.be.application.account.dto;


import lombok.*;

/**
 * Obiekt zmiany hasła użytkownika.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private String password;
    private String newPassword;
}
