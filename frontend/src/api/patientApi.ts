import useFetch from "../useAuthenticatedFetch";

export interface Patient {
  nhi: string;
  firstName: string;
  surname: string;
  ntaNumber: string;
}

const API_BASE = "http://localhost:8080/api/patients";

/**
 * Custom hook that provides patient API methods with Clerk authentication
 */
export function PatientApi() {
  const authenticatedFetch = useFetch();

  /**
   * Get all patients
   */
  const getAllPatients = async (): Promise<Patient[]> => {
    return authenticatedFetch(API_BASE);
  };

  /**
   * Get a single patient by NHI
   */
  const getPatientByNhi = async (nhi: string): Promise<Patient> => {
    try {
      return await authenticatedFetch(`${API_BASE}/${nhi}`);
    } catch (err: any) {
      if (err.message.includes("404")) {
        throw new Error("No patient found with that NHI");
      }
      throw err;
    }
  };

  /**
   * Add a new patient
   */
  const addPatient = async (patient: Patient): Promise<Patient> => {
    return authenticatedFetch(API_BASE, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(patient),
    });
  };

  /**
   * Update a patient
   */
  const updatePatient = async (nhi: string, patient: Patient): Promise<Patient> => {
    return authenticatedFetch(`${API_BASE}/${nhi}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(patient),
    });
  };

  /**
   * Delete a patient
   */
  const deletePatient = async (nhi: string): Promise<void> => {
    await authenticatedFetch(`${API_BASE}/${nhi}`, {
      method: "DELETE",
    });
  };

  return {
    getAllPatients,
    getPatientByNhi,
    addPatient,
    updatePatient,
    deletePatient,
  };
}
