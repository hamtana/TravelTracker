import { useEffect, useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import type { Booking } from "../api/bookingApi";
import { useNavigate } from "react-router-dom";
import { BookingApi } from "../api/bookingApi";

export function ViewPatientBookings() {
  const { getBookingsByPatientNhi } = BookingApi();
  const [bookings, setBookings] = useState<Booking[]>([]);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [nhi, setNhi] = useState<string | null>(null);

  useEffect(() => {
    const fetchBookings = async () => {
      setLoading(true);
      setError("");

      const storedNhi = sessionStorage.getItem("selectedNhi");
      if (!storedNhi) {
        setError("No NHI selected");
        setLoading(false);
        return;
      }

      setNhi(storedNhi);

      try {
        // ✅ use storedNhi directly (not state)
        const data = await getBookingsByPatientNhi(storedNhi);
        setBookings(data);
      } catch (err: any) {
        setError(err.message || "Failed to load bookings");
      } finally {
        setLoading(false);
      }
    };

    fetchBookings();
  }, []);

  return (
    <div className="min-h-screen bg-background text-foreground overflow-x-hidden">
      <ThemeToggle />
      <Navbar />
      <main>
        <SignedOut>
          <SignedOutComponent />
        </SignedOut>

        <SignedIn>
          <section className="relative min-h-screen flex flex-col items-center justify-center px-4 py-12">
            <div className="container max-w-6xl mx-auto space-y-8">
              <h1 className="text-3xl font-bold text-center mb-6">
                {nhi ? `Bookings for ${nhi}` : "Patient Bookings"}
              </h1>

              {loading && (
                <p className="text-center text-lg">
                  Loading patient bookings...
                </p>
              )}

              {error && (
                <p className="text-red-500 text-center text-lg">Error: {error}</p>
              )}

              {/* ✅ Booking Cards Grid */}
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {bookings.map((booking) => (
                  <div
                    key={booking.id}
                    className="p-6 bg-foreground/5 rounded-2xl shadow-md border border-foreground/10 hover:shadow-lg transition-shadow"
                  >
                    <h2 className="text-xl font-semibold mb-2 text-center">
                      {booking.destination || "Unknown Destination"}
                    </h2>

                    <p className="text-sm mb-1">
                      <strong>Status:</strong> {booking.bookingStatus || "N/A"}
                    </p>

                    <p className="text-sm mb-1">
                      <strong>Date of Departure:</strong>{" "}
                      {booking.dateOfDeparture
                        ? new Date(booking.dateOfDeparture).toLocaleDateString()
                        : "N/A"}
                    </p>

                    <p className="text-sm mb-1">
                      <strong>Date of Return:</strong>{" "}
                      {booking.dateOfReturn
                        ? new Date(booking.dateOfReturn).toLocaleDateString()
                        : "N/A"}
                    </p>

                    {booking.estimatedCost != null && (
                      <p className="text-sm mb-1">
                        <strong>Estimated Cost:</strong> $
                        {booking.estimatedCost.toFixed(2)}
                      </p>
                    )}

                    {booking.estimatedCostForPatient != null && (
                      <p className="text-sm mb-1">
                        <strong>Cost for Patient:</strong> $
                        {booking.estimatedCostForPatient.toFixed(2)}
                      </p>
                    )}

                    <p className="text-sm mb-1">
                      <strong>Service Provider:</strong>{" "}
                      {booking.serviceProvider?.name || "N/A"}
                    </p>

                    {booking.accommodationAddress && (
                      <p className="text-sm mb-1">
                        <strong>Accommodation:</strong>{" "}
                        {booking.accommodationAddress.name || "Unknown"} -{" "}
                        {booking.accommodationAddress.address ||
                          "No address provided"}
                      </p>
                    )}

                    {booking.notes && booking.notes.length > 0 && (
                      <div className="mt-2">
                        <strong>Notes:</strong>
                        <ul className="list-disc list-inside text-sm">
                          {booking.notes.map((note) => (
                            <li key={note.id || Math.random()}>
                              {note.message || "Empty note"}
                            </li>
                          ))}
                        </ul>
                      </div>
                    )}

                    <div className="mt-4 flex justify-center gap-2">
                      <button className="cosmic-button">View Details</button>
                      <button className="cosmic-button">Delete</button>
                    </div>
                  </div>
                ))}

                {bookings.length === 0 && !loading && (
                  <p className="col-span-full text-center text-lg">
                    No bookings found.
                  </p>
                )}

              

              </div>
                   {/* Return to Previous Page */}
                  <button onClick={() => navigate("/patients")} 
                    className="cosmic-button mt-4 px-6 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">
                    Back to Patients
                  </button>  
            </div>
          </section>
        </SignedIn>
      </main>
    </div>
  );
}

export default ViewPatientBookings;
