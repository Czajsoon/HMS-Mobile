import React, {useEffect, useState} from 'react';
import {StyleSheet, Text, TextInput, TouchableOpacity, View} from 'react-native';
import {apiUrl, lightWhite, primaryColor, screenHeight, screenWidth, secondaryColor} from "../shared/Utils";
import {Box} from "native-base";
import WelcomeScreen from "../shared/WelcomeHeader";
import {SafeAreaView} from "react-native-safe-area-context";
import axios from 'axios';
import Toast from "react-native-toast-message";
import AsyncStorage from "@react-native-async-storage/async-storage";
import {useAuth} from "../auth/AuthContext";

export default function LoginScreen({navigation}) {
    const [login, setLoginValue] = useState('');
    const [password, setPasswordValue] = useState('');
    const {onLogin} = useAuth();


    return (
        <><SafeAreaView>
            <View style={styles.container}>
                <WelcomeScreen></WelcomeScreen>
                <View>
                    <Box style={styles.titleBox}>
                        <Text style={styles.titleBox.titleText}>Logowanie</Text>
                    </Box>
                    <Box style={styles.inputBox}>
                        <Text style={styles.inputBox.inputText}>Login</Text>
                        <TextInput
                            value={login}
                            onChangeText={(loginText) => setLoginValue(loginText)}
                            // @ts-ignore
                            style={styles.inputBox.input}
                        />
                    </Box>
                    <Box style={styles.inputBox}>
                        <Text style={styles.inputBox.inputText}>Hasło</Text>
                        <TextInput
                            secureTextEntry={true}
                            value={password}
                            onChangeText={(passwordText) => setPasswordValue(passwordText)}
                            // @ts-ignore
                            style={styles.inputBox.input}/>
                    </Box>
                    <Box style={styles.submitBox}>
                        <TouchableOpacity
                            onPress={() => onLogin(login, password)}
                            //@ts-ignore
                            style={styles.submitBox.submitButton}
                            disabled={!login || !password}
                        >
                            <Text style={styles.submitBox.submitButton.text}>Zaloguj się</Text>
                        </TouchableOpacity>
                    </Box>
                </View>
            </View>
        </SafeAreaView>
            <Toast/>
        </>
    );
}

const styles = StyleSheet.create({
    container: {
        height: screenHeight,
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center'
    },
    loginContainer: {
        height: screenHeight * 0.8
    },
    inputBox: {
        height: 70,
        margin: '2%',
        justifyContent: 'center',
        alignItems: 'center',
        inputText: {
            height: 25,
            fontSize: 22,
            color: 'black',
        },
        input: {
            height: 40,
            width: screenWidth * 0.8,
            margin: 10,
            borderColor: primaryColor,
            borderRadius: 10,
            borderWidth: 1,
            padding: 0,
            color: secondaryColor,
            textAlign: 'center',
        },
    },
    titleBox: {
        height: 100,
        padding: 30,
        justifyContent: 'center',
        alignItems: 'center',
        titleText: {
            fontSize: 30,
            color: 'black',
        },
    },
    submitBox:{
        marginTop: 30,
        alignItems: 'center',
        justifyContent: 'center',
        submitButton: {
            backgroundColor: primaryColor,
            color: secondaryColor,
            borderRadius: 15,
            height: 60,
            textAlign: 'center',
            width: screenWidth * 0.8,
            marginTop: 40,
            alignItems: 'center',
            justifyContent: 'center',
            text:{
                fontSize: 25,
                color: lightWhite
            }
        }
    }
});
