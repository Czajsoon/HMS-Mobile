import MainNavigation from "../navigation/MainNavigation";
import React from "react";
import {RoomProvider} from "./context/RoomsContext";
import RoomsScreen from "./Rooms";

export default function RoomsMain({navigation}) {

    return (
        <MainNavigation navigation={navigation}>
            <RoomProvider>
                <RoomsScreen/>
            </RoomProvider>
        </MainNavigation>
    );
}
