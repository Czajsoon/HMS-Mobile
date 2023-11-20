package hotel.management.system.be.configuration.security.shared;

/**
 * The type Token utils.
 */
public class TokenUtils {
    private TokenUtils() {
    }

    /**
     * Gets token from raw header.
     *
     * @param rawHeader the raw header
     * @return the token from raw header
     */
    public static String getTokenFromRawHeader(String rawHeader) {
        return rawHeader.substring(6);
    }
}
