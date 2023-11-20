package hotel.management.system.be.application.account.mappers;

import hotel.management.system.be.application.account.dto.AccountDetailDTO;
import hotel.management.system.be.application.account.dto.AccountDetailsDTO;
import hotel.management.system.be.application.account.dto.RegisterRequest;
import hotel.management.system.be.application.account.dto.TwoFactorAccountConfig;
import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import hotel.management.system.be.entities.AccountEntity;
import hotel.management.system.be.entities.AccountRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Mapper dla kont.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AccountMapper {
    /**
     * Zwraca instancję Mappera dla ACCOUNT_MAPPER.
     */
    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    /**
     * Mapuje encje na AccountPrincipal.
     *
     * @param account konto z bazy danych
     * @return AccountPrincipal
     */
    @Mapping(expression = "java(mapAuthorities(account.getRole()))", target = "authorities")
    AccountPrincipal map(AccountEntity account);

    /**
     * Mapuje z requesta do rejesttracji na encje bazodanową.
     *
     * @param request         żądanie
     * @param passwordEncoder enkoder dla haseł
     * @return encja bazodanowa AccountEntity
     */
    @Mapping(expression = "java(java.util.UUID.randomUUID())", target = "id")
    @Mapping(expression = "java(passwordEncoder.encode(request.getPassword()))", target = "password")
    @Mapping(expression = "java(java.lang.Boolean.FALSE)", target = "is2FaConfigured")
    @Mapping(target = "secret", expression = "java(\"\")")
    AccountEntity map(RegisterRequest request, PasswordEncoder passwordEncoder);

    /**
     * Mapuje encję bazodanową do danych konta.
     *
     * @param account encja bazodanowa
     * @return dane użytkownika
     */
    AccountDetailDTO mapAccountDetail(AccountEntity account);

    /**
     * Mapuje encje bazodanową do konfiguracji 2FA.
     *
     * @param account encja bazodanowa
     * @return konfiguracja 2FA
     */
    TwoFactorAccountConfig mapToConfig(AccountEntity account);

    /**
     * Mapuje role uzytkowników.
     *
     * @param role rola uzytkownika
     * @return kolekcja roli użytkownika
     */
    default Collection<GrantedAuthority> mapAuthorities(AccountRole role) {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Mapuje encje bazodanową na szczegóły konta.
     *
     * @param account encja bazodanowa
     * @return szczegóły uzytkownika
     */
    AccountDetailsDTO mapAccountDetails(AccountEntity account);

    /**
     * Mapuje listę encji bazodanowych na listę szczegółów użytkowników.
     *
     * @param accounts lista encji bazodanowych
     * @return lista szczegółów kont
     */
    default List<AccountDetailsDTO> mapAccountDetailsList(List<AccountEntity> accounts) {
        return accounts.stream()
                .map(this::mapAccountDetails)
                .toList();
    }
}
