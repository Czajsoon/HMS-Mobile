package hotel.management.system.be.application.room.client;

import hotel.management.system.be.AbstractIntegrationTest;
import hotel.management.system.be.application.account.dto.RegisterRequest;
import hotel.management.system.be.application.room.dto.AddRoomDTO;
import hotel.management.system.be.application.room.dto.FakeAddRoomDtoBuilder;
import hotel.management.system.be.entities.AccountEntity;
import hotel.management.system.be.entities.RoomStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static hotel.management.system.be.application.account.dto.FakeRegisterRequestBuilder.fakeRegisterRequestBuilder;
import static hotel.management.system.be.application.account.mappers.AccountMapper.ACCOUNT_MAPPER;
import static hotel.management.system.be.entities.FakeRoomEntity.fakeRoomEntityBuilder;
import static hotel.management.system.be.shared.JsonMapper.toJson;
import static hotel.management.system.be.shared.TestRandomUtils.getRandomValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoomControllerTest extends AbstractIntegrationTest {


    @AfterEach
    void tearDown() {
        roomTestRepository.deleteAll();
        accountTestRepository.deleteAll();
    }

    @Test
    void update_room_status() throws Exception {
        //given
        var existingRoom = roomTestRepository.insertRoom(fakeRoomEntityBuilder().build());
        var roomStatus = getRandomValue(RoomStatus.class);
        var url = String.format("/services-rest/rooms/status/%s/%s", existingRoom.getId().toString(), roomStatus);
        RegisterRequest registerRequest = fakeRegisterRequestBuilder()
                .build();
        AccountEntity account = accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));

        //when & then
        mockMvc.perform(patch(url)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void add_room() throws Exception{
        AddRoomDTO addRoomDTO= FakeAddRoomDtoBuilder.fakeAddRoomDtoBuilder().build();
        String url= "/services-rest/rooms/add";
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().build();
        AccountEntity account = accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));
        String json= toJson(addRoomDTO);

        mockMvc.perform(post(url)
                        .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void get_room_details() throws Exception{
        String url= "/services-rest/rooms/all";
        RegisterRequest registerRequest = fakeRegisterRequestBuilder().build();
        AccountEntity account = accountTestRepository.insertAccount(registerRequest);
        String token = jwtTokenUtil.generateToken(ACCOUNT_MAPPER.map(account));
        mockMvc.perform(get(url)
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }
}