package hotel.management.system.be.application.account.dto;

import hotel.management.system.be.shared.TestRandomUtils;

public class FakeLoginRequestBuilder {

    public static LoginRequest.LoginRequestBuilder fakeLoginRequestBuilder() {
        return LoginRequest.builder()
                .login(TestRandomUtils.getRandomString(8))
                .password(TestRandomUtils.getRandomString(8));
    }
}