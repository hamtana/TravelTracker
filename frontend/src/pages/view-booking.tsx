import { useEffect, useState } from "react";
import { SignedIn, SignedOut } from "@clerk/clerk-react";
import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import SignedOutComponent from "../components/SignedOutComponent";
import { useNavigate } from "react-router-dom";
import type { Booking } from "../api/bookingApi";
import { BookingApi } from "../api/bookingApi";
import type { Notes } from "../api/notesApi";

export function ViewBooking() {
  const { updateBookingById, addBookingNote } = BookingApi();
  const [booking, setBooking] = useState<Booking>();
  const [loading, setLoading] = useState(false);
  const [notesText, setNotesText] = useState("");
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

      // Initialize notes textarea string
      setNotesText(
        parsedBooking.notes
          ? parsedBooking.notes.map((n: Notes) => n.message).join("\n")
          : ""
      );
    } catch (err) {
      setError("Failed to load booking details.");
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { id, value } = e.target;

    if (id === "notes") {
      setNotesText(value);
      return;
    }

    setBooking((prev) => {
      if (!prev) return prev;
      return { ...prev, [id]: value };
    });
  };

  const editBooking = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!booking || !booking.id || !booking.patient?.nhi) {
      alert("❌ Booking ID or Patient NHI is missing.");
      return;
    }

    try {
      await updateBookingById(
        booking.id,
        booking.patient.nhi,
        booking
      );

      // Add each note individually
      const noteLines = notesText
        .split(/\r?\n/)
        .map((line) => line.trim())
        .filter((line) => line !== "");

      for (const line of noteLines) {
        await addBookingNote(booking.id, {
          message: line, // backend expects 'message'
          booking: booking, 
        });
      }

      alert("✅ Booking updated successfully.");
    } catch (err) {
      console.error(err);
      alert("❌ Failed to update booking.");
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
          <section className="relative min-h-screen flex flex-col items-center justify-center px-4 py-24">
            <div className="container max-w-6xl mx-auto space-y-8">
              <h1 className="text-2xl font-bold text-center">
                View / Edit Booking
              </h1>

              {loading && <p className="text-center">Loading booking...</p>}
              {error && (
                <p className="text-center text-red-500 font-semibold">
                  {error}
                </p>
              )}

              {!loading && booking && (
                <form
                  onSubmit={editBooking}
                  className="flex flex-col space-y-4 items-center"
                >
                  <div className="flex flex-col space-y-2 w-full max-w-lg">
                    <label htmlFor="bookingCreatedAt" className="font-medium">
                      Booking Created At:
                    </label>
                    <input
                      type="text"
                      id="bookingCreatedAt"
                      value={new Date(
                        booking.bookingCreatedAt
                      ).toLocaleString()}
                      readOnly
                      className="border p-2 rounded"
                    />

                    <label htmlFor="dateOfDeparture" className="font-medium">
                      Departure Date:
                    </label>
                    <input
                      type="date"
                      id="dateOfDeparture"
                      value={booking.dateOfDeparture}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="dateOfReturn" className="font-medium">
                      Return Date:
                    </label>
                    <input
                      type="date"
                      id="dateOfReturn"
                      value={booking.dateOfReturn}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="patientNhi" className="font-medium">
                      Patient NHI:
                    </label>
                    <input
                      type="text"
                      id="patientNhi"
                      value={booking.patient.nhi}
                      readOnly
                      className="border p-2 rounded"
                    />

                    <label htmlFor="destination" className="font-medium">
                      Destination:
                    </label>
                    <input
                      type="text"
                      id="destination"
                      value={booking.destination}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="bookingStatus" className="font-medium">
                      Booking Status:
                    </label>
                    <input
                      type="text"
                      id="bookingStatus"
                      value={booking.bookingStatus}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="estimatedCost" className="font-medium">
                      Estimated Cost:
                    </label>
                    <input
                      type="number"
                      id="estimatedCost"
                      value={booking.estimatedCost || ""}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="notes" className="font-medium">
                      Notes:
                    </label>
                    <textarea
                      id="notes"
                      value={notesText}
                      onChange={handleChange}
                      className="border p-2 rounded h-24 w-full"
                    />

                    <label
                      htmlFor="estimatedCostForPatient"
                      className="font-medium"
                    >
                      Estimated Cost for Patient:
                    </label>
                    <input
                      type="number"
                      id="estimatedCostForPatient"
                      value={booking.estimatedCostForPatient || ""}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <label htmlFor="serviceProvider" className="font-medium">
                      Service Provider:
                    </label>
                    <input
                      type="text"
                      id="serviceProvider"
                      value={booking.serviceProvider?.name || "N/A"}
                      readOnly
                      className="border p-2 rounded"
                    />

                    <label htmlFor="accommodation" className="font-medium">
                      Accommodation Address:
                    </label>
                    <input
                      type="text"
                      id="accommodation"
                      value={booking.accommodationAddress?.name || "N/A"}
                      onChange={handleChange}
                      className="border p-2 rounded"
                    />

                    <button
                      type="submit"
                      className="cosmic-button mt-4 px-6 py-2 rounded"
                    >
                      Save Changes
                    </button>
                  </div>
                </form>
              )}

              {/* Return to Previous Page */}
              <button
                onClick={() => navigate(-1)}
                className="cosmic-button mt-4 px-6 py-2 rounded"
              >
                Back to Bookings
              </button>
            </div>
          </section>
        </SignedIn>
      </main>
    </div>
  );
}

export default ViewBooking;
