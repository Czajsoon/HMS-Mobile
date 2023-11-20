package hotel.management.system.be.application.room.infrastructure;

import hotel.management.system.be.application.room.dto.AddRoomDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsPageDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsRequest;
import hotel.management.system.be.entities.RoomEntity;
import hotel.management.system.be.entities.RoomStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class RoomFacadeImpl implements RoomFacade{

    private final RoomService roomService;

    @Override
    public RoomDetailsPageDTO getRoomDetailsPage(RoomDetailsRequest request) {
        return roomService.findRooms(
                request);
    }

    @Override
    public RoomEntity addRoom(AddRoomDTO addRoomDTO) {
        return roomService.addRoom(addRoomDTO);
    }

    @Override
    public void changeRoomStatus(UUID roomId, RoomStatus roomStatus) {
        roomService.changeRoomStatus(roomId, roomStatus);
    }
}
