package hotel.management.system.be.application.room.client;

import hotel.management.system.be.application.room.dto.AddRoomDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsPageDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsRequest;
import hotel.management.system.be.application.room.filters.RoomDetailsFilter;
import hotel.management.system.be.application.room.infrastructure.RoomFacade;
import hotel.management.system.be.entities.RoomEntity;
import hotel.management.system.be.entities.RoomStatus;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Kontroler dla pokoi.
 */
@RestController
@AllArgsConstructor
@RequestMapping("services-rest/rooms/")
public class RoomController {

    private final RoomFacade roomFacade;

    /**
     * pobiera dane pokojów.
     *
     * @param balconyAvailable czy posiada balkon
     * @param level            poziom
     * @param roomStatus       status pokoju
     * @param pricePerNight    cena za noc
     * @param bedsNumber       ilość łóżek
     * @param page             strona
     * @param size             ilośc wyników na stronie
     * @param token            token
     * @return szczegóły pokoi
     */
    @GetMapping("/all")
    public RoomDetailsPageDTO getRoomDetails(
            @RequestParam(required = false) Boolean balconyAvailable,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) RoomStatus roomStatus,
            @RequestParam(required = false) BigDecimal pricePerNight,
            @RequestParam(required = false) Integer bedsNumber,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "9") Integer size,
            @RequestHeader(name = "Authorization") String token
    ) {
        RoomDetailsRequest filters = RoomDetailsRequest.builder()
                .filters(RoomDetailsFilter.builder()
                        .balconyAvailable(balconyAvailable)
                        .level(level)
                        .roomStatus(roomStatus)
                        .pricePerNight(pricePerNight)
                        .bedsNumber(bedsNumber)
                        .build())
                .page(page)
                .size(size)
                .build();

        return roomFacade.getRoomDetailsPage(filters);
    }

    /**
     * Dodawanie pokoju.
     *
     * @param addRoomDTO the add room dto
     * @return the room entity
     */
    @PostMapping("/add")
    public RoomEntity addRoom(@RequestBody AddRoomDTO addRoomDTO) {
        return roomFacade.addRoom(addRoomDTO);
    }

    /**
     * Change room status.
     *
     * @param roomId     the room id
     * @param roomStatus the room status
     */
    @PatchMapping("/status/{roomId}/{roomStatus}")
    public void changeRoomStatus(
            @PathVariable UUID roomId,
            @PathVariable RoomStatus roomStatus
    ) {
        roomFacade.changeRoomStatus(roomId, roomStatus);
    }

}
