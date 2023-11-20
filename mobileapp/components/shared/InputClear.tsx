import {StyleSheet, Text, TextInput, TouchableOpacity} from "react-native";
import {Box} from "native-base";
import {FontAwesome5} from "@expo/vector-icons";
import {lightBlack, primaryColor, secondaryColor} from "./Utils";
import React from "react";


export default function InputClear(
    {
        label,
        value,
        setMethod,
        secured = false,
        placeholder = '',
        inputType = "default",
        showClear = true
    }) {
    return (
        <Box style={styles.box}>
            {/*@ts-ignore*/}
            <Text style={styles.box.text}>{label}</Text>
            {/*@ts-ignore*/}
            <TextInput
                secureTextEntry={secured}
                value={value}
                placeholder={placeholder}
                placeholderTextColor={lightBlack}
                keyboardType={inputType}
                onChangeText={(inputText) => setMethod(inputText)}
                // @ts-ignore
                style={styles.box.input}/>
            {
                showClear ?
                    (
                        <>
                            {/*@ts-ignore*/}
                            <TouchableOpacity style={styles.box.clear} onPress={() => setMethod('')}>
                                <FontAwesome5 name="times" size={24} color={primaryColor}/>
                            </TouchableOpacity>
                        </>
                    )
                    :
                    (
                        <></>
                    )
            }
        </Box>
    );
}

const styles = StyleSheet.create({
    box: {
        marginBottom: 15,
        flex: 1,
        flexDirection: 'row',
        height: 25,
        text: {
            textAlign: 'center',
            margin: 'auto',
            marginRight: 20,
            justifyContent: 'center',
            alignItems: 'center',
            flexBasis: '30%'
        },
        input: {
            textAlign: 'center',
            flexBasis: '50%',
            borderStyle: 'solid',
            borderColor: secondaryColor,
            borderRadius: 20,
            borderWidth: 1,
        },
        clear: {
            flexBasis: '20%',
            justifyContent: 'center',
            alignItems: 'center',
        }
    }
})
