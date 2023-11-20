import React from 'react';
import {StyleSheet, Text, View} from 'react-native';
import {primaryColor, screenHeight, screenWidth, secondaryColor} from "./Utils";
import {Box} from "native-base";

export default function WelcomeScreen() {
    return (
        <View style={styles.header}>
            <Box style={styles.titleBox}>
                <Text style={styles.title}>Hotel{'\n'}Management{'\n'}System</Text>
            </Box>
        </View>
    );
}


const styles = StyleSheet.create({
    header: {
        width: screenWidth,
        height: screenHeight * 0.35,
        backgroundColor: primaryColor,
        alignItems: 'center',
        justifyContent: 'center',
        borderStyle: 'solid',
        borderColor: secondaryColor,
        borderBottomWidth: 3
    },
    titleBox: {
        marginTop: '40%',
        alignItems: 'center',
        justifyContent: 'center'
    },
    title: {
        textAlign: "center",
        fontSize: 30,
        color: 'white',
    }
});
