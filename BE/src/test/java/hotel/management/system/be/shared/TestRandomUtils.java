package hotel.management.system.be.shared;

import java.util.Random;
import java.util.UUID;

public class TestRandomUtils {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ALPHA_NUMERIC_STRING_LENGTH = ALPHA_NUMERIC_STRING.length();

    public static String getRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING_LENGTH);
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }

    public static UUID getRandomUUID() {
        return UUID.randomUUID();
    }

    public static String getRandomUUIDString() {
        return getRandomUUID().toString();
    }

    public static Integer getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static Integer getRandomInt() {
        return getRandomInt(1, 100000);
    }

    public static <T extends Enum<T>> T getRandomValue(Class<T> enumClass) {
        Random random = new Random();
        T[] values = enumClass.getEnumConstants();
        return values[random.nextInt(values.length)];
    }

}
