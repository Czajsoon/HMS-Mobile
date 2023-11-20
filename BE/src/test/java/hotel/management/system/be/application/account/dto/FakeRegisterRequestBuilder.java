package hotel.management.system.be.application.account.dto;

import hotel.management.system.be.entities.AccountRole;

import static hotel.management.system.be.shared.TestRandomUtils.getRandomString;
import static hotel.management.system.be.shared.TestRandomUtils.getRandomValue;

public class FakeRegisterRequestBuilder {

    public static RegisterRequest.RegisterRequestBuilder fakeRegisterRequestBuilder() {
        return RegisterRequest.builder()
                .username(getRandomString(8))
                .password(getRandomString(8))
                .role(getRandomValue(AccountRole.class))
                .firstName(getRandomString(8))
                .secondName(getRandomString(8));
    }

    public static RegisterRequest admin() {
        return RegisterRequest.builder()
                .username(getRandomString(8))
                .password(getRandomString(8))
                .role(AccountRole.ADMIN)
                .firstName(getRandomString(8))
                .secondName(getRandomString(8))
                .build();
    }

    public static RegisterRequest user() {
        return RegisterRequest.builder()
                .username(getRandomString(8))
                .password(getRandomString(8))
                .role(AccountRole.ADMIN)
                .firstName(getRandomString(8))
                .secondName(getRandomString(8))
                .build();
    }
}