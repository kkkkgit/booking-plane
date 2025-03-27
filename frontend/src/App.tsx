import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';
import FlightSearchPage from './pages/FlightSearchPage';
import BookingPage from './pages/BookingPage';

function App() {
  return (
      <Router>
        <div className="App">
            <header className="App-header">
                <h1>
                    <Link to="/" className="app-title">Flight Booking System</Link>
                </h1>
            </header>
            <main>
            <Routes>
                  <Route path="/" element={<FlightSearchPage />} />
                <Route path="/booking/:flightId" element={<BookingPage />} />
            </Routes>
            </main>
            <footer className="App-footer">
                <p>{new Date().getFullYear()} Flight Booking System</p>
            </footer>
        </div>
      </Router>
  );
}

export default App;
