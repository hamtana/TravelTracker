import { useEffect, useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import { PatientApi } from "../api/patientApi";
import type { Patient } from "../api/patientApi";

export function ViewPatient() {
  const { getPatientByNhi, updatePatient } = PatientApi();
  const [patient, setPatient] = useState<Patient>();
  const [searchNhi, setSearchNhi] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    loadPatient();
  }, []);


const loadPatient = async () => {
  setLoading(true);
  setError("");

  const nhi = sessionStorage.getItem("selectedNhi");
  if (!nhi) {
    setError("No NHI selected");
    setLoading(false);
    return;
  }
  setSearchNhi(nhi);

  try {
    const data = await getPatientByNhi(nhi); // pass NHI directly
    setPatient(data);
  } catch (err: any) {
    setError(err.message);
    setPatient(undefined);
  } finally {
    setLoading(false);
  }
};


  /**
   * Handle form field changes
   */
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { id, value } = e.target;
    setPatient((prev) => {
      if (!prev) return prev; // if undefined, skip
      if (id === "notes") {
        return { ...prev, notes: value.split("\n") };
      }
      return { ...prev, [id]: value };
    });
  };

  /**
   * Submit the edited patient back to the server
   */
  const editPatient = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!patient) return;

    try {
      await updatePatient(searchNhi, patient);
      alert("✅ Patient updated successfully!");
    } catch (err: any) {
      alert("❌ Failed to update patient: " + err.message);
    }
  };

  return (
    <div className="min-h-screen bg-background text-foreground overflow-x-hidden">
      <ThemeToggle />
      <Navbar />
      <main>
        <SignedOut>
          <SignedOutComponent />
        </SignedOut>

        <SignedIn>
          <section className="relative min-h-screen flex flex-col items-center justify-center px-4">
            <div className="container max-w-6xl mx-auto space-y-8">
              <h1 className="text-2xl font-bold text-center">
                View / Edit Patient
              </h1>

              {loading && <p className="text-center">Loading patient...</p>}
              {error && (
                <p className="text-center text-red-500 font-semibold">{error}</p>
              )}

              {!loading && patient && (
                <form onSubmit={editPatient} className="flex flex-col space-y-4 items-center">
                  <div className="flex flex-col space-y-2 w-full max-w-lg">
                    <label htmlFor="nhi" className="font-medium">
                      NHI:
                    </label>
                    <input
                      type="text"
                      id="nhi"
                      value={searchNhi}
                      readOnly
                      className="border p-2 rounded"
                    />

                    <label htmlFor="firstName" className="font-medium">
                      First Name:
                    </label>
                    <input
                      type="text"
                      id="firstName"
                      value={patient.firstName}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="surname" className="font-medium">
                      Surname:
                    </label>
                    <input
                      type="text"
                      id="surname"
                      value={patient.surname}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="ntaNumber" className="font-medium">
                      NTA Number:
                    </label>
                    <input
                      type="text"
                      id="ntaNumber"
                      value={patient.ntaNumber}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <button
                      type="submit"
                      className="cosmic-button mt-4 px-6 py-2 rounded bg-blue-600 text-white hover:bg-blue-700"
                    >
                      Save Changes
                    </button>
                  </div>
                </form>
              )}
            </div>
          </section>
        </SignedIn>
      </main>
    </div>
  );
}

export default ViewPatient;