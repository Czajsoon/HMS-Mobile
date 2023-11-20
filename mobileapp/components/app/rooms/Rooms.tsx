import React from 'react';
import {Text, View} from "react-native";
import AccordionComponent from "../../shared/AccorditionComponent";
import RoomsFilters from "./RoomsFilters";
import RoomsList from "./RoomsList";

export default function RoomsScreen() {

    return (
        <View>
            <AccordionComponent title={'Filtry'}>
                <RoomsFilters/>
            </AccordionComponent>
            <RoomsList/>
        </View>
    );
}
