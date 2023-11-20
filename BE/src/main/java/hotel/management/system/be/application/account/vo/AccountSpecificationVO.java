package hotel.management.system.be.application.account.vo;

import hotel.management.system.be.entities.AccountEntity;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specyfikacja kont Value-Object.
 */
@Value
@Builder
public class AccountSpecificationVO {
    Specification<AccountEntity> specification;
    PageRequest pageRequest;
}
