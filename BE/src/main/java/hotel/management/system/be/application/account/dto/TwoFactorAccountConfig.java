package hotel.management.system.be.application.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Obiekt przeznaczony do zwracania informacji o konfiguracji 2FA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorAccountConfig {
    private Boolean isUsing2FA;
    private Boolean is2FaConfigured;
}
