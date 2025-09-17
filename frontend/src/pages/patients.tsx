
import { SignedIn, SignedOut } from '@clerk/clerk-react'
import { Navbar } from '../components/Navbar'

export function Patients() {
  return (
    <div>
      <main>
        <Navbar />
        <SignedOut>
          <h2>Please sign in to continue</h2>
        </SignedOut>

        <SignedIn>
            
            {/* Will need some buttons on the top of the page which states Add Patient, Update Patient */}

            {/* This will show a table with all of the patients retrieved via a function in the components */}










        </SignedIn>
      </main>
    </div>
  )
}

export default Patients
