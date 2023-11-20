package hotel.management.system.be.application.account.infrastructure;

import hotel.management.system.be.AbstractIntegrationTest;
import hotel.management.system.be.application.account.dto.*;
import hotel.management.system.be.application.account.filters.AccountDetailsFilter;
import hotel.management.system.be.entities.AccountEntity;
import hotel.management.system.be.entities.AccountRole;
import lombok.Builder;
import lombok.Value;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hotel.management.system.be.application.account.dto.FakeRegisterRequestBuilder.fakeRegisterRequestBuilder;
import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;
import static hotel.management.system.be.entities.AccountRole.ADMIN;
import static hotel.management.system.be.entities.AccountRole.EMPLOYEE;
import static hotel.management.system.be.shared.TestRandomUtils.getRandomString;
import static hotel.management.system.be.shared.TestRandomUtils.getRandomUUIDString;

class AccountFacadeTest extends AbstractIntegrationTest {

    @Autowired
    AccountFacadeImpl sut;

    private static final String FILTER_TEST = "FILTER_TEST. ";
    private static final String PAGE_TEST = "PAGE_TEST. ";
    private static final Account ADMIN_FOR_TEST = account(ADMIN);
    private static final Account EMPLOYEE_1 = account(EMPLOYEE);
    private static final Account EMPLOYEE_2 = account(EMPLOYEE);

