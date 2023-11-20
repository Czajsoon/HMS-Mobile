import React from 'react';
import {Text, TouchableOpacity, View} from "react-native";
import {Entypo} from '@expo/vector-icons';
import {lightWhite, primaryColor, secondaryColor} from "../../shared/Utils";
import {Box, ScrollView} from "native-base";
import {SafeAreaView} from "react-native-safe-area-context";
import Toast from "react-native-toast-message";


export default function MainNavigation({navigation, children}) {

    return (
        <>
            <SafeAreaView
                edges={['top']}
                style={{
                    backgroundColor: primaryColor,
                    flexDirection: 'row',
                    justifyContent: 'space-between',
                    paddingBottom: 5,
                    borderStyle: 'solid',
                    borderColor: secondaryColor,
                    borderBottomWidth: 5
            }}
            >
                <TouchableOpacity style={{marginLeft: 10}} onPress={() => navigation.openDrawer()}>
                    <Entypo
                        name="menu"
                        size={55}
                        color={lightWhite}
                    />
                </TouchableOpacity>
                <View style={{justifyContent: 'center', alignItems: 'center', margin: 'auto'}}>
                    <Text style={{fontSize: 30, color: lightWhite, marginRight: 20}}>
                        HMS
                    </Text>
                </View>
            </SafeAreaView>
            <ScrollView>
                {children}
            </ScrollView>
            <Toast/>
        </>

    );
}

