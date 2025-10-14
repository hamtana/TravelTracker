import type { Booking } from "./bookingApi";
import type { Patient } from "./patientApi";

export interface Notes{
    id?: number;
    message: string;
    booking?: Booking;
    patientNhi?: Patient;
}

