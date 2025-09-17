export interface Patient {
  nhi: string;
  firstName: string;
  surname: string;
  ntaNumber: string;
}

const API_BASE = "http://localhost:8080/api/patients";

/**
 * Get all patients
 */
export async function getAllPatients(): Promise<Patient[]> {
  const response = await fetch(API_BASE);
  if (!response.ok) {
    throw new Error("Failed to fetch patients");
  }
  return response.json();
}

/**
 * Get a single patient by NHI
 */
export async function getPatientByNhi(nhi: string): Promise<Patient> {
  const response = await fetch(`${API_BASE}/${nhi}`);
  if (response.status === 404) {
    throw new Error("No patient found with that NHI");
  }
  if (!response.ok) {
    throw new Error("Failed to fetch patient");
  }
  return response.json();
}

/**
 * Add a new patient
 */
export async function addPatient(patient: Patient): Promise<Patient> {
  const response = await fetch(API_BASE, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(patient),
  });
  if (!response.ok) {
    throw new Error("Failed to add patient");
  }
  return response.json();
}

/**
 * Update a patient
 */
export async function updatePatient(nhi: string, patient: Patient): Promise<Patient> {
  const response = await fetch(`${API_BASE}/${nhi}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(patient),
  });
  if (!response.ok) {
    throw new Error("Failed to update patient");
  }
  return response.json();
}

/**
 * Delete a patient
 */
export async function deletePatient(nhi: string): Promise<void> {
  const response = await fetch(`${API_BASE}/${nhi}`, {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("Failed to delete patient");
  }
}
