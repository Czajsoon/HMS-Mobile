package hotel.management.system.be.application.account.client;

import hotel.management.system.be.AbstractIntegrationTest;
import hotel.management.system.be.application.account.dto.AccountDetailDTO;
import hotel.management.system.be.application.account.dto.AccountDetailDTOAssert;
import hotel.management.system.be.application.account.dto.RegisterRequest;
import hotel.management.system.be.entities.AccountEntity;
import hotel.management.system.be.entities.AccountRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static hotel.management.system.be.application.account.dto.FakeRegisterRequestBuilder.*;
import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;
import static hotel.management.system.be.shared.JsonMapper.fromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends AbstractIntegrationTest {

    @AfterEach
    void tearDown() {
        accountTestRepository.deleteAll();
    }

    @ParameterizedTest
    @EnumSource(AccountRole.class)
    void get_role_and_return_200_http_status(AccountRole role) throws Exception {
        //given
        RegisterRequest registerRequest = fakeRegisterRequestBuilder()
                .role(role)
                .build();
        AccountEntity account = accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));
        AccountDetailDTO expectedAccountDetails = buildDetails(account);

        //when && then
        String response = mockMvc.perform(get("/services-rest/account/details")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AccountDetailDTO result = fromJson(response, AccountDetailDTO.class);

        AccountDetailDTOAssert.assertThat(result)
                .hasRole(expectedAccountDetails.getRole())
                .hasFirstName(expectedAccountDetails.getFirstName())
                .hasSecondName(expectedAccountDetails.getSecondName());
    }

    @Test
    void find_accounts_and_return_200_http_status() throws Exception {
        //given
        RegisterRequest admin = admin();
        AccountEntity account = accountTestRepository.insertAccount(admin);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));

        //when && then
        mockMvc.perform(get("/services-rest/account/admin/details/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void delete_account_and_return_200_http_status() throws Exception {
        //given
        RegisterRequest admin = admin();
        RegisterRequest user = user();
        AccountEntity account = accountTestRepository.insertAccount(admin);
        AccountEntity accountTodelete = accountTestRepository.insertAccount(user);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));

        //when && then
        mockMvc.perform(delete("/services-rest/account/admin/delete/" + accountTodelete.getId().toString())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private AccountDetailDTO buildDetails(AccountEntity account) {
        return AccountDetailDTO.builder()
                .firstName(account.getFirstName())
                .secondName(account.getSecondName())
                .role(account.getRole().name())
                .build();
    }
}