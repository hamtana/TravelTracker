import type { ServiceProvider } from "./serviceProviderApi";
import useFetch from "../useAuthenticatedFetch";
import type { Accommodation } from "./accommodationApi";


export interface Booking {
    id?: number; 
    dateOfDeparture: Date;
    dateOfReturn: Date;
    destination: String;
    bookingStatus: String;
    patientNhi: string;
    estimatedCost: number;
    estimatedCostForPatient : number;
    bookingCreatedAt: Date;
    serviceProviderId: ServiceProvider;
    accommodationAddress : Accommodation | null;
}

const API_BASE = "http://localhost:8080/api/bookings";

export function BookingApi() {
    const authenticatedFetch = useFetch();


    const addBooking = async (booking : Booking): Promise<Booking> => {
        return authenticatedFetch(API_BASE, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(booking),
        });
    };

    const deleteBookingById = async (id: string): Promise<void> => {
        return authenticatedFetch(`${API_BASE}/${id}`, {
            method: "DELETE",
        });
    }

    const getBookingById = async (id: string): Promise<Booking> => {
        try{
            return await authenticatedFetch(`${API_BASE}/${id}`);
        } catch (err: any) {
            if (err.message.includes("404")) {
                throw new Error("No booking found with that ID");
            }
            throw err;
        };
    };

    const getAllBookings = async (): Promise<Booking[]> => {
        return authenticatedFetch(API_BASE);
    };

    const updateBookingById = async (id: string, booking : Booking): Promise<Booking> => {
        return authenticatedFetch(`${API_BASE}/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(booking),
        });
    };

    return {
        addBooking,
        deleteBookingById,
        getBookingById,
        getAllBookings,
        updateBookingById,
    };
}
