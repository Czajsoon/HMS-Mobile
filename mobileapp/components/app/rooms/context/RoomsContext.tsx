import React, {createContext, useContext, useEffect, useState} from "react";
import {getRoomStatus, RoomProps, RoomsRequestParams, RoomsResponse, RoomStatus} from "../models/RoomsModels";
import axios, {AxiosResponse} from "axios/index";
import {apiUrl} from "../../../shared/Utils";
import Toast from "react-native-toast-message";

const RoomContext = createContext<RoomProps>({})

export const useRoom = () => {
    return useContext(RoomContext);
}

export const RoomProvider = ({children}: any) => {
    const [rooms, setRooms] = useState<RoomsResponse>({})
    const [request, setRequest] = useState({})
    const [refreshing, setRefreshing] = useState(false);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0)


    const getRooms = async (request: RoomsRequestParams = {}) => {
        await axios.get(`${apiUrl}services-rest/rooms/all`, {params: request}).then(
            (response: AxiosResponse<RoomsResponse>) => {
                setRooms(response.data)
                setRequest(request)
                console.log(response.data)
                console.log(request)
            }
        ).catch(error => {
            Toast.show({
                type: 'error',
                text1: 'Błąd',
                text2: error.response.data,
                position: 'bottom'
            });
        })
    }

    const updateRoomStatus = async (roomId: string, roomStatus: RoomStatus) => {
        await axios.patch(
            `${apiUrl}services-rest/rooms/status/${roomId}/${roomStatus}`
        ).then(() => {
            onRefresh()
            Toast.show({
                type: 'success',
                text1: 'Sukces!',
                text2: `Zmiana na ${getRoomStatus(roomStatus)}`,
                position: 'bottom',
            });
        }).catch(error => {
            Toast.show({
                type: 'error',
                text1: 'Błąd',
                text2: error.response.data,
                position: 'bottom'
            });
        })
    }

    useEffect(() => {
        getRooms({...request, page: currentPage})
    }, []);

    const onRefresh = () => {
        setRefreshing(true);
        setCurrentPage(0)
        getRooms({...request, page: 0}).finally(() => setRefreshing(false));
    };

    const value: RoomProps = {
        getRooms: getRooms,
        rooms: rooms.rooms,
        updateRoomStatus: updateRoomStatus
    };

    // @ts-ignore
    return <RoomContext.Provider value={value}>
        {children}
    </RoomContext.Provider>
}
