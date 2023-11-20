package hotel.management.system.be.application.room.dto;

import hotel.management.system.be.application.room.filters.RoomDetailsFilter;
import lombok.*;

/**
 * The type Room details request.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsRequest {
    private Integer page;
    private Integer size;
    private RoomDetailsFilter filters;
}
