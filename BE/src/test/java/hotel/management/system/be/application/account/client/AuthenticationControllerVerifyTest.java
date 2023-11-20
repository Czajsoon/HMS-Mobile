package hotel.management.system.be.application.account.client;

import com.warrenstrange.googleauth.IGoogleAuthenticator;
import hotel.management.system.be.AbstractIntegrationTest;
import hotel.management.system.be.application.account.dto.RegisterRequest;
import hotel.management.system.be.entities.AccountEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hotel.management.system.be.application.account.dto.FakeRegisterRequestBuilder.fakeRegisterRequestBuilder;
import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerVerifyTest extends AbstractIntegrationTest {

    @AfterEach
    void tearDown() {
        accountTestRepository.deleteAll();
    }

    @MockBean
    IGoogleAuthenticator googleAuthenticator;

    @Test
    void validate_code() throws Exception{
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().isUsing2FA(true).build();
        AccountEntity account= accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));
        int code=123;
        String url= "/services-rest/authenticate/validate/"+code;

        Mockito.when(googleAuthenticator.authorize(anyString(),anyInt())).thenReturn(true);

        mockMvc.perform(put(url)
                .header("Authorization", String.format("Bearer %s", token))
        ).andExpect(status().isOk());

    }
}
