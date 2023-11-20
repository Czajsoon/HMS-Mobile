package hotel.management.system.be.application.account.dto;

import hotel.management.system.be.application.account.filters.AccountDetailsFilter;
import lombok.*;

/**
 * Request o listę użytkowników z filtrami.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsRequest {
    private Integer page;
    private Integer size;
    private AccountDetailsFilter filters;
}
