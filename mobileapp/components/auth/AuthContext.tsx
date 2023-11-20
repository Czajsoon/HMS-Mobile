import {createContext, useContext, useEffect, useState} from "react";
import axios, {AxiosResponse} from "axios/index";
import {ACCOUNT_DETAILS, apiUrl, TOKEN_KEY} from "../shared/Utils";
import Toast from "react-native-toast-message";
import * as SecureStore from 'expo-secure-store';
import {AccountDetails, AuthProps, DefaultAccountDetails, Role} from "./AuthContextModels";


const AuthContext = createContext<AuthProps>({});

export const useAuth = () => {
    return useContext(AuthContext);
}

export const AuthProvider = ({children}: any) => {
    const [authState, setAuthState] = useState<{
        token: string | null;
        authenticated: boolean | null
    }>({
        token: null,
        authenticated: null
    })

    const [details, setDetails] = useState<AccountDetails>(DefaultAccountDetails)

    useEffect(() => {
        const loadToken = async () => {
            const token = await SecureStore.getItemAsync(TOKEN_KEY);
            if (token) {
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                setAuthState({token: `Bearer ${token}`, authenticated: true});
                await accountDetails()
            }
        }
        loadToken();
    }, [])

    const login = async (login: string, password: string) => {
        await axios.post(
            `${apiUrl}services-rest/authenticate/login`,
            {
                login: login,
                password: password
            }).then(async (response) => {
            setAuthState({token: `Bearer ${response.data.token}`, authenticated: true});
            axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
            await SecureStore.setItemAsync(TOKEN_KEY, response.data.token);
            await accountDetails();
            return response;
        }).catch((error) => {
            Toast.show({
                type: 'error',
                text1: 'Błąd',
                text2: error.response.data,
                position: 'bottom'
            });
        });
    }

    const accountDetails = async () => {
        await axios.get(`${apiUrl}services-rest/account/details`)
            .then(async (response: AxiosResponse<AccountDetails>) => {
                await SecureStore.setItemAsync(ACCOUNT_DETAILS, JSON.stringify(response.data));
                setDetails(response.data)
            })
            .catch((error) => {
                Toast.show({
                    type: 'error',
                    text1: 'Błąd',
                    text2: error.response.data,
                    position: 'bottom'
                });
            })
    }

    const logout = async () => {
        await SecureStore.deleteItemAsync(TOKEN_KEY);
        axios.defaults.headers.common['Authorization'] = ``;
        setAuthState({token: null, authenticated: false});
    }

    const admin = (): boolean =>{
        return details.role === Role.ADMIN
    }

    const value: AuthProps = {
        authState,
        details: details,
        isAdmin: admin(),
        onLogin: login,
        onLogout: logout
    };

    // @ts-ignore
    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
