package hotel.management.system.be.application.account.vo;

import lombok.Value;

/**
 * 2FA Value-Object.
 */
@Value
public class TwoAuthAccountDataVO {
    String email;
    String secret;
}
