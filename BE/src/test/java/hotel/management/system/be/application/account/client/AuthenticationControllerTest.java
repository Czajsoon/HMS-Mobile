package hotel.management.system.be.application.account.client;

import hotel.management.system.be.AbstractIntegrationTest;
import hotel.management.system.be.application.account.dto.ChangePasswordRequest;
import hotel.management.system.be.application.account.dto.LoginRequest;
import hotel.management.system.be.application.account.dto.LoginResponse;
import hotel.management.system.be.application.account.dto.RegisterRequest;
import hotel.management.system.be.entities.AccountEntity;
import hotel.management.system.be.entities.AccountRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static hotel.management.system.be.application.account.dto.FakeLoginRequestBuilder.fakeLoginRequestBuilder;
import static hotel.management.system.be.application.account.dto.FakeRegisterRequestBuilder.fakeRegisterRequestBuilder;
import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;
import static hotel.management.system.be.shared.JsonMapper.fromJson;
import static hotel.management.system.be.shared.JsonMapper.toJson;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerTest extends AbstractIntegrationTest {

    @AfterEach
    void tearDown() {
        accountTestRepository.deleteAll();
    }

    @Test
    void login_and_get_token_and_return_200_http_status() throws Exception {
        //given
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().build();
        accountTestRepository.insertAccount(registerRequest);
        LoginRequest loginRequest = fakeLoginRequestBuilder()
                .login(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .build();

        //when && then
        String response = mockMvc.perform(post("/services-rest/authenticate/login")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponse result = fromJson(response, LoginResponse.class);

        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(result.getToken());

        Assertions.assertThat(registerRequest.getUsername())
                .isEqualTo(usernameFromToken);
    }


    @Test
    void fail_login_when_user_not_exists_and_return_400_http_status() throws Exception {
        //given
        LoginRequest loginRequest = fakeLoginRequestBuilder().build();

        //when && then
        String response = mockMvc.perform(post("/services-rest/authenticate/login")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(loginRequest)))
                .andExpect(status().is(BAD_REQUEST.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(response)
                .isEqualTo("Użytkownik o podanym loginie nie istnieje");
    }

    @Test
    void register_new_user_and_return_201_http_status() throws Exception {
        //given
        RegisterRequest adminRegisterRequest = fakeRegisterRequestBuilder()
                .role(AccountRole.ADMIN)
                .build();
        AccountEntity adminAccountEntity = accountTestRepository.insertAccount(adminRegisterRequest);
        RegisterRequest registerRequest = fakeRegisterRequestBuilder()
                .build();
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(adminAccountEntity));

        //when && then
        mockMvc.perform(post("/services-rest/authenticate/admin/register")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", String.format("Bearer %s", token))
                        .content(toJson(registerRequest)))
                .andExpect(status().is(CREATED.value()));
    }

    @Test
    void change_password() throws Exception{
        String url= "/services-rest/authenticate/changePassword";
        String password="abc";
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().password(password).build();
        AccountEntity account = accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));
        ChangePasswordRequest changePasswordRequest= new ChangePasswordRequest(password,"def");

        mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .header("Authorization", String.format("Bearer %s", token))
                .content(toJson(changePasswordRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void get_two_factor_config() throws Exception{
        String url= "/services-rest/authenticate/two-factor/config";
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().build();
        AccountEntity account = accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));

        mockMvc.perform(get(url)
                .header("Authorization", String.format("Bearer %s", token)))
                .andExpect(status().isOk());
    }

    @Test
    void get_qr_code() throws Exception{
        String url= "/services-rest/authenticate/qrcode";
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().isUsing2FA(true).build();
        AccountEntity account = accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));

        mockMvc.perform(get(url)
                        .header("Authorization", String.format("Bearer %s", token)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE));
    }

    @Test
    void reset_two_factor() throws Exception{

        RegisterRequest adminRegisterRequest = fakeRegisterRequestBuilder()
                .role(AccountRole.ADMIN)
                .build();
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().isUsing2FA(true).build();
        AccountEntity adminAccount = accountTestRepository.insertAccount(adminRegisterRequest);
        AccountEntity account= accountTestRepository.insertAccount(registerRequest);
        String url= "/services-rest/authenticate/admin/reset-two-factor/"+account.getId();
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(adminAccount));

        mockMvc.perform(put(url)
                .header("Authorization", String.format("Bearer %s", token))
        ).andExpect(status().isOk());
    }

    @Test
    void change_two_factor() throws Exception{
        RegisterRequest adminRegisterRequest = fakeRegisterRequestBuilder()
                .role(AccountRole.ADMIN)
                .build();
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().isUsing2FA(true).build();
        AccountEntity adminAccount = accountTestRepository.insertAccount(adminRegisterRequest);
        AccountEntity account= accountTestRepository.insertAccount(registerRequest);
        String url= "/services-rest/authenticate/admin/change-two-factor/"+account.getId()+"/false";
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(adminAccount));

        mockMvc.perform(put(url)
                .header("Authorization", String.format("Bearer %s", token))
        ).andExpect(status().isOk());
    }
}