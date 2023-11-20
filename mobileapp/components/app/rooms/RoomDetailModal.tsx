import React, {useEffect, useState} from "react";
import {Modal, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {lightWhite, primaryColor, screenWidth} from "../../shared/Utils";
import DisplayItem from "../../shared/DisplayItem";
import {getRoomStatus, Room} from "./models/RoomsModels";

export default function RoomDetailModal({isVisible, closeModal, room}) {

    const [actualRoom, setActualRoom] = useState<Room>(room)

    useEffect(() => {
        setActualRoom(room)
    }, [room]);

    return (
        <Modal
            transparent={true}
            animationType="fade"
            visible={isVisible}
        >
            <View style={styles.modalContainer}>
                <View style={styles.modalContent}>
                    <Text style={{textAlign: 'center', marginBottom: 20, marginTop: 20}}>Detale pokoju</Text>
                    <DisplayItem label={'Id: '}>
                        <Text>{actualRoom?.id}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Piętro: '}>
                        <Text>{actualRoom?.level}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Status: '}>
                        <Text>{getRoomStatus(actualRoom?.roomStatus)}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Cena za noc: '}>
                        <Text>{actualRoom?.pricePerNight}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Czy posiada balkon: '}>
                        <Text>{actualRoom?.balconyAvailable ? 'Tak' : 'Nie'}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Ilość łóżek: '}>
                        <Text>{actualRoom?.bedsNumber}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Opis: '}>
                        <Text>{actualRoom?.description}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Dodatkowy komentarz: '}>
                        <Text>{actualRoom?.comments}</Text>
                    </DisplayItem>
                    <View style={styles.actionButtons}>
                        <TouchableOpacity style={styles.actionButtons.button} onPress={() => closeModal()}>
                            <Text style={styles.actionButtons.button.text}>Zamknij</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </View>
        </Modal>
    )
}

const styles = StyleSheet.create({
    modalContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
    },
    modalContent: {
        backgroundColor: 'white',
        padding: 5,
        borderRadius: 10,
        width: screenWidth * 0.9,
        height: 500,
        dropdown: {
            height: 35,
            marginBottom: 35
        }
    },
    dropdownBox: {
        flex: 1,
        flexDirection: 'row',
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
            flexBasis: '60%'
        }
    },
    actionButtons: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'center',
        button: {
            backgroundColor: primaryColor,
            height: 25,
            width: 100,
            marginLeft: 30,
            marginRight: 30,
            textAlign: 'center',
            justifyContent: 'center',
            alignItems: 'center',
            borderRadius: 20,
            text: {
                textAlign: 'center',
                color: lightWhite
            }
        }
    }
});
