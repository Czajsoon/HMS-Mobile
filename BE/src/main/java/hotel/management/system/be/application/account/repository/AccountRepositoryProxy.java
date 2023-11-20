package hotel.management.system.be.application.account.repository;

import hotel.management.system.be.application.account.dto.AccountDetailsPageDTO;
import hotel.management.system.be.application.account.dto.AccountDetailsRequest;
import hotel.management.system.be.application.account.vo.AccountSpecificationVO;
import hotel.management.system.be.application.exception.BadRequestException;
import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import hotel.management.system.be.entities.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;

@Service
@AllArgsConstructor
class AccountRepositoryProxy implements AccountRepository {
    private final AccountRepositoryPort accountRepositoryPort;

    private static final String ACCOUNT_BY_USERNAME_NOT_FOUND = "Użytkownik o podanym loginie nie istnieje";
    private static final String ACCOUNT_BY_USERNAME_EXISTS = "Konto o podanym loginie: %s już istnieje";
    private static final String ACCOUNT_NOT_EXISTS = "Nie znaleziono podanego użytkownika";

    @Override
    public AccountPrincipal findUserPrincipalByUsername(String username) {
        return accountRepositoryPort.findAccountEntityByUsername(username)
                .map(ACCOUNT_MAPPER::map)
                .orElseThrow(() -> new BadRequestException(ACCOUNT_BY_USERNAME_NOT_FOUND));
    }

    @Override
    public AccountEntity findAccountByUsername(String username) {
        return accountRepositoryPort.findAccountEntityByUsername(username)
                .orElseThrow(() -> new BadRequestException(ACCOUNT_BY_USERNAME_NOT_FOUND));
    }

    @Override
    public void validateAccountExistsWithUsername(String username) {
        if (accountRepositoryPort.existsAccountEntityByUsername(username)) {
            throw new BadRequestException(ACCOUNT_BY_USERNAME_EXISTS, username);
        }
    }

    @Override
    public AccountEntity findAccountById(String id) {
        return accountRepositoryPort.findById(UUID.fromString(id))
                .orElseThrow(() -> new BadRequestException(ACCOUNT_NOT_EXISTS));
    }

    @Override
    public AccountEntity save(AccountEntity account) {
        return accountRepositoryPort.save(account);
    }

    @Override
    public String getAccountSecret(String username) {
        return accountRepositoryPort.findAccountEntityByUsername(username)
                .map(AccountEntity::getSecret)
                .orElseThrow(()->new BadRequestException("chuj"));
    }



    @Override
    public AccountDetailsPageDTO findAccountDetails(AccountDetailsRequest accountDetailsRequest, String username) {
        AccountSpecificationVO accountSpecificationVO = AccountSpecification.getSpecificationAndPage(accountDetailsRequest, username);
        Page<AccountEntity> accountEntityPage = accountRepositoryPort.findAll(accountSpecificationVO.getSpecification(), accountSpecificationVO.getPageRequest());
        return AccountDetailsPageDTO.builder()
                .totalElements(accountEntityPage.getTotalElements())
                .totalPages(accountEntityPage.getTotalPages())
                .accounts(ACCOUNT_MAPPER.mapAccountDetailsList(accountEntityPage.getContent()))
                .build();
    }

    @Override
    public void delete(String accountId) {
        accountRepositoryPort.deleteById(UUID.fromString(accountId));
    }

}
