import type { ServiceProvider } from "./serviceProviderApi";

export interface Booking {
    dateOfDeparture: Date;
    destination: String;
    bookingStatus: String;
    estimatedCost: number;
    estimatedCostForPatient : number;
    bookingCreatedAt: Date;
    serviceProviderId: ServiceProvider;
}