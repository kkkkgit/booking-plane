import axios from 'axios';
import { Flight } from '../types/Flight';
import {FilterOptions} from "../components/FlightFilter";

const API_URL = 'http://localhost:8080';

export const FlightService = {
    getAllFlights: async (): Promise<Flight[]> => {
        try {
            const response = await axios.get<Flight[]>(`${API_URL}/flight`);
            return response.data;
        } catch (error) {
            console.error('Error fetching flights:', error);
            return [];
        }
    },

    getFlightById: async (id: number): Promise<Flight | null> => {
        try {
            const response = await axios.get<Flight>(`${API_URL}/flight/${id}`);
            return response.data
        } catch (error) {
            console.error(`Error fetching flight with id ${id}: `, error);
            return null;
        }
    },


    getFilteredFlights: async (filters: FilterOptions): Promise<Flight[]> => {
        try {
            const allFlights = await FlightService.getAllFlights();

            return allFlights.filter(flight => {
                if (filters.destination && flight.destination !== filters.destination) {
                    return false;
                }

                // Check departure date filter
                if (filters.departureDate && flight.departureDate !== filters.departureDate) {
                    return false;
                }

                // Check min price filter
                if (filters.minPrice !== undefined && flight.price < filters.minPrice) {
                    return false;
                }

                // Check max price filter
                if (filters.maxPrice !== undefined && flight.price > filters.maxPrice) {
                    return false;
                }

                return true;
            });
        } catch (error) {
            console.error('Error filtering flights:', error);
            return [];
        }
    }
};