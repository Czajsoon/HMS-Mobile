export interface AuthProps {
    authState?: { token: string | null, authenticated: boolean | null };
    details?: AccountDetails | null,
    isAdmin?: boolean | null,
    onLogin?: (login: string, password: string) => Promise<any>;
    onLogout?: () => Promise<any>;
}

export interface AccountDetails {
    firstname?: string | null,
    secondName?: string | null,
    role?: Role | null
}

export const DefaultAccountDetails: AccountDetails = {
    firstname: null,
    secondName: null,
    role: null
}

export enum Role {
    ADMIN = 'ADMIN',
    EMPLOYEE = 'EMPLOYEE'
}
