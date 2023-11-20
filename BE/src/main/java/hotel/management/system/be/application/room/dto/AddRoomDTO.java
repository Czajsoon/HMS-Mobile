package hotel.management.system.be.application.room.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * The type Add room dto.
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class AddRoomDTO {

    private Integer level;

    private BigDecimal pricePerNight;

    private Boolean balconyAvailable;

    private Integer bedsNumber;

}
