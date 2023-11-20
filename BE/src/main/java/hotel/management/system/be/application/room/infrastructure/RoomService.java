package hotel.management.system.be.application.room.infrastructure;

import hotel.management.system.be.application.room.dto.AddRoomDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsPageDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsRequest;
import hotel.management.system.be.application.room.repository.RoomRepository;
import hotel.management.system.be.entities.RoomEntity;
import hotel.management.system.be.entities.RoomStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static hotel.management.system.be.application.room.mappers.RoomMapper.ROOM_MAPPER;

/**
 * The type Room service.
 */
@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;


    /**
     * Find rooms room details page dto.
     *
     * @param request the request
     * @return the room details page dto
     */
    RoomDetailsPageDTO findRooms(RoomDetailsRequest request) {
        return roomRepository.findRoomDetails(request);
    }

    /**
     * Add room room entity.
     *
     * @param addRoomDTO the add room dto
     * @return the room entity
     */
    RoomEntity addRoom(AddRoomDTO addRoomDTO) {
        RoomEntity room = ROOM_MAPPER.mapRoomEntity(addRoomDTO);
        room.setRoomStatus(RoomStatus.AVAILABLE);
        return roomRepository.save(room);
    }

    /**
     * Change room status.
     *
     * @param roomId     the room id
     * @param roomStatus the room status
     */
    void changeRoomStatus(UUID roomId, RoomStatus roomStatus) {
        RoomEntity room = roomRepository.findRoomById(roomId);
        room.setRoomStatus(roomStatus);
        roomRepository.save(room);
    }


}
