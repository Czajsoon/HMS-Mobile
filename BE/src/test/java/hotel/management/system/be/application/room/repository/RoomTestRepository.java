package hotel.management.system.be.application.room.repository;

import hotel.management.system.be.entities.RoomEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class RoomTestRepository {
    @Autowired
    private final RoomRepositoryPort roomRepositoryPort;

    public RoomEntity insertRoom(RoomEntity entity) {
        return roomRepositoryPort.save(entity);
    }

    public RoomEntity findById(UUID roomId) {
        return roomRepositoryPort.findById(roomId).get();
    }

    public List<RoomEntity> findAll() {
        return roomRepositoryPort.findAll();
    }

    public void deleteAll() {
        roomRepositoryPort.deleteAll();
    }
}