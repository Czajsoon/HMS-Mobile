package hotel.management.system.be.application.room.repository;

import hotel.management.system.be.application.room.dto.RoomDetailsPageDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsRequest;
import hotel.management.system.be.entities.RoomEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The interface Room repository.
 */
@Repository
public interface RoomRepository {

    /**
     * Find all rooms list.
     *
     * @return the list
     */
    List<RoomEntity> findAllRooms();

    /**
     * Find room details room details page dto.
     *
     * @param roomDetailsRequest the room details request
     * @return the room details page dto
     */
    RoomDetailsPageDTO findRoomDetails(RoomDetailsRequest roomDetailsRequest);

    /**
     * Save room entity.
     *
     * @param room the room
     * @return the room entity
     */
    RoomEntity save(RoomEntity room);

    /**
     * Find room by id room entity.
     *
     * @param roomId the room id
     * @return the room entity
     */
    RoomEntity findRoomById(UUID roomId);
}
