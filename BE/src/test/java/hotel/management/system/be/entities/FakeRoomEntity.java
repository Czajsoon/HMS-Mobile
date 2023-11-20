package hotel.management.system.be.entities;

import java.math.BigDecimal;

import static hotel.management.system.be.shared.TestRandomUtils.*;

public class FakeRoomEntity {

    public static RoomEntity.RoomEntityBuilder fakeRoomEntityBuilder() {
        return RoomEntity.builder()
                .roomStatus(getRandomValue(RoomStatus.class))
                .balconyAvailable(true)
                .bedsNumber(getRandomInt())
                .level(getRandomInt())
                .comments(getRandomString(5))
                .description(getRandomString(5))
                .pricePerNight(BigDecimal.TEN);
    }

}