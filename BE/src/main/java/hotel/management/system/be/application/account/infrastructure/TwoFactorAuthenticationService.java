package hotel.management.system.be.application.account.infrastructure;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.IGoogleAuthenticator;
import hotel.management.system.be.application.account.dto.TwoFactorAccountConfig;
import hotel.management.system.be.application.account.repository.AccountRepository;
import hotel.management.system.be.application.account.vo.TwoAuthAccountDataVO;
import hotel.management.system.be.application.exception.BadRequestException;
import hotel.management.system.be.configuration.Properties;
import hotel.management.system.be.configuration.security.JwtTokenUtil;
import hotel.management.system.be.configuration.security.shared.TokenUtils;
import hotel.management.system.be.entities.AccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;

/**
 * 2FA serwis.
 */
@Service
@AllArgsConstructor
public class TwoFactorAuthenticationService {


    private final IGoogleAuthenticator googleAuthenticator;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final Properties properties;

    /**
     * Generuje kod QR.
     *
     * @param rawToken token uzytkownika
     * @return obrazek kodu QR
     * @throws WriterException Może wystąpić wyjątek WriterException
     * @throws IOException     Może wystąpić wyjątek IOException
     */
    public byte[] generateQRCode(String rawToken) throws WriterException, IOException {
        TwoAuthAccountDataVO twoAuthAccountDataVO = handleSetUpSecret(rawToken);
        BitMatrix qrCode = new QRCodeWriter()
                .encode(generateQrCode(twoAuthAccountDataVO), BarcodeFormat.QR_CODE, 200, 200);
        return writeToQrCodeToOutput(qrCode);
    }

    /**
     * Sprawdzanie kodu z aplikacji GoogleAuthenticator.
     *
     * @param rawToken token użytkownika
     * @param code     kod wprowadzony przez użytkownika
     */
    public void validateCode(String rawToken, int code) {
        if (!googleAuthenticator.authorize(accountService.getAccountSecret(TokenUtils.getTokenFromRawHeader(rawToken)), code)) {
            throw new BadRequestException("Podano nierawidłowy kod");
        }
    }

    /**
     * Gets two fa account config.
     *
     * @param rawToken the raw token
     * @return the two fa account config
     */
    public TwoFactorAccountConfig getTwoFaAccountConfig(String rawToken) {
        String token = TokenUtils.getTokenFromRawHeader(rawToken);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return ACCOUNT_MAPPER.mapToConfig(accountRepository.findAccountByUsername(username));
    }

    /**
     * Reset two factor.
     *
     * @param accountId the account id
     */
    public void resetTwoFactor(String accountId) {
        AccountEntity account = accountRepository.findAccountById(accountId);
        account.setSecret(generateSecretKey());
        account.setIs2FaConfigured(false);
        accountRepository.save(account);
    }

    /**
     * Change two factor.
     *
     * @param accountId the account id
     * @param value     the value
     */
    public void changeTwoFactor(String accountId, Boolean value) {
        AccountEntity account = accountRepository.findAccountById(accountId);
        account.setIsUsing2FA(value);
        accountRepository.save(account);
    }

    private String generateSecretKey() {
        return googleAuthenticator.createCredentials().getKey();
    }

    private TwoAuthAccountDataVO handleSetUpSecret(String rawToken) {
        AccountEntity account = getAccountByToken(rawToken);
        is2FaAccount(account);
        String secretKey = generateSecretKey();
        account.setSecret(secretKey);
        account.setIs2FaConfigured(true);
        accountRepository.save(account);
        return new TwoAuthAccountDataVO(account.getEmail(), secretKey);
    }

    private void is2FaAccount(AccountEntity account) {
        if (!account.getIsUsing2FA()) {
            throw new BadRequestException("To konto nie posiada skonfigurowanego 2FA");
        }
        if (account.getIs2FaConfigured()) {
            throw new BadRequestException("To konto posiada juz skonfigurowane 2FA");
        }
    }

    private String generateQrCode(TwoAuthAccountDataVO twoAuthAccountDataVO) {
        String issuer = properties.getAppName();
        return "otpauth://totp/"
                + issuer + ":"
                + twoAuthAccountDataVO.getEmail() +
                "?secret=" + twoAuthAccountDataVO.getSecret() +
                "&issuer=" + issuer;
    }

    private byte[] writeToQrCodeToOutput(BitMatrix qrCode) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(qrCode, "PNG", outStream);
        return outStream.toByteArray();
    }

    private AccountEntity getAccountByToken(String rawToken) {
        String token = TokenUtils.getTokenFromRawHeader(rawToken);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return accountRepository.findAccountByUsername(username);
    }
}
