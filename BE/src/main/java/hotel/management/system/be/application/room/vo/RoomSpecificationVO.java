package hotel.management.system.be.application.room.vo;


import hotel.management.system.be.entities.RoomEntity;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

/**
 * The type Room specification vo.
 */
@Builder
@Value
public class RoomSpecificationVO {
    Specification<RoomEntity> specification;
    PageRequest pageRequest;
}
