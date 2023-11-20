package hotel.management.system.be.application.room.repository;

import hotel.management.system.be.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Room repository port.
 */
@Repository
interface RoomRepositoryPort extends JpaRepository<RoomEntity, UUID>, JpaSpecificationExecutor<RoomEntity> {
}
