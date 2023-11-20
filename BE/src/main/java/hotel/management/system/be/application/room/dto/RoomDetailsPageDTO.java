package hotel.management.system.be.application.room.dto;

import lombok.*;

import java.util.List;

/**
 * The type Room details page dto.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsPageDTO {
    private Long totalElements;
    private Integer totalPages;
    private List<RoomDetailDTO> rooms;
}
