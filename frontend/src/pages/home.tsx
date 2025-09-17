
import { SignedIn, SignedOut } from '@clerk/clerk-react'
import { Navbar } from '../components/Navbar'
import { ThemeToggle } from '../components/ThemeToggle'

export function Home() {
  return (
    <div className="min-h-screen bg-background text-foreground overflow-x-hidden">
      <main>
          {/* Theme toggle */}
         <ThemeToggle/>

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
