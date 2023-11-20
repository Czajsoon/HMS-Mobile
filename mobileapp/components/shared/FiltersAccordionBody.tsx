import React from "react";
import {StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {Box} from "native-base";
import {lightWhite, primaryColor} from "./Utils";

export default function FiltersAccordionBody({
                                                   children,
                                                   showButtons = true,
                                                   clearForm,
                                                   search
                                               }) {
    return (
        <View>
            {children}
            {
                showButtons ?
                    (
                        <Box style={styles.submitBox}>
                            <TouchableOpacity style={styles.submitBox.clear} onPress={() => clearForm()}>
                                <Text style={styles.submitBox.search.text}>Wyczyść</Text>
                            </TouchableOpacity>
                            <TouchableOpacity style={styles.submitBox.search} onPress={() => search()}>
                                <Text style={styles.submitBox.search.text}>Szukaj</Text>
                            </TouchableOpacity>
                        </Box>
                    )
                    :
                    (
                        <></>
                    )
            }
        </View>
    )
}

const styles = StyleSheet.create({
    submitBox: {
        flex: 1,
        flexDirection: 'row',
        marginTop: 20,
        marginBottom: 20,
        justifyContent: 'space-between',
        alignItems: 'center',
        clear: {
            textAlign: 'center',
            justifyContent: 'center',
            alignItems: 'center',
            flexBasis: '30%',
            height: 30,
            backgroundColor: primaryColor,
            borderRadius: 15,
            text: {
                color: lightWhite,
            }
        },
        search: {
            textAlign: 'center',
            justifyContent: 'center',
            alignItems: 'center',
            flexBasis: '30%',
            height: 30,
            backgroundColor: primaryColor,
            borderRadius: 15,
            text: {
                color: lightWhite,
            }
        }
    }
})
