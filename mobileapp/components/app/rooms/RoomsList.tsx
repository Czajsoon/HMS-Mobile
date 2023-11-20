import {FlatList} from "native-base";
import {useRoom} from "./context/RoomsContext";
import React, {useState} from "react";
import RoomsTile from "./RoomTile";
import ChangeRoomStatusModal from "./ChangeRoomStatusModal";
import {Room} from "./models/RoomsModels";
import RoomDetailModal from "./RoomDetailModal";

export default function RoomsList() {
    const {rooms} = useRoom()
    const [changeRoomStatusVisible, setChangeRoomStatusVisible] = useState(false)
    const [detailRoomVisible, setDetailRoomVisible] = useState(false)
    const [selectedRoom, setSelectedRoom] = useState<Room>(null)

    return (
        <>
            <FlatList data={rooms}
                      renderItem={(item) => RoomsTile(
                          item,
                          () => setChangeRoomStatusVisible(true),
                          () => setDetailRoomVisible(true),
                          setSelectedRoom
                      )}
                      keyExtractor={(item) => item.id}/>
            <RoomDetailModal
                isVisible={detailRoomVisible}
                room={selectedRoom}
                closeModal={() => {
                    setDetailRoomVisible(false);
                    setSelectedRoom(null)
                }}
            />
            <ChangeRoomStatusModal
                isVisible={changeRoomStatusVisible}
                room={selectedRoom}
                closeModal={() => {
                    setChangeRoomStatusVisible(false);
                    setSelectedRoom(null)
                }
            }/>
        </>
    )
}
