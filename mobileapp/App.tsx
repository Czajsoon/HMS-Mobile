import {NativeBaseProvider} from "native-base";
import {SafeAreaProvider} from "react-native-safe-area-context";
import React from "react";
import {AuthProvider} from "./components/auth/AuthContext";
import {MainLayout} from "./components/layouts/MainLayout";


export default function App() {
    return (
        <NativeBaseProvider>
            <SafeAreaProvider>
                <AuthProvider>
                    <MainLayout></MainLayout>
                </AuthProvider>
            </SafeAreaProvider>
        </NativeBaseProvider>
    );
}
