import {DropdownData} from "../../../shared/SharedModels";

export interface RoomProps {
    getRooms?: (request: RoomsRequestParams) => void,
    rooms?: Room[],
    updateRoomStatus?: (roomId: string, roomStatus: RoomStatus) => void,
}

export interface RoomsRequestParams {
    balconyAvailable?: boolean | null,
    level?: number | null,
    roomStatus?: RoomStatus | null,
    pricePerNight?: number | null,
    bedsNumber?: number | null,
    page?: number | null
}

export interface RoomsResponse {
    totalElements?: number | null,
    totalPages?: number | null,
    rooms?: Room[] | null
}

export interface Room {
    id?: string | null,
    level?: number | null,
    roomStatus?: RoomStatus | null,
    pricePerNight?: number | null,
    balconyAvailable?: boolean | null,
    bedsNumber?: number | null,
    description?: string | null,
    comments?: string | null,
}

export enum RoomStatus {
    DISABLED = 'DISABLED',
    AVAILABLE = 'AVAILABLE',
    TO_PREPARE = 'TO_PREPARE',
    OCCUPIED = 'OCCUPIED'
}

export const RoomStatusesDropdownData: DropdownData[] = [
    {
        label: "Niedostępny",
        value: RoomStatus.DISABLED
    },
    {
        label: "Dostępny",
        value: RoomStatus.AVAILABLE
    },
    {
        label: "Do przygotowania",
        value: RoomStatus.TO_PREPARE
    },
    {
        label: "Zajęty",
        value: RoomStatus.OCCUPIED
    }
]

export const getRoomStatus = (roomStatus: RoomStatus): string => {
    switch (roomStatus) {
        case RoomStatus.AVAILABLE:
            return 'Dostępny'
        case RoomStatus.OCCUPIED:
            return 'Zajęty'
        case RoomStatus.DISABLED:
            return 'Niedostępny'
        case RoomStatus.TO_PREPARE:
            return 'Do przygotowania'
        default:
            return 'Brak statusu'
    }
}
