import useFetch from "../useAuthenticatedFetch";
import type { Patient } from "./patientApi";

export interface SupportPerson {
    id?: number;
    firstName : string;
    surname : string;
    coveredByNta : boolean;
    patientNhi : Patient[];
}

const API_BASE = "http://localhost:8080/api/support-persons";

export function SupportPersonApi() {
    const authenticatedFetch = useFetch();

    const addSupportPerson = async (supportPerson : SupportPerson): Promise<SupportPerson> => {
        return authenticatedFetch(API_BASE, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(supportPerson),
        });
    };

    const deleteSupportPersonById = async (id: string): Promise<void> => {
        return authenticatedFetch(`${API_BASE}/${id}`, {
            method: "DELETE",
        });
    }

    const getSupportPersonById = async (id: string): Promise<SupportPerson> => {
        try{
            return await authenticatedFetch(`${API_BASE}/${id}`);
        } catch (err: any) {
            if (err.message.includes("404")) {
                throw new Error("No support person found with that ID");
            }
            throw err;
        };
    }

    const updateSupporterById = async (id: string, supportPerson : SupportPerson): Promise<SupportPerson> => {
        return authenticatedFetch(`${API_BASE}/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(supportPerson),
        });
    }

    return {
        addSupportPerson,
        deleteSupportPersonById,
        getSupportPersonById,
        updateSupporterById,
    };

}