import {Box, FlatList} from "native-base";
import React, {useState} from "react";
import {Alert, Text, TouchableOpacity, View} from "react-native";
import {useAccount} from "./context/AccountContext";
import {Role} from "../../auth/AuthContextModels";
import {AntDesign} from "@expo/vector-icons";
import {primaryColor} from "../../shared/Utils";
import AccountAddModal from "./AccountAddModal";
import {Account} from "./models/AccountsModels";

export default function AccountsTable() {
    const {accounts,deleteAccount} = useAccount();


    const renderItem = ({item}) => (
        <View style={{
            flex: 1,
            flexDirection: 'row',
            justifyContent: 'space-between',
            width: '90%',
            alignItems: 'center',
            paddingBottom: 20
        }}>
            <Text style={{flexBasis: '20%', textAlign: 'center'}}>{item.firstName}</Text>
            <Text style={{flexBasis: '20%', textAlign: 'center'}}>{item.secondName}</Text>
            <Text style={{flexBasis: '20%', textAlign: 'center'}}>{item.username}</Text>
            <Text style={{flexBasis: '20%', textAlign: 'center'}}>{item.role === Role.ADMIN ? "A" : "P"}</Text>
            <Box style={{flexBasis: '20%', flex: 1, flexDirection: 'row', justifyContent: 'center'}}>
                <TouchableOpacity onPress={() => deleteAccount(item.id)}>
                    <AntDesign name="delete" size={24} color={primaryColor}/>
                </TouchableOpacity>
            </Box>
        </View>
    );

    return (
        <>
            <View style={{
                flex: 1,
                flexDirection: 'row',
                justifyContent: 'space-between',
                width: '90%',
                alignItems: 'center',
                paddingBottom: 20
            }}>
                <Text style={{flexBasis: '20%', textAlign: 'center'}}>Imię</Text>
                <Text style={{flexBasis: '20%', textAlign: 'center'}}>Nazwisko</Text>
                <Text style={{flexBasis: '20%', textAlign: 'center'}}>Login</Text>
                <Text style={{flexBasis: '20%', textAlign: 'center'}}>Rola</Text>
                <Text style={{flexBasis: '20%', textAlign: 'center'}}>Akcje</Text>
            </View>
            <FlatList
                data={accounts}
                renderItem={renderItem}
                keyExtractor={(item) => item.id}/>
        </>
    )
}
