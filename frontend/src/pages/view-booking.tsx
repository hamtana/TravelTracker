import { useEffect, useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import { useNavigate } from "react-router-dom";
import type { Booking } from "../api/bookingApi";

export function ViewBooking() {
  const [booking, setBooking] = useState<Booking | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    loadBooking();
  }, []);

  const loadBooking = async () => {
    setLoading(true);
    setError("");

    try {
      const storedBooking = sessionStorage.getItem("selectedBooking");
      if (!storedBooking) {
        setError("No booking selected");
        return;
      }
      const parsedBooking: Booking = JSON.parse(storedBooking);
      setBooking(parsedBooking);
    } catch (err) {
      setError("Failed to load booking details.");
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
            <section className="relative min-h-screen flex flex-col items-center justify-start px-6 pt-24">
          <div className="container max-w-3xl mx-auto space-y-8">
            <h1 className="text-2xl font-bold mb-4">View Booking</h1>

            {loading && <p>Loading booking...</p>}
            {error && <p className="text-red-500">Error: {error}</p>}

            {booking && (
              <div className="space-y-2">
                <p>
                  <strong>Booking ID:</strong> {booking.id}
                </p>
                <p>
                  <strong>Destination:</strong> {booking.destination}
                </p>
                <p>
                  <strong>Date of Departure:</strong> {booking.dateOfDeparture}
                </p>
                <p>
                  <strong>Date of Return:</strong> {booking.dateOfReturn}
                </p>
                <p>
                  <strong>Booking Created At:</strong>{" "}
                  {new Date(booking.bookingCreatedAt).toLocaleString()}
                </p>
                <p>
                  <strong>Service Provider:</strong>{" "}
                  {booking.serviceProvider
                    ? `${booking.serviceProvider.name}, ${booking.serviceProvider.address}`
                    : "Not specified"}
                </p>
                <p>
                  <strong>Support Persons:</strong>{" "}
                  {booking.supportPersons?.length
                    ? booking.supportPersons.map((sp) => sp.firstName).join(", ")
                    : "None"}
                </p>
                <p>
                  <strong>Accommodation Address:</strong>{" "}
                  {booking.accommodationAddress
                    ? `${booking.accommodationAddress.name}, ${booking.accommodationAddress.address}`
                    : "Not specified"}
                </p>

                <div>
                  <strong>Notes:</strong>
                  {booking.notes && booking.notes.length > 0 ? (
                    <ul className="list-disc list-inside">
                      {booking.notes.map((note, index) => (
                        <li key={index}>{note.message}</li>
                      ))}
                    </ul>
                  ) : (
                    <p>No notes available.</p>
                  )}
                </div>
                <button className="cosmic-button" onClick={() => navigate(-1)}>
                    Back
                    </button>
              </div>
            )}
          </div>
          </section>
        </SignedIn>
      </main>
    </div>
  );
}

export default ViewBooking;
