import { useEffect, useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { useNavigate } from "react-router-dom"; 
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import { PatientApi } from "../api/patientApi";
import type { Patient } from "../api/patientApi";

export function Patients() {
  const { getAllPatients, getPatientByNhi } = PatientApi();
  const [patients, setPatients] = useState<Patient[]>([]);
  const [searchNhi, setSearchNhi] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const navigate = useNavigate(); // <-- Initialize navigate


  useEffect(() => {
    loadAllPatients();
  }, []);

  const loadAllPatients = async () => {
    setLoading(true);
    setError("");

    try {
      const data = await getAllPatients();
      setPatients(data);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const addNhiToSession = (nhi: string) => {
    sessionStorage.setItem("selectedNhi", nhi);
    navigate("/view-patient")
  };

  const handleSearch = async () => {
    if (!searchNhi) {
      loadAllPatients();
      return;
    }

    setLoading(true);
    setError("");

    try {
      const data = await getPatientByNhi(searchNhi);
      setPatients([data]);
    } catch (err: any) {
      setError(err.message);
      setPatients([]);
    } finally {
      setLoading(false);
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
              <h1 className="text-2xl font-bold text-center">Patients</h1>

              {/* Toolbar */}
              <div className="flex justify-between items-center">
                <div className="space-x-2">
                  <button className="cosmic-button">Add Patient</button>
                </div>
                <div className="flex space-x-2">
                  <input
                    type="text"
                    value={searchNhi}
                    onChange={(e) => setSearchNhi(e.target.value)}
                    onKeyDown={(e) => {
                      if (e.key === "Enter") handleSearch();
                    }}
                    placeholder="Enter NHI..."
                    className="px-3 py-2 border rounded-md bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
                  />
                  <button onClick={handleSearch} className="cosmic-button">
                    Search
                  </button>
                </div>
              </div>

              {loading && <p>Loading patients...</p>}
              {error && <p className="text-red-500">Error: {error}</p>}

              {/* Patient Table */}
              <div className="overflow-x-auto items-center">
                <table className="min-w-full border border-foreground/20 rounded-lg">
                  <thead>
                    <tr className="bg-foreground/10 text-center">
                      <th className="px-4 py-2">NHI Number</th>
                      <th className="px-4 py-2">First Name</th>
                      <th className="px-4 py-2">Surname</th>
                      <th className="px-4 py-2">NTA Number</th>
                      <th className="px-4 py-2">Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {patients.map((patient) => (
                      <tr key={patient.nhi} className="border-t text-center">
                        <td className="px-4 py-2">{patient.nhi}</td>
                        <td className="px-4 py-2">{patient.firstName}</td>
                        <td className="px-4 py-2">{patient.surname}</td>
                        <td className="px-4 py-2">{patient.ntaNumber}</td>
                        <td className="px-4 py-2 space-x-2">
                          <button className="cosmic-button">Bookings</button>
                          <button className="cosmic-button">Add Booking</button>
                          <button
                            onClick={() => addNhiToSession(patient.nhi)}
                            className="cosmic-button"
                          >
                            View Patient
                          </button>
                        </td>
                      </tr>
                    ))}
                    {patients.length === 0 && !loading && (
                      <tr>
                        <td colSpan={5} className="px-4 py-2 text-center">
                          No patients found.
                        </td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </section>
        </SignedIn>
      </main>
    </div>
  );
}

export default Patients;
