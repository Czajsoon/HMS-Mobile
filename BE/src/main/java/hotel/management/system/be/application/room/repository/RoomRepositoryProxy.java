package hotel.management.system.be.application.room.repository;

import hotel.management.system.be.application.exception.BadRequestException;
import hotel.management.system.be.application.room.dto.RoomDetailsPageDTO;
import hotel.management.system.be.application.room.dto.RoomDetailsRequest;
import hotel.management.system.be.application.room.vo.RoomSpecificationVO;
import hotel.management.system.be.entities.RoomEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static hotel.management.system.be.application.room.mappers.RoomMapper.ROOM_MAPPER;

/**
 * The type Room repository proxy.
 */
@Service
@AllArgsConstructor
public class RoomRepositoryProxy implements RoomRepository{

    private final RoomRepositoryPort roomRepositoryPort;

    @Override
    public List<RoomEntity> findAllRooms(){
        return roomRepositoryPort.findAll();
    }

    @Override
    public RoomDetailsPageDTO findRoomDetails(RoomDetailsRequest roomDetailsRequest) {
        RoomSpecificationVO roomSpecificationVO = RoomSpecification.getSpecificationAndPage(roomDetailsRequest);
        Page<RoomEntity> roomEntityPage = roomRepositoryPort.findAll(roomSpecificationVO.getSpecification(), roomSpecificationVO.getPageRequest());
        return RoomDetailsPageDTO.builder()
                .totalElements(roomEntityPage.getTotalElements())
                .totalPages(roomEntityPage.getTotalPages())
                .rooms(ROOM_MAPPER.mapAccountDetailsList(roomEntityPage.getContent()))
                .build();
    }

    @Override
    public RoomEntity save(RoomEntity room) {
        return roomRepositoryPort.save(room);
    }

    @Override
    public RoomEntity findRoomById(UUID roomId) {
        return roomRepositoryPort.findById(roomId)
                .orElseThrow(() -> new BadRequestException("Pokój o Id: %s nie został znaleziony!", roomId.toString()));
    }
}
