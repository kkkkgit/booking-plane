import axios from 'axios';
import { Flight } from '../types/Flight';

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

};