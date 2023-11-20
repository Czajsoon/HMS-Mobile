package hotel.management.system.be.application.account.dto;

import lombok.*;

/**
 * obiekt przeznaczony do logowania.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String login;
    private String password;
}
