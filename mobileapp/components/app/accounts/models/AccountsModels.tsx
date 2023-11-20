import {Role} from "../../../auth/AuthContextModels";
import {DropdownData} from "../../../shared/SharedModels";

export interface AccountProps {
    getAccounts?: (request: AccountsRequestParams) => void,
    accounts?: Account[] | null,
    cachedRequest?: AccountsRequestParams | null,
    nextPage?: () => void,
    previousPage?: () => void,
    deleteAccount?: (accountId: string) => void,
    addAccount?: (addRequest: AddAccountRequest) => void
}

export interface AccountsResponse {
    totalElements?: number | null,
    totalPages?: number | null,
    accounts?: Account[] | null
}

export interface AccountsRequestParams {
    id?: string | null,
    username?: string | null,
    firstName?: string | null,
    secondName?: string | null,
    role?: Role | null,
    page?: number | null
}

export interface AddAccountRequest {
    username?: string,
    firstName?: string,
    secondName?: string,
    password?: string,
    email?: string,
    role?: Role,
}

export interface Account {
    id?: string | null,
    username?: string | null,
    firstName?: string | null,
    secondName?: string | null,
    role?: Role | null,
}

export const RolesDropdownData: DropdownData[] = [
    {
        label: "Administrator",
        value: Role.ADMIN
    },
    {
        label: "Pracownik",
        value: Role.EMPLOYEE
    }
]
