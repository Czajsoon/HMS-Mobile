import React from "react";
import {StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {Dropdown} from "react-native-element-dropdown";
import {FontAwesome5} from "@expo/vector-icons";
import {primaryColor} from "./Utils";

export default function DropdownClear({
                                          data = [],
                                          value,
                                          setMethod,
                                          label = "",
                                          placeholder = "",
                                          showClear = true
                                      }) {

    const styles = StyleSheet.create({
        dropdownBox: {
            flex: 1,
            flexDirection: 'row',
            marginTop: 5,
            marginBottom: 10,
            label: {
                textAlign: 'center',
                margin: 'auto',
                marginRight: 20,
                justifyContent: 'center',
                alignItems: 'center',
                flexBasis: '30%'
            },
            dropdown: {
                textAlign: 'center',
                marginRight: 20,
                justifyContent: 'center',
                alignItems: 'center',
                flexBasis: showClear ? '45%' : '60%'
            },
            clear: {
                flexBasis: '20%',
                justifyContent: 'center',
                alignItems: 'center',
            }
        }
    })

    return (
        <View style={styles.dropdownBox}>
            {/*@ts-ignore*/}
            <Text style={styles.dropdownBox.label}>{label}</Text>
            {/*@ts-ignore*/}
            <Dropdown
                // @ts-ignore
                style={styles.dropdownBox.dropdown}
                data={data}
                placeholder={placeholder}
                labelField={'label'}
                valueField={'value'}
                value={value}
                onChange={item => {
                    setMethod(item.value);
                }}/>
            {
                showClear ?
                    (
                        <>
                            {/*@ts-ignore*/}
                            <TouchableOpacity style={styles.dropdownBox.clear} onPress={() => setMethod(null)}>
                                <FontAwesome5 name="times" size={24} color={primaryColor}/>
                            </TouchableOpacity>
                        </>
                    )
                    :
                    (
                        <></>
                    )
            }
        </View>
    )
}


