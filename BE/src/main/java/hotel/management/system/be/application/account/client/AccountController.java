package hotel.management.system.be.application.account.client;

import hotel.management.system.be.application.account.dto.AccountDetailDTO;
import hotel.management.system.be.application.account.dto.AccountDetailsPageDTO;
import hotel.management.system.be.application.account.dto.AccountDetailsRequest;
import hotel.management.system.be.application.account.filters.AccountDetailsFilter;
import hotel.management.system.be.application.account.infrastructure.AccountFacade;
import hotel.management.system.be.entities.AccountRole;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Kontroler dla kont użytkowników.
 */
@RestController
@RequestMapping("services-rest/account/")
@AllArgsConstructor
public class AccountController {

    private final AccountFacade accountFacade;

    /**
     * Ta metoda pobiera szczegóły konta przypisanego do tokena
     *
     * @param token token JWT
     * @return szczegóły konta
     */
    @GetMapping("details")
    public AccountDetailDTO getAccountDetails(
            @RequestHeader(name = "Authorization") String token
    ) {
        return accountFacade.getAccountDetails(token);
    }

    /**
     * Pobiera listę kont uzytkowników podzieloną na strony bez konta właściciela tokena.
     *
     * @param id         parametr wyszukiwania po identyfikatorze użytkownika
     * @param username   parametr wyszukiwania po loginie
     * @param firstName  parametr wyszukiwania po imieniu
     * @param secondName parametr wyszukiwania po nazwisku
     * @param role       parametr wyszukiwania po roli w systemie
     * @param page       parametr do wyboru strony
     * @param size       parametr do wyboru ilości rekordów na stronie
     * @param token      token użytkownika
     * @return Szczegóły kont uzytkowników podzielona na strony
     */
    @GetMapping("admin/details/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AccountDetailsPageDTO getAccountsDetails(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String secondName,
            @RequestParam(required = false) AccountRole role,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestHeader(name = "Authorization") String token
    ) {
        AccountDetailsRequest filters = AccountDetailsRequest.builder()
                .filters(AccountDetailsFilter.builder()
                        .id(id)
                        .username(username)
                        .firstName(firstName)
                        .secondName(secondName)
                        .role(role)
                        .build())
                .page(page)
                .size(size)
                .build();

        return accountFacade.getAccountDetailsPage(filters, token);
    }

    /**
     * Usuwa konto użytkownika o podanym identyfikatorze.
     *
     * @param accountId identyfikator użytkownika do usunięcia
     */
    @DeleteMapping("/admin/delete/{accountId}")
    public void deleteAccount(@PathVariable String accountId) {
        accountFacade.deleteAccount(accountId);
    }
}
