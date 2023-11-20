package hotel.management.system.be.application.account.dto;

import lombok.*;

import java.util.List;

/**
 * Strona szczegółów uzytkowników
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsPageDTO {
    private Long totalElements;
    private Integer totalPages;
    private List<AccountDetailsDTO> accounts;
}
