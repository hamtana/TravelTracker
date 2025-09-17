import type { ServiceProvider } from "./serviceProviderApi";
import type { Accommodation } from "./accommodationApi";

export interface Booking {
    dateOfDeparture: Date;
    destination: String;
    bookingStatus: String;
    estimatedCost: number;
    estimatedCostForPatient : number;
    bookingCreatedAt: Date;
    serviceProviderId: ServiceProvider;
}