import { useEffect, useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import { getPatientByNhi, updatePatient } from "../api/patientApi";
import type { Patient } from "../api/patientApi";

export function ViewPatient() {
  const [patient, setPatient] = useState<Patient>();
  const [searchNhi, setSearchNhi] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    loadPatient();
    retrieveNhiFromSession();
  }, []);

  const retrieveNhiFromSession = () => {
    const nhi = sessionStorage.getItem("selectedNhi");
    if (nhi) {
      setSearchNhi(nhi);
    }
    }

  const loadPatient = async () => {
    setLoading(true);
    setError("");

    try {
      const data = await getPatientByNhi(searchNhi);
      setPatient(data);
    } catch (err: any) {
      setError(err.message);
      setPatient(undefined);
    } finally {
      setLoading(false);
    }
    
    const updatePatient = async () => {

        try{
            const updatedPatient = await updatePatient();
            setPatient(updatedPatient);
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
              <h1 className="text-2xl font-bold text-center"></h1>
              <form>
                <div className="flex space-x-2 justify-center">

                    <label htmlFor="nhi" className="font-medium">NHI:</label>
                    <input type="text" id="nhi" value={searchNhi} readOnly> </input>

                    <label htmlFor="firstName" className="font-medium">First Name:</label>
                    <input type="text" id="firstName" value={patient?.firstName ?? ""} readOnly> </input>

                    <label htmlFor="surname" className="font-medium">Surname:</label>
                    <input type="text" id="surname" value={patient?.surname ?? ""} readOnly> </input>

                    <label htmlFor="ntaNumber" className="font-medium">NTA Number:</label>
                    <input type="text" id="ntaNumber" value={patient?.ntaNumber ?? ""}> </input>

                    <label htmlFor="notes" className="font-medium">Notes:</label>
                    <textarea id="notes" value={patient?.notes.join("\n") ?? ""} className="w-96 h-32"></textarea>

                    <button onSubmit={} className="cosmic-button"></button>
                </div>
              </form>



              {/* Patient Table */}
              <div className="overflow-x-auto items-center">

              </div>
            </div>
          </section>
        </SignedIn>
      </main>
    </div>
  )
}

export default ViewPatient
