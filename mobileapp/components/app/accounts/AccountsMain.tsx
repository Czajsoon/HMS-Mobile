import MainNavigation from "../navigation/MainNavigation";
import {Text} from "react-native";
import React from "react";
import {AccountProvider} from "./context/AccountContext";
import AccountsScreen from "./Accounts";

export default function AccountsMain({navigation}) {

    return (
        <MainNavigation navigation={navigation}>
            <AccountProvider>
                <AccountsScreen/>
            </AccountProvider>
        </MainNavigation>
    );
}
