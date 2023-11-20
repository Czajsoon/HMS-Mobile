package hotel.management.system.be.application.account.repository;

import hotel.management.system.be.application.account.dto.RegisterRequest;
import hotel.management.system.be.entities.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;

@Repository
@AllArgsConstructor
public class AccountTestRepository {
    private final AccountRepositoryPort accountRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public AccountEntity save(AccountEntity entity) {
        return accountRepositoryPort.save(entity);
    }

    public AccountEntity insertAccount(RegisterRequest request) {
        return save(ACCOUNT_MAPPER.map(request, passwordEncoder));
    }

    public void deleteAll() {
        accountRepositoryPort.deleteAll();
    }
}