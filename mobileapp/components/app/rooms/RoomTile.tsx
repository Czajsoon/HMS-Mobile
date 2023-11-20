import React from "react";
import {ListRenderItemInfo, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {getRoomStatus, Room} from "./models/RoomsModels";
import DisplayItem from "../../shared/DisplayItem";
import {lightWhite, primaryColor} from "../../shared/Utils";

export default function RoomsTile(
    room: ListRenderItemInfo<Room>,
    openChangeRoomStatusModal: { (): void; (): void; },
    openDetailRoomModal: { (): void; (): void; },
    setActualRoom: { (value: React.SetStateAction<Room>): void; (arg0: Room): void; }
) {

    return (
        <TouchableOpacity style={styles.tile} onPress={() => {
            setActualRoom(room.item)
            openDetailRoomModal()
        }}>
            <View style={styles.tile.element}>
                <DisplayItem label={'Id: '}>
                    <Text>{room.item.id}</Text>
                </DisplayItem>
                <View style={styles.tile.element.doubleValue}>
                    <DisplayItem label={'Piętro: '}>
                        <Text>{room.item.level}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Status: '}>
                        <Text>{getRoomStatus(room.item.roomStatus)}</Text>
                    </DisplayItem>
                </View>
                <View style={styles.tile.element.doubleValue}>
                    <DisplayItem label={'Cena za noc: '}>
                        <Text>{room.item.pricePerNight}</Text>
                    </DisplayItem>
                    <DisplayItem label={'Czy jest balkon: '}>
                        <Text>{room.item.balconyAvailable ? 'Tak' : 'Nie'}</Text>
                    </DisplayItem>
                </View>
                <DisplayItem label={'Liczba łóżek: '}>
                    <Text>{room.item.bedsNumber}</Text>
                </DisplayItem>
                <View style={styles.tile.actionButton}>
                    <TouchableOpacity style={styles.tile.actionButton.button} onPress={() => {
                        setActualRoom(room.item)
                        openChangeRoomStatusModal()
                    }}>
                        <Text style={styles.tile.actionButton.button.text}>Edytuj</Text>
                    </TouchableOpacity>
                </View>

            </View>

        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    tile: {
        flex: 1,
        margin: 12,
        backgroundColor: lightWhite,
        borderColor: primaryColor,
        borderWidth: 2,
        borderStyle: 'solid',
        borderRadius: 20,
        element: {
            flex: 1,
            margin: 8,
            doubleValue: {
                flex: 1,
                flexDirection: 'row'
            }
        },
        actionButton: {
            flex: 1,
            flexDirection: 'row',
            justifyContent: 'center',
            button: {
                backgroundColor: primaryColor,
                height: 25,
                width: 140,
                marginTop: 10,
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
    }
})
