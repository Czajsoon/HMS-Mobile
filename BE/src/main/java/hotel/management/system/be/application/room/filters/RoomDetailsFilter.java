package hotel.management.system.be.application.room.filters;

import hotel.management.system.be.entities.RoomStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

/**
 * The type Room details filter.
 */
@Value
@Builder
public class RoomDetailsFilter {
    Boolean balconyAvailable;
    Integer level;
    RoomStatus roomStatus;
    BigDecimal pricePerNight;
    Integer bedsNumber;
}
