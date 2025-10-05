import useFetch from "../useAuthenticatedFetch";

export interface Accommodation {
  address: string;
  name: string;
}

const API_BASE = "http://localhost:8080/api/accommodations"


export function AccommodationApi(){

    const authenticatedFetch = useFetch();

    /**
     * Get all Accommodation
     * @returns accomodation []
     */
    const getAccommodation = async (): Promise<Accommodation[]> => {
        return authenticatedFetch(API_BASE);
    };

    /**
     * Add Accommodation
     * @param accommodation 
     * @returns 
     */
    const addAccommodation = async (accommodation : Accommodation): Promise<Accommodation> => {
        return authenticatedFetch(API_BASE, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(accommodation),
        });
    };

    /**
     * Get Accommodation by address
     * @param address 
     * @returns accommodation
     */
    const getAccommodationByAddress = async (address: string): Promise<Accommodation> => {
        try{
            return await authenticatedFetch(`${API_BASE}/${address}`);
        } catch (err: any) {
            if (err.message.includes("404")) {
                throw new Error("No patient found with that NHI");
            }
            throw err;
        };
    };

    /**
     * Update Accommodation via Address
     * @param address 
     * @param accommodation 
     * @returns 
     */
    const updateAccommodation = async (address: string, accommodation : Accommodation): Promise<Accommodation> => {
        return authenticatedFetch(`${API_BASE}/${address}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(accommodation),
        });
    };

    /**
     * Delete Accommodation via address
     * @param address 
     */
    const deleteAccommodation = async (address: string) => {
        await authenticatedFetch(`${API_BASE}/${address}`, {
            method: "DELETE",
        });
    };

    return {
        deleteAccommodation,
        updateAccommodation,
        getAccommodation,
        getAccommodationByAddress,
        addAccommodation,
    }








}