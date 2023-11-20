import React, {useEffect, useState} from "react";
import {Modal, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {screenWidth} from "../../shared/Utils";
import {Box} from "native-base";
import InputClear from "../../shared/InputClear";
import {Role} from "../../auth/AuthContextModels";
import {Dropdown} from "react-native-element-dropdown";
import {useAccount} from "./context/AccountContext";

export default function AccountAddModal({isVisible, closeModal}) {
    const {addAccount} = useAccount();
    const [username, setUsername] = useState('')
    const [firstname, setFirstname] = useState('')
    const [secondName, setSecondName] = useState('')
    const [password, setPassword] = useState('')
    const [email, setEmail] = useState('')
    const [role, setRole] = useState(null);
    const [formValid, setFormValid] = useState(false);

    useEffect(() => {
        setFormValid(username === '' && firstname === '' && secondName === '' && password === '' && email === '' && role === null)
    }, [username, firstname, secondName, password, email, role])

    const processAddAccount = () => {
        addAccount({
            username: username,
            firstName: firstname,
            secondName: secondName,
            password: password,
            email: email,
            role: role
        })
    }

    const clearForm = () => {
        setUsername('')
        setFirstname('')
        setSecondName('')
        setPassword('')
        setEmail('')
        setRole(null)
    }

    return (
        <Modal
            transparent={true}
            animationType="fade"
            visible={isVisible}
        >
            <View style={styles.modalContainer}>
                <View style={styles.modalContent}>
                    <Text style={{textAlign: 'center', marginBottom: 20, marginTop: 20}}>Dodaj konto</Text>
                    <Box style={{height: 200, margin: 'auto'}}>
                        <InputClear text={'Login:'} value={username} setMethod={setUsername} placeholder={"wpisz login"}></InputClear>
                        <InputClear text={'Hasło:'} value={password} setMethod={setPassword}
                                    secured={true} placeholder={"wpisz hasło"}></InputClear>
                        <InputClear text={'Imię:'} value={firstname} setMethod={setFirstname} placeholder={"wpisz imię"}></InputClear>
                        <InputClear text={'Nazwisko:'} value={secondName} setMethod={setSecondName} placeholder={"wpisz nazwisko"}></InputClear>
                        <InputClear text={'Email:'} value={email} setMethod={setEmail} placeholder={"wpisz email"}></InputClear>
                        <Box style={styles.dropdownBox}>
                            <Text style={styles.dropdownBox.label}>Rola: </Text>
                            <Dropdown
                                style={styles.dropdownBox.dropdown}
                                data={[
                                    {label: "Administrator", value: Role.ADMIN},
                                    {label: "Pracownik", value: Role.EMPLOYEE}
                                ]}
                                placeholder="Wybierz rolę"
                                labelField={'label'}
                                valueField={'value'}
                                value={role}
                                onConfirmSelectItem={item => {
                                    setRole(item.value);
                                }}
                                onChange={item => {
                                    setRole(item.value);
                                }}/>
                        </Box>
                    </Box>
                    <Box style={{
                        flex: 1,
                        flexDirection: 'row',
                        justifyContent: 'space-between',
                        marginTop: 25,
                        margin: 15
                    }}>
                        <TouchableOpacity onPress={() => closeModal()}>
                            <Text style={{textAlign: 'center'}}>Zamknij</Text>
                        </TouchableOpacity>
                        <TouchableOpacity onPress={() => clearForm()}>
                            <Text style={{textAlign: 'center'}}>Wyczyść</Text>
                        </TouchableOpacity>
                        <TouchableOpacity disabled={formValid} onPress={() => processAddAccount()}>
                            <Text style={{textAlign: 'center'}}>Dodaj</Text>
                        </TouchableOpacity>
                    </Box>
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
        height: 330
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
});
