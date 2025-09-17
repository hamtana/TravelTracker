import { SignedIn, SignedOut } from '@clerk/clerk-react'
import { Navbar } from '../components/Navbar'
import { ThemeToggle } from '../components/ThemeToggle'
import { ArrowDown } from 'lucide-react'
import SignedOutComponent from '../components/SignedOutComponent'

export function Home() {
  return (
    <div className="min-h-screen bg-background text-foreground overflow-x-hidden">
      {/* Theme toggle */}
      <ThemeToggle />

      <Navbar />

      <main>
        <SignedOut>
          <SignedOutComponent />
        </SignedOut>

        <SignedIn>
          <section
            id="hero"
            className="relative min-h-screen flex flex-col items-center justify-center px-4"
          >
            <div className="container max-w-5xl mx-auto text-center z-10">
              <div className="space-y-6">
                <h1 className="text-4xl md:text-6xl font-bold tracking-tight">
                  <span className="opacity-0 animate-fade-in">National Travel </span>
                  <span className="text-primary opacity-0 animate-fade-in-delay-1">
                    Assistance Portal
                  </span>
                </h1>

                <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto opacity-0 animate-fade-in-delay-3">
                  A portal for keeping track of flights, patients, service providers and their support people.
                </p>

                <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto opacity-0 animate-fade-in-delay-3">
                  Developed for the Capital & Coast District Health Board.
                </p>
              </div>
            </div>

            <div className="absolute bottom-8 left-1/2 transform -translate-x-1/2 flex flex-col items-center animate-bounce">
              <span className="text-sm text-muted-foreground mb-2">Scroll</span>
              <ArrowDown className="h-5 w-5 text-primary" />
            </div>
          </section>

        </SignedIn>
      </main>
    </div>
  )
}

export default Home
