import React, {useEffect, useState} from 'react';
import {Flight} from '../types/Flight';
import {Seat, SeatClass, SeatPreferences} from '../types/Seat';
import {SeatService} from '../services/SeatService';
import SeatMap from './SeatMap';
import './SeatSelection.css';

interface SeatSelectionProps {
    flight: Flight;
    numberOfPassengers: number;
    onSeatsSelected: (selectedSeats: Seat[]) => void;
}

const SeatSelection: React.FC<SeatSelectionProps> = ({flight, numberOfPassengers, onSeatsSelected}) => {
    const [seats, setSeats] = useState<Seat[]>([]);
    const [selectedSeatIds, setSelectedSeatIds] = useState<number[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [preferences, setPreferences] = useState<SeatPreferences>({
        wantsWindowSeat: false,
        wantsAisleSeat: false,
        wantsExitRowSeat: false,
        wantsExtraLegroom: false
    });

    useEffect(() => {
        let isMounted = true;

        const fetchSeats = async () => {
            if (!flight || !flight.id) {
                console.error("No flight ID available");
                return
            }

            setLoading(true);

            try {
                let flightSeats = await SeatService.getSeatsByFlight(flight.id);

                if (isMounted) {
                    if (!flightSeats || flightSeats.length === 0) {
                        console.log("No seats found")
                        try {
                            flightSeats = await SeatService.generateSeatsForFlight(flight.id, 20, 6);
                        } catch (error) {
                            console.error("Error generating seats: ", error);
                        }
                    }

                    setSeats(flightSeats || []);
                    setLoading(false);
                }
            } catch (error) {
                console.error("Error fetching seats: ", error);
                if (isMounted) setLoading(false);
            }
        };

        fetchSeats();

        return () => {
            isMounted = false;
        };
    }, [flight]);

    useEffect(() => {
        setSelectedSeatIds([]);
    }, [numberOfPassengers]);

    const handlePreferenceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = event.target;
        setPreferences(prev => ({...prev, [name]: checked}));
    };

    const handleSeatSelect = (seat: Seat) => {
        setSelectedSeatIds(prev => {
            const isAlreadySelected = prev.includes(seat.id);

            if (isAlreadySelected) {
                return prev.filter(id => id !== seat.id);
            } else {
                if (prev.length >= numberOfPassengers) {
                    return [...prev.slice(1), seat.id];
                }
                return [...prev, seat.id]
            }
        });
    };

    const getRecommendations = async () => {
        setLoading(true);
        const recommendedSeats = await SeatService.recommendSeats(
            flight.id,
            preferences,
            numberOfPassengers
        );

        setSelectedSeatIds(recommendedSeats.map(seat => seat.id));
        setLoading(false);
    };

    const confirmSelection = () => {
        const selectedSeats = seats.filter(seat => selectedSeatIds.includes(seat.id));
        onSeatsSelected(selectedSeats);
    };

    const areAdjacentSeats = (selectedSeats: Seat[]): boolean => {
        if (selectedSeats.length <= 1) return true;

        // Check if all seats are in the same row
        const row = selectedSeats[0].row;
        if (!selectedSeats.every(seat => seat.row === row)) return false;

        // Sort seats by column and check if they're adjacent
        const sortedSeats = [...selectedSeats].sort((a, b) =>
        a.column.charCodeAt(0) - b.column.charCodeAt(0)
        );

        for (let i = 1; i < sortedSeats.length; i++) {
            const prevColCode = sortedSeats[i-1].column.charCodeAt(0);
            const currColCode = sortedSeats[i].column.charCodeAt(0);

            if (currColCode - prevColCode !== 1) {
                return false;
            }
        }

        return true;
    };

    if (loading) {
        return <div>Loading seats...</div>;
    }

    const selectedSeats = seats.filter(seat => selectedSeatIds.includes(seat.id));
    const isSelectionComplete = selectedSeats.length === numberOfPassengers;
    const areSeatsAdjacent = areAdjacentSeats(selectedSeats);

    return (
        <div className="seat-selection-container">
            <h2>Select Seats for Flight to {flight.destination}</h2>

            <div className="seat-preferences">
                <h3>Seat Preferences</h3>
                <div className="preferences-form">
                    <label>
                        <input
                            type="checkbox"
                            name="wantsWindowSeat"
                            checked={preferences.wantsWindowSeat}
                            onChange={handlePreferenceChange}
                        />
                        Window Seat
                    </label>

                    <label>
                        <input
                            type="checkbox"
                            name="wantsAisleSeat"
                            checked={preferences.wantsAisleSeat}
                            onChange={handlePreferenceChange}
                        />
                        Aisle Seat
                    </label>

                    <label>
                        <input
                            type="checkbox"
                            name="wantsExtraLegroom"
                            checked={preferences.wantsExtraLegroom}
                            onChange={handlePreferenceChange}
                        />
                        Extra Legroom
                    </label>

                    <label>
                        <input
                            type="checkbox"
                            name="wantsExitRowSeat"
                            checked={preferences.wantsExitRowSeat}
                            onChange={handlePreferenceChange}
                        />
                        Near Exit
                    </label>

                    <button
                        onClick={getRecommendations}
                        className="recommend-button"
                    >
                        Recommend Seats
                    </button>
                </div>
            </div>

            <SeatMap
                seats={seats}
                selectedSeatIds={selectedSeatIds}
                onSeatSelect={handleSeatSelect}
            />

            <div className="seat-selection-summary">
                <h3>Selected Seats</h3>
                <div className="selected-seats-list">
                    {selectedSeats.length > 0 ? (
                        <ul>
                            {selectedSeats.map(seat => (
                                <li key={seat.id}>
                                    {seat.seatNumber} - {getSeatFeaturesText(seat)}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No seats selected. Please select {numberOfPassengers} seat(s).</p>
                    )}
                </div>

                {!areSeatsAdjacent && selectedSeats.length > 1 && (
                    <div className="warning-message">
                        Warning: Selected seats are not adjacent to each other.
                    </div>
                )}

                <button
                    onClick={confirmSelection}
                    disabled={!isSelectionComplete}
                    className={`confirm-button ${!isSelectionComplete ? 'disabled' : ''}`}
                >
                    Confirm Selection
                </button>
            </div>
        </div>
    );
};

function getSeatFeaturesText(seat: Seat): string {
    const features = [];

    if (seat.seatClass === SeatClass.FIRST_CLASS) {
        features.push('First Class');
    } else if (seat.seatClass === SeatClass.BUSINESS_CLASS) {
        features.push('Business Class');
    } else {
        features.push('Economy Class');
    }

    if (seat.isWindowSeat) features.push('Window');
    if (seat.isAisleSeat) features.push('Aisle');
    if (seat.hasExtraLegroom) features.push('Extra Legroom');
    if (seat.isExitRowSeat) features.push('Exit Row');

    return features.join(', ');
}

export default SeatSelection;