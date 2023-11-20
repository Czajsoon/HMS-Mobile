package hotel.management.system.be.application.room.dto;

import hotel.management.system.be.entities.RoomStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The type Room detail dto.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailDTO {

    private UUID id;
    private Integer level;
    private RoomStatus roomStatus;
    private BigDecimal pricePerNight;
    private Boolean balconyAvailable;
    private Integer bedsNumber;
    private String description;
    private String comments;
}
