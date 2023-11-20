import React, {useState} from "react";
import InputClear from "../../shared/InputClear";
import DropdownClear from "../../shared/DropdownClear";
import {YesNoDropdownData} from "../../shared/SharedModels";
import {RoomStatus, RoomStatusesDropdownData} from "./models/RoomsModels";
import FiltersAccordionBody from "../../shared/FiltersAccordionBody";
import {useRoom} from "./context/RoomsContext";

export default function RoomsFilters() {
    const {getRooms} = useRoom()
    const [level, setLevel] = useState<number>(null)
    const [balcony, setBalcony] = useState<boolean>(null)
    const [roomStatus, setRoomStatus] = useState<RoomStatus>(null)
    const [pricePerNight, setPricePerNight] = useState<number>(null)
    const [bedsNumber, setBedsNumber] = useState<number>(null)

    const clearForm = () => {
        setLevel(null)
        setBalcony(null)
        setRoomStatus(null)
        setPricePerNight(null)
        setBedsNumber(null)
    }

    const search = () => {
        getRooms({
            balconyAvailable: balcony,
            level: level ? level : null,
            roomStatus: roomStatus ? roomStatus : null,
            pricePerNight: pricePerNight ? pricePerNight : null,
            bedsNumber: bedsNumber ? bedsNumber : null
        })
    }

    return (
        <FiltersAccordionBody search={search} clearForm={clearForm}>
            <DropdownClear data={YesNoDropdownData} label={"Balkon: "} placeholder={"Balkon dostępny"} value={balcony}
                           setMethod={setBalcony}/>
            <InputClear label={'Piętro:'} value={level} setMethod={setLevel} placeholder={"Wpisz piętro"}
                        inputType={"numeric"}></InputClear>
            <DropdownClear data={RoomStatusesDropdownData} label={"Status: "} placeholder={"Wybierz status"}
                           value={roomStatus} setMethod={setRoomStatus}/>
            <InputClear label={'Cena za noc:'} value={pricePerNight} setMethod={setPricePerNight}
                        placeholder={"Wpisz cenę za noc"} inputType={"numeric"}></InputClear>
            <InputClear label={'Liczba łóżek:'} value={bedsNumber} setMethod={setBedsNumber}
                        placeholder={"Wpisz liczbę łóżek"} inputType={"numeric"}></InputClear>
        </FiltersAccordionBody>
    )
}
