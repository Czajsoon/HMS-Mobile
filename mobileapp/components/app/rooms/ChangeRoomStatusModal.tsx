import React, {useEffect, useState} from "react";
import {Modal, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {lightWhite, primaryColor, primaryColorDisabled, screenWidth} from "../../shared/Utils";
import {RoomStatus, RoomStatusesDropdownData} from "./models/RoomsModels";
import DropdownClear from "../../shared/DropdownClear";
import {useRoom} from "./context/RoomsContext";

export default function ChangeRoomStatusModal({isVisible, closeModal, room}) {
    const {updateRoomStatus} = useRoom()
    const [roomStatus, setRoomStatus] = useState<RoomStatus>(room?.roomStatus)

    useEffect(() => {
        setRoomStatus(room?.roomStatus)
    }, [room]);

    return (
        <Modal
            transparent={true}
            animationType="fade"
            visible={isVisible}
        >
            <View style={styles.modalContainer}>
                <View style={styles.modalContent}>
                    <Text style={{textAlign: 'center', marginBottom: 20, marginTop: 20}}>Ustaw status pokoju</Text>
                    <View style={styles.modalContent.dropdown}>
                        <DropdownClear
                            data={RoomStatusesDropdownData}
                            value={roomStatus}
                            setMethod={setRoomStatus}
                            showClear={false}
                            label={'Status pokoju: '}
                        />
                    </View>
                    <View style={styles.actionButtons}>
                        <TouchableOpacity disabled={room?.roomStatus === roomStatus} onPress={() => {
                            updateRoomStatus(room?.id, roomStatus)
                            closeModal()
                        }}
                                          style={room?.roomStatus !== roomStatus ? styles.actionButtons.button : styles.actionButtons.buttonDisabled}>
                            <Text style={styles.actionButtons.button.text}>Zapisz</Text>
                        </TouchableOpacity>
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
        height: 180,
        dropdown:{
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
    actionButtons:{
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-between',
        button:{
            backgroundColor: primaryColor,
            height: 25,
            width: 100,
            marginLeft: 30,
            marginRight: 30,
            textAlign: 'center',
            justifyContent: 'center',
            alignItems: 'center',
            borderRadius: 20,
            text:{
                textAlign: 'center',
                color: lightWhite
            }
        },
        buttonDisabled:{
            backgroundColor: primaryColorDisabled,
            height: 25,
            width: 100,
            marginLeft: 30,
            marginRight: 30,
            textAlign: 'center',
            justifyContent: 'center',
            alignItems: 'center',
            borderRadius: 20,
            text:{
                textAlign: 'center',
                color: lightWhite
            }
        }
    }
});
