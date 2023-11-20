import RoomsScreen from "../app/rooms/Rooms";
import React from "react";
import {createDrawerNavigator} from "@react-navigation/drawer";
import {CustomDrawer} from "./customDrawer/CustomDrawer";
import {deepBlack, lightWhite} from "../shared/Utils";
import {useAuth} from "../auth/AuthContext";
import AccountsMain from "../app/accounts/AccountsMain";
import {Role} from "../auth/AuthContextModels";
import RoomsMain from "../app/rooms/RoomsMain";

const Drawer = createDrawerNavigator();

export const AppNavigation = () => {
    const {isAdmin} = useAuth();

    return (
        <Drawer.Navigator
            drawerContent={(props) => <CustomDrawer {...props} />}
        >
            <Drawer.Screen
                name={'Rooms'}
                component={RoomsMain}
                options={drawerScreenOptions('Pokoje')}/>
            {
                isAdmin ?
                    (
                        <>
                            <Drawer.Screen
                                name={'Accounts'}
                                component={AccountsMain}
                                options={drawerScreenOptions('Użytkownicy')}/>
                        </>
                    )
                    : (<></>)

            }
        </Drawer.Navigator>
    );
}

const drawerScreenOptions = (displayLabelName: string) => {
    return {
        headerShown: false,
        drawerActiveTintColor: deepBlack,
        drawerInactiveTintColor: lightWhite,
        drawerInactiveBackgroundColor: deepBlack,
        drawerActiveBackgroundColor: lightWhite,
        drawerLabel: displayLabelName
    }
}
