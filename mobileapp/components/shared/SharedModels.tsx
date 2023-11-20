export interface DropdownData {
    label: string,
    value: any
}

export enum EYesNo{

}

export const YesNoDropdownData: DropdownData[] = [
    {
        label: "Tak",
        value: true
    },
    {
        label: "Nie",
        value: false
    },
]
