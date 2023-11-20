import {StyleSheet, Text, View} from "react-native";
import React from "react";

export default function DisplayItem({children, label}){
    return(
        <View style={styles.item}>
            <Text>{label}</Text>
            {children}
        </View>
    )
}

const styles = StyleSheet.create({
    item:{
        flex: 1,
        flexDirection: "row",
        margin: 10
    }
})
