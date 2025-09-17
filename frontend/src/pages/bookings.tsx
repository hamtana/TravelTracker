
import { SignedIn, SignedOut } from '@clerk/clerk-react'
import { Navbar } from '../components/Navbar'

export function Bookings() {
  return (
    <div>
      <main>
        <Navbar />
        <SignedOut>
          <h2>Please sign in to continue</h2>
        </SignedOut>

        <SignedIn>
          
        </SignedIn>
      </main>
    </div>
  )
}

export default Bookings
