import React, {useState} from "react";
import InputClear from "../../shared/InputClear";
import {useAccount} from "./context/AccountContext";
import DropdownClear from "../../shared/DropdownClear";
import {RolesDropdownData} from "./models/AccountsModels";
import FiltersAccordionBody from "../../shared/FiltersAccordionBody";

export default function AccountFilterForm() {
    const {getAccounts} = useAccount();
    const [id, setId] = useState('')
    const [username, setUsername] = useState('')
    const [firstname, setFirstname] = useState('')
    const [secondName, setSecondName] = useState('')
    const [role, setRole] = useState(null);

    const clearForm = () => {
        setId('')
        setUsername('')
        setFirstname('')
        setSecondName('')
        setRole(null)
    }

    const search = () => {
        getAccounts({
            id: id ? id : null,
            username: username ? username : null,
            firstName: firstname ? firstname : null,
            secondName: secondName ? secondName : null,
            role: role ? role : null
        })
    }

    return (
        <FiltersAccordionBody clearForm={clearForm} search={search}>
            <InputClear label={'Identyfikator:'} value={id} setMethod={setId} placeholder={"wpisz id"}></InputClear>
            <InputClear label={'Login:'} value={username} setMethod={setUsername}
                        placeholder={"wpisz login"}></InputClear>
            <InputClear label={'Imię:'} value={firstname} setMethod={setFirstname}
                        placeholder={"wpisz imie"}></InputClear>
            <InputClear label={'Nazwisko:'} value={secondName} setMethod={setSecondName}
                        placeholder={"wpisz nazwisko"}></InputClear>
            <DropdownClear data={RolesDropdownData} label={"Rola: "} placeholder={"Wybierz rolę"} value={role}
                           setMethod={setRole}/>
        </FiltersAccordionBody>
    );
}
