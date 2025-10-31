import { useEffect, useState } from 'react'
import { SignedIn, SignedOut } from '@clerk/clerk-react'
import { Navbar } from '../components/Navbar'
import { ThemeToggle } from '../components/ThemeToggle'
import type {Booking} from '../api/bookingApi'
import { BookingApi } from '../api/bookingApi'


export function Bookings() {

  const {getAllBookings} = BookingApi();
  const [bookings, setBookings] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(false);
  const [searchDptDate, setSearchDptDate] = useState("");
  const [error, setError] = useState("");
  
  useEffect(() => {
    const fetchBookings = async () => {
      setLoading(true);
      setError("");
      try {
        const data = await getAllBookings();
        // Order data by dateOfDeparture descending
        data.sort((a, b) => 
          new Date(b.dateOfDeparture).getTime() - new Date(a.dateOfDeparture).getTime()
        );
        setBookings(data);
      } catch (err: any) {
        setError(err.message || "Failed to load bookings");
      } finally {
        setLoading(false);
      }
    };

    fetchBookings();
  }, []);

  // Handle the search functionality, searching by date of departure
  const handleSearch = async () => {
    const filtered = bookings.filter((bookings: { dateOfDeparture: string | any[] }) => 
      bookings.dateOfDeparture.includes(searchDptDate)
    );
    setBookings(filtered);
  }



  return (
    <div>
      <main>
        <ThemeToggle />
        <Navbar />
        <SignedOut>
          <h2>Please sign in to continue</h2>
        </SignedOut>

        <SignedIn>
          <section className="relative min-h-screen flex flex-col items-center justify-start px-6 pt-24">
            <div className="container max-w-6xl mx-auto space-y-8">
              <h1 className="text-3xl font-bold text-center">Bookings</h1>
            </div>

            {/* Search Bar - Search Dates */}
            <div className="flex justify-center space-x-3 w-full">
              <input type="date" value={searchDptDate} onChange={(e) => setSearchDptDate(e.target.value)} 
              onKeyDown={(e) => {
                if (e.key === "Enter") handleSearch();
              }} 
                placeholder="Enter Date of Departure...."
                className="w-2/3 md:w-1/2 px-4 py-3 border rounded-md bg-background text-foreground text-lg focus:outline-none focus:ring-2 focus:ring-primary shadow-sm"
              />
              <button onClick={handleSearch}
              className="cosmic-button px-6 py-3 text-lg">Search</button>
              {/* Clear Search */}
              <button onClick={async () => {
                setLoading(true);
                setError("");
                try {
                  const data = await getAllBookings();
                  setBookings(data);
                } catch (err: any) {
                  setError(err.message || "Failed to load bookings");
                } finally {
                  setLoading(false);
                }
              }}
              className="cosmic-button px-6 py-3 text-lg">Clear</button>
              </div>


            {loading && <p className="text-center">Loading bookings...</p>}
            {error && (
              <p className="text-center text-red-500 font-semibold">{error}</p>
            )}
            {!loading && !error && bookings.length === 0 && (
              <p className="text-center">No bookings found.</p>
            )}

            {/* Display Bookings as table */}
            {bookings.length > 0 && !loading && (
              <div className="w-full overflow-x-auto mt-6">
                <table className="min-w-full border border-foreground/20 rounded-lg shadow-md">
                  <thead className="bg-foreground/10 text-center text-lg sticky top-0 z-10">
                    <tr className="bg-foreground/10 text-center text-lg">
                      <th className="px-6 py-3">Patient</th>
                      <th className="px-6 py-3">Date of Departure</th>
                      <th className="px-6 py-3">Date of Return</th>
                      <th className="px-6 py-3">Destination</th>
                      <th className="px-6 py-3">Booking Status</th>
                      <th className="px-6 py-3">Estimated Cost</th>
                      <th className="px-6 py-3">Estimated Cost for Patient</th>
                      <th className="px-6 py-3">Booking Created At</th>
                      <th className="px-6 py-3">Service Provider</th>
                      <th className="px-6 py-3">Support Persons</th>
                      <th className="px-6 py-3">Accommodation</th>
                    </tr>
                  </thead>
                  <tbody>
                    {bookings.map((booking) => (
                      <tr key={booking.id} className="text-center border-t border-foreground/20">
                        <td className="px-6 py-4">{booking.patient?.nhi || "N/A"}</td>
                        <td className="px-6 py-4">
                          {booking.dateOfDeparture
                            ? new Date(booking.dateOfDeparture).toLocaleDateString()
                            : "N/A"}
                        </td>
                        <td className="px-6 py-4">
                          {booking.dateOfReturn
                            ? new Date(booking.dateOfReturn).toLocaleDateString()
                            : "N/A"}
                        </td>
                        <td className="px-6 py-4">{booking.destination || "N/A"}</td>
                        <td className="px-6 py-4">{booking.bookingStatus || "N/A"}</td>
                        <td className="px-6 py-4">
                          {booking.estimatedCost != null
                            ? `$${booking.estimatedCost.toFixed(2)}`
                            : "N/A"}
                        </td>
                        <td className="px-6 py-4">
                          {booking.estimatedCostForPatient != null
                            ? `$${booking.estimatedCostForPatient.toFixed(2)}`
                            : "N/A"}
                        </td>
                        <td className="px-6 py-4">
                          {booking.bookingCreatedAt
                            ? new Date(booking.bookingCreatedAt).toLocaleDateString()
                            : "N/A"}
                        </td>
                        <td className="px-6 py-4">
                          {booking.serviceProvider
                            ? booking.serviceProvider.name
                            : "N/A"}
                        </td>
                        <td className="px-6 py-4">
                          {booking.supportPersons && booking.supportPersons.length > 0
                            ? booking.supportPersons.map((sp) => sp.firstName).join(", ")
                            : "N/A"}
                        </td>
                        <td className="px-6 py-4">
                          {booking.accommodationAddress
                            ? booking.accommodationAddress.name
                            : "N/A"}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                
                </table>
              </div>
            )}



          </section>
        </SignedIn>
      </main>
    </div>
  )
}

export default Bookings
