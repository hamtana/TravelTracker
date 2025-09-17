
import { SignedIn, SignedOut, SignInButton, UserButton } from '@clerk/clerk-react'
import { Navbar } from '../components/Navbar'

export function Home() {
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

export default Home
