import React, {createContext, useContext, useEffect, useState} from "react";
import {AccountProps, AccountsRequestParams, AccountsResponse, AddAccountRequest} from "../models/AccountsModels";
import axios from "axios";
import {apiUrl} from "../../../shared/Utils";
import Toast from "react-native-toast-message";
import {RefreshControl} from "react-native";
import {AxiosResponse} from "axios/index";

const AccountContext = createContext<AccountProps>({})

export const useAccount = () => {
    return useContext(AccountContext);
}

export const AccountProvider = ({children}: any) => {
    const [accounts, setAccounts] = useState([])
    const [request, setRequest] = useState({})
    const [refreshing, setRefreshing] = useState(false);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0)

    const getAccounts = async (request: AccountsRequestParams = {}) => {
        await axios.get(`${apiUrl}services-rest/account/admin/details/all`, {params: request})
            .then(async (response: AxiosResponse<AccountsResponse>) => {
                setAccounts(response.data.accounts)
                setTotalPages(response.data.totalPages)
                setRequest(request)
            }).catch((error) => {
                Toast.show({
                    type: 'error',
                    text1: 'Błąd',
                    text2: error.response.data,
                    position: 'bottom'
                });
            });
    }

    const previousPage = async () =>{
        if (currentPage - 1 >= 0){
            setCurrentPage((currentPage - 1))
        }
    }

    const nextPage = () =>{
        if (currentPage + 1 < totalPages){
            setCurrentPage(1)
        }
    }

    const addAccount = async (addRequest: AddAccountRequest) => {
        await axios.post(`${apiUrl}services-rest/authenticate/admin/register`, addRequest).then(
            () => {
                Toast.show({
                    type: 'success',
                    text1: 'Operacja powiodła się',
                    text2: "Konto zostało dodane",
                    position: 'bottom'
                });
                onRefresh()
            }
        ).catch(error => {
            Toast.show({
                type: 'error',
                text1: 'Błąd',
                text2: error.response.data,
                position: 'bottom'
            });
        })
    }

    const deleteAccount = async (accountId: string) => {
        await axios.delete(`${apiUrl}services-rest/account/admin/delete/${accountId}`).then(
            () => {
                Toast.show({
                    type: 'success',
                    text1: 'Operacja powiodła się',
                    text2: "Konto zostało usunięte",
                    position: 'bottom'
                });
                onRefresh()
            }
        ).catch(error => {
            Toast.show({
                type: 'error',
                text1: 'Błąd',
                text2: error.response.data,
                position: 'bottom'
            });
        })
    }

    useEffect(() => {
        getAccounts({...request, page: currentPage});
    }, [currentPage]);

    const onRefresh = () => {
        setRefreshing(true);
        setCurrentPage(0)
        getAccounts({...request, page: 0}).finally(() => setRefreshing(false));
    };

    const value: AccountProps = {
        getAccounts: getAccounts,
        accounts: accounts,
        cachedRequest: request,
        nextPage: nextPage,
        previousPage: previousPage,
        deleteAccount: deleteAccount,
        addAccount: addAccount
    };

    // @ts-ignore
    return <AccountContext.Provider value={value}>
        <RefreshControl refreshing={refreshing} onRefresh={onRefresh}/>
        {children}
    </AccountContext.Provider>
}
