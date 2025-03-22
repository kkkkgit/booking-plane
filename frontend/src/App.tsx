import React from 'react';
import './App.css';
import FlightSearchPage from "./pages/FlightSearchPage";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Flight booking system</h1>
      </header>
      <main>
        <FlightSearchPage />
      </main>
    </div>
  );
}

export default App;
