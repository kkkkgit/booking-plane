import React, { useState } from "react";

// Different filter types that are usable for the user
export interface FilterOptions {
    destination?: string;
    departureDate?: string;
    minPrice?: number;
    maxPrice?: number;
}

//
interface FlightFilterProps {
    onFilterChange: (filters: FilterOptions) => void;
    destinations: string[]; // all available destinations
}

const FlightFilter: React.FC<FlightFilterProps> = ({ onFilterChange, destinations }) => {
    const [filters, setFilters] = useState<FilterOptions>({});

    const handleFilterChange = (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = event.target;

        if (name === 'minPrice' || name === 'maxPrice') {
            const numValue = value ? parseInt(value, 10) : undefined;
            setFilters(prev => ({ ...prev, [name]: numValue }));
        }else {
            setFilters(prev => ({ ...prev, [name]: value || undefined}))
        }
    };

    const applyFilters = () => {
        // parent component notification
        onFilterChange(filters);
    };

    const resetFilters = () => {
        // clear all filters
        setFilters({});
        // parent component notification
        onFilterChange({});
    }

    return (
        <div className="flight-filter">
            <h3>Filter Flights</h3>
            <div className="filter-form">
                <div className="filter-group">
                    <label htmlFor="destination">Destination:</label>
                    <select
                        id="destination"
                        name="destination"
                        value={filters.destination}
                        onChange={handleFilterChange}
                    >
                    <option value="">All Destinations</option>
                        {destinations.map(dest => (
                            <option key={dest} value={dest}>{dest}</option>
                        ))}
                    </select>
                </div>

                <div className="filter-group">
                    <label htmlFor="departureDate">Departure date:</label>
                    <input
                        type="date"
                        id="departureDate"
                        name="departureDate"
                        value={filters.departureDate || ''}
                        onChange={handleFilterChange}
                    />
                </div>

                <div className="filter-group">
                    <label htmlFor="minPrice">Min price (EUR):</label>
                    <input
                        type="number"
                        id="minPrice"
                        name="minPrice"
                        value={filters.minPrice || ''}
                        onChange={handleFilterChange}
                    />
                </div>

                <div className="filter-group">
                    <label htmlFor="maxPrice">Max price (EUR)</label>
                    <input
                        type="number"
                        id="maxPrice"
                        name="maxPrice"
                        value={filters.maxPrice || ''}
                        onChange={handleFilterChange}
                    />
                </div>

                <div className="filter-actions">
                    <button type="button" onClick={applyFilters}>Apply filters</button>
                    <button type="button" onClick={resetFilters}>Reset</button>
                </div>
            </div>
        </div>
    );
};

export default FlightFilter;