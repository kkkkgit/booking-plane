import React, {useState, useEffect} from "react";
import FlightList from "../components/FlightList";
import FlightFilter, {FilterOptions} from "../components/FlightFilter";
import { FlightService} from "../services/FlightService";
import {Flight} from "../types/Flight";

const FlightSearchPage: React.FC = () => {
    const [flights, setFlights] = useState<Flight[]>([]);
    const [filteredFlights, setFilteredFlights] = useState<Flight[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [destinations, setDestinations] = useState<string[]>([]);

    useEffect(() => {
        const fetchFlights = async () => {
            setIsLoading(true);
            const data = await FlightService.getAllFlights();
            setFlights(data);
            setFilteredFlights(data);

            const uniqueDestinations = Array.from(new Set(data.map(flight => flight.destination)));
            setDestinations(uniqueDestinations);

            setIsLoading(false);
        };

        fetchFlights();
    }, []);

    const handleFilterChange = async (filters: FilterOptions)=> {
        setIsLoading(true);
        const filtered = await FlightService.getFilteredFlights(filters);
        setFilteredFlights(filtered);
        setIsLoading(false);
    };

    return (
        <div className="flight-search-page">
            <h1>Flight Search</h1>
            <div className="flight-search-container">
                <FlightFilter
                    onFilterChange={handleFilterChange}
                    destinations={destinations}
                />
                <FlightList
                    flights={filteredFlights}
                    isLoading={isLoading}
                />
            </div>
        </div>
    );
};

export default FlightSearchPage;