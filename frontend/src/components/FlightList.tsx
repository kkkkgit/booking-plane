import React, { useState, useEffect } from "react";
import { Flight } from "../types/Flight";
import { FlightService } from "../services/FlightService";

interface FlightListProps {
    flights?: Flight[];
    isLoading?: boolean;
}

const FlightList: React.FC<FlightListProps> = ({flights: propFlights, isLoading: propIsLoading}) => {
    // Component logic
    const [flights, setFlights] = useState<Flight[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        if (propFlights) {
            setFlights(propFlights);
            setIsLoading(propIsLoading || false);
        } else {
            const fetchFlights = async () => {
                setIsLoading(true);
                const data = await FlightService.getAllFlights();
                setFlights(data);
                setIsLoading(false);
            };

            fetchFlights();
        }
    }, [propFlights, propIsLoading]);

    if (isLoading) {
        return <div>Loading flights...</div>
    }

    if (flights.length === 0) {
        return <div>There are no flights.</div>
    }

    return (
        // Webpage display logic
        <div className="flight-list">
            <h2>Available Flights</h2>
            <table>
                <thead>
                    <tr>
                        <th>Destination</th>
                        <th>Departure</th>
                        <th>Arrival</th>
                        <th>Duration</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                {flights.map((flight) => (
                    <tr key={flight.id}>
                        <td>{flight.destination}</td>
                        <td>{new Date(flight.departureDate).toLocaleDateString()} {flight.departureTime}</td>
                        <td>{new Date(flight.arrivalDate).toLocaleDateString()} {flight.arrivalTime}</td>
                        <td>{flight.flightDuration}</td>
                        <td>{flight.price}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default FlightList;