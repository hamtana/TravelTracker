import { useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { useNavigate } from "react-router-dom";
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import { PatientApi } from "../api/patientApi";
import type { Patient } from "../api/patientApi";

export function Patients() {
  const { getPatientByNhi } = PatientApi();
  const [patients, setPatients] = useState<Patient[]>([]);
  const [searchNhi, setSearchNhi] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const addNhiToSession = (nhi: string) => {
    sessionStorage.setItem("selectedNhi", nhi);
    navigate("/view-patient");
  };

  const addNhiToSessionBookings = (nhi: string) => {
    sessionStorage.setItem("selectedNhi", nhi);
    navigate(`/patients/${nhi}/bookings`);
  };

  const addPatientToSessionAddBooking = (storePatient: Patient) => {
    sessionStorage.setItem("storePatient", JSON.stringify(storePatient));
    navigate(`/patients/${storePatient.nhi}/add-booking`);
  };

  const handleSearch = async () => {
    const formattedNhi = searchNhi.trim().toUpperCase(); // ✅ always uppercase

    if (!formattedNhi) {
      setError("Please enter an NHI to search.");
      setPatients([]);
      return;
    }

    setLoading(true);
    setError("");

    try {
      const data = await getPatientByNhi(formattedNhi);
      if (data) {
        setPatients([data]);
      } else {
        setError("No patient found with that NHI.");
        setPatients([]);
      }
    } catch (err: any) {
      setError("No patient found with that NHI.");
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
          <section className="relative min-h-screen flex flex-col items-center justify-center px-6">
             <div className="container max-w-6xl mx-auto space-y-8">
              <h1 className="text-3xl font-bold text-center">Search Patient</h1>

              {/* Search Bar */}
              <div className="flex justify-center space-x-3 w-full">
                <input
                  type="text"
                  value={searchNhi}
                  onChange={(e) =>
                    setSearchNhi(e.target.value.toUpperCase()) // ✅ updates as uppercase while typing
                  }
                  onKeyDown={(e) => {
                    if (e.key === "Enter") handleSearch();
                  }}
                  placeholder="Enter NHI..."
                  className="w-2/3 md:w-1/2 px-4 py-3 border rounded-md bg-background text-foreground text-lg focus:outline-none focus:ring-2 focus:ring-primary shadow-sm uppercase" // ✅ ensures uppercase text appearance
                />
                <button
                  onClick={handleSearch}
                  className="cosmic-button px-6 py-3 text-lg"
                >
                  Search
                </button>
              </div>

              {loading && <p className="text-center text-lg">Searching...</p>}
              {error && <p className="text-center text-red-500 text-lg">{error}</p>}

              {/* Display Patient if Found */}
              {patients.length > 0 && !loading && (
                <div className="overflow-x-auto w-full">
                  <table className="w-full border border-foreground/20 rounded-lg shadow-md">
                    <thead>
                      <tr className="bg-foreground/10 text-center text-lg">
                        <th className="px-6 py-3">NHI Number</th>
                        <th className="px-6 py-3">First Name</th>
                        <th className="px-6 py-3">Surname</th>
                        <th className="px-6 py-3">NTA Number</th>
                        <th className="px-6 py-3">Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {patients.map((patient) => (
                        <tr key={patient.nhi} className="border-t text-center text-lg">
                          <td className="px-6 py-3">{patient.nhi}</td>
                          <td className="px-6 py-3">{patient.firstName}</td>
                          <td className="px-6 py-3">{patient.surname}</td>
                          <td className="px-6 py-3">{patient.ntaNumber}</td>
                          <td className="px-6 py-3 space-x-2">
                            <button
                              onClick={() => addNhiToSessionBookings(patient.nhi)}
                              className="cosmic-button"
                            >
                              Bookings
                            </button>
                            <button
                              onClick={() => addPatientToSessionAddBooking(patient)}
                              className="cosmic-button"
                            >
                              Add Booking
                            </button>
                            <button
                              onClick={() => addNhiToSession(patient.nhi)}
                              className="cosmic-button"
                            >
                              View Patient
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </section>
        </SignedIn>
      </main>
    </div>
  );
}

export default Patients;
