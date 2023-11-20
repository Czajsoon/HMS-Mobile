package hotel.management.system.be.application.room.mappers;

import hotel.management.system.be.application.room.dto.AddRoomDTO;
import hotel.management.system.be.application.room.dto.RoomDetailDTO;
import hotel.management.system.be.entities.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * The interface Room mapper.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoomMapper {

    /**
     * The constant ROOM_MAPPER.
     */
    RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);

    /**
     * Map room detail room detail dto.
     *
     * @param room the room
     * @return the room detail dto
     */
    RoomDetailDTO mapRoomDetail(RoomEntity room);

    /**
     * Map account details list list.
     *
     * @param rooms the rooms
     * @return the list
     */
    default List<RoomDetailDTO> mapAccountDetailsList(List<RoomEntity> rooms) {
        return rooms.stream()
                .map(this::mapRoomDetail)
                .toList();
    }

    /**
     * Map room entity room entity.
     *
     * @param addRoomDTO the add room dto
     * @return the room entity
     */
    @Mapping(expression = "java(java.util.UUID.randomUUID())", target = "id")
    @Mapping(ignore = true, target = "roomStatus")
    @Mapping(expression = "java(null)", target = "description")
    @Mapping(expression = "java(null)", target = "comments")
    RoomEntity mapRoomEntity(AddRoomDTO addRoomDTO);
}
