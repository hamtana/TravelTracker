import { useEffect, useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import type { Booking } from "../api/bookingApi";
import { BookingApi } from "../api/bookingApi";
import type { Patient } from "../api/patientApi";
import type { Accommodation } from "../api/accommodationApi";
import { ServiceProviderApi, type ServiceProvider } from "../api/serviceProviderApi";

export function AddBooking() {

    const { addBooking } = BookingApi();
    const { getServiceProviders } = ServiceProviderApi();
    const [booking, setBooking] = useState<Booking | null>(null);
    const [patient, setPatient] = useState<Patient | null>(null);
    const [accommodation, setAccommodation] = useState<Accommodation | null>(null);
    const [serviceProviders, setServiceProviders] = useState<ServiceProvider[] | null>(null);
    const [serviceProvider, setServiceProvider] = useState<ServiceProvider | null>(null);

    useEffect(() => {

        // Retrieve the NHI from session storage.
        const storedNhi = sessionStorage.getItem("selectedNhi");
        
        const storedPatient = sessionStorage.getItem("storePatient");
        if (storedPatient) {
            setPatient(JSON.parse(storedPatient));
        } else {
            setPatient(null);
        }

        if (storedNhi) {

            const newBooking: Booking = {
                dateOfDeparture: "",
                dateOfReturn: "",
                destination: "",
                bookingStatus: "PENDING",
                patientNhi: patient ?? { } as Patient,
                estimatedCost: parseFloat("0.0"),
                estimatedCostForPatient: parseFloat("0.0"),
                bookingCreatedAt: new Date().toISOString(),
                serviceProvider: {
                    id: 0,
                    name: "",
                    address: ""
                },
                supportPersons: [],
                notes: [],
                accommodationAddress: {
                    address: "",
                    name: ""
                }
            };
            setBooking(newBooking);
        }
        
        const fetchServiceProviders = async () => {
            try {
                const providers = await getServiceProviders();
                setServiceProviders(providers);
            }
            catch (error) {
                console.error("Error fetching service providers:", error);
            }
        };
        
        fetchServiceProviders();
    

    }, []);



    return (
        <div className="min-h-screen bg-background text-foreground overflow-x-hidden">
            <ThemeToggle />
            <Navbar />
            <main>
                <SignedIn>
                    <div className="pt-20 text-center">
                        <h1 className="text-4xl font-bold mb-4">Add Booking</h1>
                        {patient.nhi && <p className="text-lg mb-8">for Patient NHI: <strong>{patient.nhi}</strong></p>}
                        {booking ? (
                            <div className="max-w-2xl mx-auto p-6 bg-card rounded-lg shadow-md">
                                <form className="space-y-4">
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Date of Departure</label>
                                        <input
                                            type="date"
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.dateOfDeparture}
                                            onChange={(e) => setBooking({ ...booking, dateOfDeparture: e.target.value })}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Date of Return</label>
                                        <input
                                            type="date"
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.dateOfReturn}
                                            onChange={(e) => setBooking({ ...booking, dateOfReturn: e.target.value })}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Destination</label>
                                        <input
                                            type="text"
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.destination}
                                            onChange={(e) => setBooking({ ...booking, destination: e.target.value })}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Estimated Cost</label>
                                        <input
                                            type="number"
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.estimatedCost}
                                            onChange={(e) => setBooking({ ...booking, estimatedCost: parseFloat(e.target.value) })}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Estimated Cost for Patient</label>
                                        <input
                                            type="number"
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.estimatedCostForPatient}
                                            onChange={(e) => setBooking({ ...booking, estimatedCostForPatient: parseFloat(e.target.value) })}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Service Provider Name</label>
                                        <select
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.serviceProvider.name}
                                            onChange={(e) => {
                                                const selectedProvider = serviceProviders?.find(
                                                    (sp) => sp.name === e.target.value
                                                );
                                                if (selectedProvider) {
                                                    setBooking({
                                                        ...booking,
                                                        serviceProvider: {
                                                            id: selectedProvider.id,
                                                            name: selectedProvider.name,
                                                            address: selectedProvider.address,
                                                        },
                                                    });
                                                }
                                            }}
                                        >
                                            <option value="">Select a service provider</option>
                                            {serviceProviders &&
                                                serviceProviders.map((sp) => (
                                                    <option key={sp.id} value={sp.name}>
                                                        {sp.name}
                                                    </option>
                                                ))}
                                        </select>
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Accommodation Name</label>
                                        <input
                                            type="text"
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.accommodationAddress?.name || ""}
                                            onChange={(e) => setBooking({ ...booking, accommodationAddress: { ...booking.accommodationAddress!, name: e.target.value } })}
                                        />
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium mb-1">Accommodation Address</label>
                                        <input
                                            type="text"
                                            className="w-full px-3 py-2 border border-gray-300 rounded-md"
                                            value={booking.accommodationAddress?.address || ""}
                                            onChange={(e) => setBooking({ ...booking, accommodationAddress: { ...booking.accommodationAddress!, address: e.target.value } })}
                                        />
                                    </div>
                                    <button
                                        type="button"
                                        className="w-full bg-primary text-white px-4 py-2 rounded-md hover:bg-primary-dark transition"
                                        onClick={async () => {
                                            try {
                                                console.log("Submitting booking:", booking);
                                                await addBooking(booking, nhi!);
                                                alert("Booking added successfully!");
                                                // Optionally, redirect or clear form here
                                            } catch (error) {
                                                console.error("Error adding booking:", error);
                                                alert("Failed to add booking. Please try again.");
                                            }
                                        }}
                                    >
                                        Add Booking
                                    </button>
                                </form>
                            </div>
                        ) : (
                            <p>Loading booking details...</p>
                        )}
                    </div>
                </SignedIn>
                <SignedOut>
                    <SignedOutComponent />
                </SignedOut>
            </main>
        </div>
    );

}

export default AddBooking;
