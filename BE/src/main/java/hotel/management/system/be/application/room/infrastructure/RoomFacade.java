package hotel.management.system.be.application.room.infrastructure;

import hotel.management.system.be.application.room.dto.AddRoomDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsPageDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsRequest;
import hotel.management.system.be.entities.RoomEntity;
import hotel.management.system.be.entities.RoomStatus;

import java.util.UUID;

/**
 * The interface Room facade.
 */
public interface RoomFacade {

    /**
     * Gets room details page.
     *
     * @param request the request
     * @return the room details page
     */
    RoomDetailsPageDTO getRoomDetailsPage(RoomDetailsRequest request);

    /**
     * Add room room entity.
     *
     * @param addRoomDTO the add room dto
     * @return the room entity
     */
    RoomEntity addRoom(AddRoomDTO addRoomDTO);

    /**
     * Change room status.
     *
     * @param roomId     the room id
     * @param roomStatus the room status
     */
    void changeRoomStatus(UUID roomId, RoomStatus roomStatus);
}
