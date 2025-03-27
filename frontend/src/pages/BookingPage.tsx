import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Flight } from '../types/Flight';
import { Seat } from '../types/Seat';
import { FlightService } from '../services/FlightService';
import SeatSelection from '../components/SeatSelection';
import './BookingPage.css';

const BookingPage: React.FC = () => {
    const { flightId } = useParams<{ flightId: string }>();
    const navigate = useNavigate();
    const [flight, setFlight] = useState<Flight | null>(null);
    const [numberOfPassengers, setNumberOfPassengers] = useState<number>(1);
    const [selectedSeats, setSelectedSeats] = useState<Seat[]>([]);
    const [step, setStep] = useState<'passengers' | 'seats' | 'confirmation'>('passengers');
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchFlight = async () => {
            if (!flightId) return;

            setLoading(true);
            try {
                const flightData = await  FlightService.getFlightById(parseInt(flightId));
                setFlight(flightData);
            } catch (error) {
                console.error('Error fetching flight: ', error);
            } finally {
                setLoading(false);
            }
        };
    }, [flightId]);

    const handlePassengerCountChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setNumberOfPassengers(parseInt(event.target.value));
    };

    const handleSeatsSelected = (seats: Seat[]) => {
        setSelectedSeats(seats);
        setStep('confirmation')
    };

    const handleConfirmBooking = async () => {
        alert(`Booking confirmed! Flight to ${flight?.destination} with ${selectedSeats.length} passengers.`);
        navigate('/');
    };

    if (loading) {
        return <div className="booking-page loading">Loading flight details...</div>;
    }

    if (!flight) {
        return <div className="booking-page error">Flight not found</div>;
    }

    return (
        <div className="booking-page">
            <h1>Book Flight to {flight.destination}</h1>

            <div className="booking-details">
                <div>
                    <strong>Flight:</strong> {flight.destination}
                </div>
                <div>
                    <strong>Departure:</strong> {new Date(flight.departureDate).toLocaleDateString()} at {flight.departureTime}
                </div>
                <div>
                    <strong>Arrival:</strong> {new Date(flight.arrivalDate).toLocaleDateString()} at {flight.arrivalTime}
                </div>
                <div>
                    <strong>Duration:</strong> {flight.flightDuration}
                </div>
                <div>
                    <strong>Price:</strong> €{flight.price} per passenger
                </div>
            </div>

            <div className="booking-steps">
                <div className={`step ${step === 'passengers' ? 'active' : ''}`}>
                    <div className="step-number">1</div>
                    <div className="step-label">Passengers</div>
                </div>
                <div className={`step ${step === 'seats' ? 'active' : ''}`}>
                    <div className="step-number">2</div>
                    <div className="step-label">Seat Selection</div>
                </div>
                <div className={`step ${step === 'confirmation' ? 'active' : ''}`}>
                    <div className="step-number">3</div>
                    <div className="step-label">Confirmation</div>
                </div>
            </div>

            {step === 'passengers' && (
                <div className="passenger-selection">
                    <h2>How many passengers are traveling?</h2>
                    <div className="passenger-form">
                        <label htmlFor="passengerCount">Number of Passengers:</label>
                        <select
                            id="passengerCount"
                            value={numberOfPassengers}
                            onChange={handlePassengerCountChange}
                        >
                            {[1, 2, 3, 4, 5, 6].map(num => (
                                <option key={num} value={num}>{num}</option>
                            ))}
                        </select>

                        <div className="price-summary">
                            <div>Price per passenger: €{flight.price}</div>
                            <div>Total price: €{flight.price * numberOfPassengers}</div>
                        </div>

                        <button
                            onClick={() => setStep('seats')}
                            className="next-button"
                        >
                            Continue to Seat Selection
                        </button>
                    </div>
                </div>
            )}

            {step === 'seats' && (
                <SeatSelection
                    flight={flight}
                    numberOfPassengers={numberOfPassengers}
                    onSeatsSelected={handleSeatsSelected}
                />
            )}

            {step === 'confirmation' && (
                <div className="booking-confirmation">
                    <h2>Confirm Your Booking</h2>

                    <div className="confirmation-details">
                        <h3>Flight Details</h3>
                        <p>
                            <strong>Destination:</strong> {flight.destination}<br />
                            <strong>Departure:</strong> {new Date(flight.departureDate).toLocaleDateString()} at {flight.departureTime}<br />
                            <strong>Arrival:</strong> {new Date(flight.arrivalDate).toLocaleDateString()} at {flight.arrivalTime}
                        </p>

                        <h3>Passenger Information</h3>
                        <p>Number of Passengers: {numberOfPassengers}</p>

                        <h3>Selected Seats</h3>
                        <ul className="confirmation-seats">
                            {selectedSeats.map(seat => (
                                <li key={seat.id}>
                                    <strong>{seat.seatNumber}</strong> -
                                    {seat.seatClass === 'FIRST_CLASS' ? ' First Class' :
                                        seat.seatClass === 'BUSINESS_CLASS' ? ' Business Class' : ' Economy Class'}
                                    {seat.isWindowSeat && ', Window Seat'}
                                    {seat.isAisleSeat && ', Aisle Seat'}
                                    {seat.hasExtraLegroom && ', Extra Legroom'}
                                    {seat.isExitRowSeat && ', Exit Row'}
                                </li>
                            ))}
                        </ul>

                        <h3>Price Summary</h3>
                        <div className="price-summary">
                            <div>Price per passenger: €{flight.price}</div>
                            <div className="total-price">Total price: €{flight.price * numberOfPassengers}</div>
                        </div>
                    </div>

                    <div className="action-buttons">
                        <button
                            onClick={() => setStep('seats')}
                            className="back-button"
                        >
                            Back to Seat Selection
                        </button>

                        <button
                            onClick={handleConfirmBooking}
                            className="confirm-button"
                        >
                            Confirm and Pay
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default BookingPage;