    @BeforeEach
    void init() {
        accountTestRepository.deleteAll();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("prepareData")
    void find_all_accounts(
            String testName,
            AccountDetailsRequest request,
            List<Account> accounts,
            Expected expected
    ) {
        //given
        String token = generateAdminToken();
        Map<String, AccountDetailsDTO> accountsById = prepareAccounts(accounts);

        //when
        AccountDetailsPageDTO result = sut.getAccountDetailsPage(request, token);

        //then
        assertCheck(result, accountsById, expected.getTotalPages(), expected.getTotalElements());
    }

    private String generateAdminToken() {
        AccountEntity account = accountTestRepository.insertAccount(ADMIN_FOR_TEST.registerRequest());
        return "Bearer " + jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));
    }

    Map<String, AccountDetailsDTO> prepareAccounts(List<Account> accounts) {
        return accounts.stream()
                .map(this::registerAndFillAccount)
                .collect(Collectors.toMap(InsertedAccount::getId, InsertedAccount::getAccount));
    }

    void assertCheck(AccountDetailsPageDTO result, Map<String, AccountDetailsDTO> accountById, Integer totalPages, Long totalElements) {
        Assertions.assertThat(result.getTotalElements())
                .isEqualTo(totalElements);

        Assertions.assertThat(result.getTotalPages())
                .isEqualTo(totalPages);

        result.getAccounts().forEach(account -> {
            Assertions.assertThat(accountById)
                    .containsKey(account.getId());

            AccountDetailsDTO expected = accountById.get(account.getId());
            AccountDetailsDTOAssert.assertThat(account)
                    .hasUsername(expected.getUsername())
                    .hasFirstName(expected.getFirstName())
                    .hasSecondName(expected.getSecondName())
                    .hasRole(expected.getRole());
        });
    }

    private static Stream<Arguments> prepareData() {
        return Stream.of(
                Arguments.of(
                        testName(FILTER_TEST, "no filters present expected 2 results"),
                        filters(emptyFilter(), 0, 1000),
                        accounts(EMPLOYEE_1, EMPLOYEE_2),
                        expected(1, 2L, EMPLOYEE_1, EMPLOYEE_2)
                ),
                Arguments.of(
                        testName(FILTER_TEST, "username present expected 1 results"),
                        filters(filter(null, EMPLOYEE_1.getUsername(), null, null, null), 0, 1000),
                        accounts(EMPLOYEE_1, EMPLOYEE_2),
                        expected(1, 1L, EMPLOYEE_1)
                ),
                Arguments.of(
                        testName(FILTER_TEST, "firstName present expected 1 results"),
                        filters(filter(null, null, EMPLOYEE_1.getFirstName(), null, null), 0, 1000),
                        accounts(EMPLOYEE_1, EMPLOYEE_2),
                        expected(1, 1L, EMPLOYEE_1)
                ),
                Arguments.of(
                        testName(FILTER_TEST, "secondName present expected 1 results"),
                        filters(filter(null, null, null, EMPLOYEE_1.getSecondName(), null), 0, 1000),
                        accounts(EMPLOYEE_1, EMPLOYEE_2),
                        expected(1, 1L, EMPLOYEE_1)
                ),
                Arguments.of(
                        testName(FILTER_TEST, "role present expected 1 results"),
                        filters(filter(null, null, null, null, EMPLOYEE), 0, 1000),
                        accounts(EMPLOYEE_1, EMPLOYEE_2),
                        expected(1, 2L, EMPLOYEE_1, EMPLOYEE_2)
                ),
                Arguments.of(
                        testName(PAGE_TEST, "no filters present 2 pages 1 each page"),
                        filters(emptyFilter(), 0, 1),
                        accounts(EMPLOYEE_1, EMPLOYEE_2),
                        expected(2, 2L, EMPLOYEE_1)
                ),
                Arguments.of(
                        testName(PAGE_TEST, "no filters present 2 pages 1 each page"),
                        filters(emptyFilter(), 1, 1),
                        accounts(EMPLOYEE_1, EMPLOYEE_2),
                        expected(2, 2L, EMPLOYEE_2)
                )
        );
    }

    public static String testName(String testName, String name) {
        return testName + name;
    }

    public static Expected expected(Integer totalPages, Long totalElements, Account... accounts) {
        return Expected.builder()
                .accounts(List.of(accounts))
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    private static List<Account> accounts(Account... accounts) {
        return List.of(accounts);
    }

    private static AccountDetailsRequest filters(AccountDetailsFilter filter, Integer page, Integer size) {
        return AccountDetailsRequest.builder()
                .filters(filter)
                .size(size)
                .page(page)
                .build();
    }

    private static AccountDetailsFilter filter(String id,
                                               String username,
                                               String firstName,
                                               String secondName,
                                               AccountRole role) {
        return AccountDetailsFilter.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .secondName(secondName)
                .role(role)
                .build();

    }

    private static AccountDetailsFilter emptyFilter() {
        return AccountDetailsFilter.builder()
                .id(null)
                .username(null)
                .firstName(null)
                .secondName(null)
                .role(null)
                .build();

    }


    InsertedAccount registerAndFillAccount(Account account) {
        AccountEntity accountEntity = accountTestRepository.save(AccountEntity.builder()
                .password(getRandomString(8))
                .username(account.getUsername())
                .firstName(account.getFirstName())
                .secondName(account.getSecondName())
                .role(account.getRole())
                .build());
        return InsertedAccount.builder()
                .id(accountEntity.getId().toString())
                .account(AccountDetailsDTO.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .firstName(account.getFirstName())
                        .secondName(account.getSecondName())
                        .role(account.getRole())
                        .build())
                .build();
    }

    private static Account account(AccountRole role) {
        return Account.builder()
                .id(getRandomUUIDString())
                .username(getRandomString(9))
                .firstName(getRandomString(6))
                .secondName(getRandomString(6))
                .role(role)
                .build();
    }

    @Value
    @Builder
    static class Expected {
        Long totalElements;
        Integer totalPages;
        List<Account> accounts;
    }

    @Value
    @Builder
    static class InsertedAccount {
        String id;
        AccountDetailsDTO account;
    }

    @Value
    @Builder
    static class Account {
        String id;
        String username;
        String firstName;
        String secondName;
        AccountRole role;

        RegisterRequest registerRequest() {
            return fakeRegisterRequestBuilder()
                    .username(username)
                    .firstName(firstName)
                    .secondName(secondName)
                    .role(role)
                    .build();
        }
    }

}