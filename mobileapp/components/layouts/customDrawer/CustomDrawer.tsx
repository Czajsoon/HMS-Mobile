import {useAuth} from "../../auth/AuthContext";
import {StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {DrawerContentScrollView, DrawerItemList} from "@react-navigation/drawer";
import {deepBlack, lightWhite, screenHeight} from "../../shared/Utils";
import React from "react";

export const CustomDrawer = ({...props}) => {

    const {onLogout} = useAuth();

    return (
        <View style={styles.mainView}>
            <DrawerContentScrollView{...props} style={styles.drawerScrollView}>
                <View
                    onTouchEndCapture={() => props.navigation.closeDrawer()}
                    style={styles.drawerScrollView.title}
                >
                    {/*@ts-ignore*/}
                    <Text style={styles.drawerScrollView.title.text}>
                        HMS
                    </Text>
                </View>
                <View>
                    <DrawerItemList state={props.state} navigation={props.navigation} descriptors={props.descriptors} {...props}/>
                </View>
            </DrawerContentScrollView>
            <TouchableOpacity onPress={() => onLogout()} style={styles.logoutButton}>
                {/*@ts-ignore*/}
                <Text style={styles.logoutButton.text}>
                    Wyloguj
                </Text>
            </TouchableOpacity>
        </View>

    );
};

const styles = StyleSheet.create({
    mainView: {
        flex: 1
    },
    drawerScrollView: {
        backgroundColor: deepBlack,
        title: {
            marginBottom: 20,
            text:{
                textAlign: 'center',
                fontSize: 30,
                color: lightWhite
            }
        }
    },
    logoutButton:{
        backgroundColor: deepBlack,
        text:{
            color: 'white',
            marginRight: 'auto',
            marginLeft: 'auto',
            justifyContent: 'center',
            marginBottom: screenHeight * 0.05
        }
    }
})
