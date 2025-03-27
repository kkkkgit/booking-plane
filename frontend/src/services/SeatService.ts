import axios from "axios";
import {Seat, SeatPreferences} from '../types/Seat';

const API_URL = "http://localhost:8080";

export const SeatService = {
    getSeatsByFlight: async (flightId: number): Promise<Seat[]> => {
        try {
            const response = await axios.get<Seat[]>(`${API_URL}/seats/flight/${flightId}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching for flight: ', error)
            return [];
        }
    },

    getAvailableSeatsByFlight: async (flightId: number): Promise<Seat[]> => {
        try {
            const response = await axios.get<Seat[]>(`${API_URL}/seats/flight/${flightId}/available`);
            return response.data;
        } catch (error) {
            console.error('Error fetching available seats for flight', error);
            return [];
        }
    },

    generateSeatsForFlight: async (flightId: number, rows: number, columns: number): Promise<Seat[]> => {
        try {
            const response = await axios.post<Seat[]>(
                `${API_URL}/seats/flight/${flightId}/generate?rows=${rows}&columns=${columns}`
            );
            return response.data;
        } catch (error) {
            console.error('Error generating seats for flight: ', error);
            return [];
        }
    } ,

    recommendSeats: async (flightId: number, preferences: SeatPreferences, count: number): Promise<Seat[]> => {
        try {
            const response = await axios.post<Seat[]>(
                `${API_URL}/seats/flight/${flightId}/recommend?count=${count}`, preferences
            );
            return response.data;
        } catch (error) {
            console.error('Error getting seat recommendations: ', error);
            return [];
        }
    },

    reserveSeat: async (seatId: number): Promise<boolean> => {
        try {
            const response = await axios.post<{success: boolean}>(
                `${API_URL}/seats/${seatId}/reserve`
            );
            return response.data.success;
        } catch (error) {
            console.error('Error reserving seat: ', error)
            return false;
        }
    }
};
