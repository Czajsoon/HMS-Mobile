package hotel.management.system.be.application.room.dto;

import java.math.BigDecimal;

public class FakeAddRoomDtoBuilder {

    public static AddRoomDTO.AddRoomDTOBuilder fakeAddRoomDtoBuilder() {
        return AddRoomDTO.builder()
                .balconyAvailable(false)
                .bedsNumber(1)
                .level(1)
                .pricePerNight(BigDecimal.valueOf(1));
    }
}
