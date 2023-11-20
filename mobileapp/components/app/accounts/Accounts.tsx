import {StyleSheet, Text, TouchableOpacity, View} from "react-native";
import React, {useState} from "react";
import AccountsTable from "./AccountsTable";
import {Box} from "native-base";
import {AntDesign} from "@expo/vector-icons";
import {useAccount} from "./context/AccountContext";
import {lightWhite, primaryColor, screenWidth, secondaryColor} from "../../shared/Utils";
import AccountAddModal from "./AccountAddModal";
import AccordionComponent from "../../shared/AccorditionComponent";
import AccountFilterForm from "./AccountFiltersForm";

export default function AccountsScreen() {
    const {previousPage, nextPage, accounts} = useAccount();
    const [editMode, setEditMode] = useState(false)

    return (
        <View style={{flex: 1, justifyContent: "center", alignItems: 'center'}}>
            <AccordionComponent title={'Filtry'}>
                <AccountFilterForm/>
            </AccordionComponent>
            {
                accounts.length > 0 ?
                    (
                        <>
                            <AccountsTable/>
                            <Box
                                style={{flexDirection: 'row', justifyContent: 'space-between', margin: 20}}>
                                <TouchableOpacity onPress={() => previousPage()}>
                                    <AntDesign name="arrowleft" size={35} color={primaryColor}/>
                                </TouchableOpacity>
                                <TouchableOpacity onPress={() => nextPage()}>
                                    <AntDesign name="arrowright" size={35} color={primaryColor}/>
                                </TouchableOpacity>
                            </Box>
                        </>
                    ) :
                    (
                        <Text>Nie ma kont do wyświetlenia</Text>
                    )
            }
            <TouchableOpacity
                style={styles.addAccountButton}
                onPress={() => setEditMode(true)}>
                <Text style={styles.addAccountButton.text}>Dodaj konto</Text>
            </TouchableOpacity>
            <AccountAddModal
                isVisible={editMode}
                closeModal={() => setEditMode(false)}/>
        </View>
    );
}

const styles = StyleSheet.create({
    addAccountButton: {
        backgroundColor: primaryColor,
        color: secondaryColor,
        borderRadius: 15,
        height: 40,
        textAlign: 'center',
        width: screenWidth * 0.8,
        marginTop: 40,
        alignItems: 'center',
        justifyContent: 'center',
        text: {
            fontSize: 25,
            color: lightWhite
        }
    }
});
