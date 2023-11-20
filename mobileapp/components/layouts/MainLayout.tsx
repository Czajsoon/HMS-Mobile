import {useAuth} from "../auth/AuthContext";
import {NavigationContainer} from "@react-navigation/native";
import LoginScreen from "../login/Login";
import React from "react";
import {createStackNavigator} from "@react-navigation/stack";
import {AppNavigation} from "./AppNavigation";

const Stack = createStackNavigator();

export const MainLayout = () => {
    const {authState, onLogout} = useAuth();
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName={'Login'}>
                {authState.authenticated ?
                    (<Stack.Screen name={'App'} component={AppNavigation} options={stackScreenOptions}/>)
                    :
                    (<Stack.Screen name={'Login'} component={LoginScreen} options={stackScreenOptions}/>)
                }
            </Stack.Navigator>
        </NavigationContainer>
    );
}

const stackScreenOptions = {
    headerShown: false
}



