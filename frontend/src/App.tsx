import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Home } from "./pages/home";
import { NotFound } from "./pages/NotFound";
import "./App.css";
import Patients from "./pages/patients";
import Bookings from "./pages/bookings";
import ViewPatient from "./pages/view-patient";
import ViewPatientBookings from "./pages/view-patient-bookings";
import AddBooking from "./pages/add-booking";


function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route index element={<Home />} />
          <Route path="/patients" element={<Patients />} />
          <Route path="/bookings" element={<Bookings />} />
          <Route path ="/view-patient" element={<ViewPatient/> }/>
          <Route path ="/patients/:nhi/bookings" element={<ViewPatientBookings/> }/>
          <Route path="/patients/:nhi/add-booking" element={<AddBooking />} />
          <Route path="*" element={<NotFound />} />
       
        </Routes> 
      
      </BrowserRouter>
    
    </>
  );
}

export default App;
