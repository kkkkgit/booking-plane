export enum SeatClass {
    FIRST_CLASS = "FIRST_CLASS",
    BUSINESS_CLASS = "BUSINESS_CLASS",
    ECONOMY_CLASS = "ECONOMY_CLASS"
}

export interface Seat {
    id: number;
    seatNumber: string;
    row: number;
    column: string;
    isWindowSeat: boolean;
    isAisleSeat: boolean;
    hasExtraLegroom: boolean;
    isExitRowSeat: boolean;
    isReserved: boolean;
    flightId?: number;
    seatClass: SeatClass;
}

export interface SeatPreferences {
    wantsWindowSeat: boolean;
    wantsAisleSeat: boolean;
    wantsExtraLegroom: boolean;
    wantsExitRowSeat: boolean;
    preferredClass?: SeatClass;
}