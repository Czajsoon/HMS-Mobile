package hotel.management.system.be.application.room.infrastructure;

import hotel.management.system.be.AbstractIntegrationTest;
import hotel.management.system.be.application.exception.BadRequestException;
import hotel.management.system.be.entities.RoomEntityAssert;
import hotel.management.system.be.entities.RoomStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static hotel.management.system.be.entities.FakeRoomEntity.fakeRoomEntityBuilder;
import static hotel.management.system.be.shared.TestRandomUtils.getRandomUUID;
import static hotel.management.system.be.shared.TestRandomUtils.getRandomValue;

class RoomFacadeTest extends AbstractIntegrationTest {

    @Autowired
    private RoomFacade sut;

    @AfterEach
    void tearDown() {
        roomTestRepository.deleteAll();
    }

    @ParameterizedTest
    @EnumSource(value = RoomStatus.class)
    void change_room_status(RoomStatus expectedStatus) {
        //given
        var existingRoom = roomTestRepository.insertRoom(fakeRoomEntityBuilder().build());

        //when
        sut.changeRoomStatus(existingRoom.getId(), expectedStatus);

        //then
        var changedRoom = roomTestRepository.findById(existingRoom.getId());

        RoomEntityAssert.assertThat(changedRoom)
                .hasRoomStatus(expectedStatus);
        roomTestRepository.deleteAll();
    }

    @Test
    void throw_exception_when_room_not_exists() {
        //given
        UUID roomId = getRandomUUID();

        //when
        BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> sut.changeRoomStatus(roomId, getRandomValue(RoomStatus.class))
        );

        //then
        org.assertj.core.api.Assertions.assertThat(exception.getMessage())
                .isEqualTo(String.format("Pokój o Id: %s nie został znaleziony!", roomId));

    }


}