import React, {useEffect, useState} from "react";
import {Seat, SeatClass} from "../types/Seat";
import './SeatMap.css'

interface SeatMapProps {
    seats: Seat[];
    selectedSeatIds: number[];
    onSeatSelect: (seat: Seat) => void;
}

const SeatMap: React.FC<SeatMapProps> = ({seats, selectedSeatIds, onSeatSelect}) => {
    const [seatMap, setSeatMap] = useState<Seat[][]>([]);

    useEffect(() => {
        if (seats.length === 0) return;

        const maxRow = Math.max(...seats.map(seat => seat.row));
        const result: Seat[][] = Array(maxRow).fill(null).map(() => []);

        seats.forEach(seat => {
            while (result[seat.row - 1].length < getColumnIndex(seat.column)) {
                result[seat.row - 1].push(null as unknown as Seat);
            }
            result[seat.row - 1][getColumnIndex(seat.column)] = seat;
        });

        setSeatMap(result);
    }, [seats]);

    const getColumnIndex = (column: string): number => {
        return column.charCodeAt(0) - 'A'.charCodeAt(0);
    };

    const getSeatClassName = (seat: Seat): string => {
        let className = 'seat';

        if (seat.isReserved) {
            className += ' seat-reserved';
        } else if (selectedSeatIds.includes(seat.id)) {
            className += ' seat-selected';
        } else {
            className += ' seat-available';
        }

        if (seat.seatClass === SeatClass.FIRST_CLASS) {
            className += ' first-class';
        } else if (seat.seatClass === SeatClass.BUSINESS_CLASS) {
            className += ' business-class';
        } else {
            className += ' economy-class';
        }

        if (seat.isWindowSeat) className += ' window-seat';
        if (seat.isAisleSeat) className += ' aisle-seat';
        if (seat.hasExtraLegroom) className += ' extra-legroom';
        if (seat.isExitRowSeat) className += ' exit-row';

        return className;
    };

    const handleSeatClick = (seat: Seat) => {
        if (!seat.isReserved) {
            onSeatSelect(seat);
        }
    };

    if (seatMap.length === 0) {
        return <div>Loading seat map...</div>;
    }

    const maxColumns = Math.max(...seatMap.map(row => row.length));

    return (
        <div className="seat-map-container">
            <div className="seat-map-legend">
                <div className="legend-item">
                    <div className="seat-sample seat-available"></div>
                    <span>Available</span>
                </div>
                <div className="legend-item">
                    <div className="seat-sample seat-selected"></div>
                    <span>Selected</span>
                </div>
                <div className="legend-item">
                    <div className="seat-sample seat-reserved"></div>
                    <span>Reserved</span>
                </div>
                <div className="legend-item">
                    <div className="seat-sample first-class"></div>
                    <span>First Class</span>
                </div>
                <div className="legend-item">
                    <div className="seat-sample business-class"></div>
                    <span>Business Class</span>
                </div>
                <div className="legend-item">
                    <div className="seat-sample economy-class"></div>
                    <span>Economy Class</span>
                </div>
            </div>

            <div className="seat-map">
                {/* Column headers (A, B, C, ...) */}
                <div className="seat-row column-header">
                    <div className="row-label"></div>
                    {Array.from({ length: maxColumns }).map((_, colIdx) => (
                        <div key={colIdx} className="column-label">
                            {String.fromCharCode('A'.charCodeAt(0) + colIdx)}
                        </div>
                    ))}
                </div>

                {seatMap.map((row, rowIdx) => (
                    <div key={rowIdx} className="seat-row">
                        <div className="row-label">{rowIdx + 1}</div>
                        {Array.from({ length: maxColumns }).map((_, colIdx) => {
                            const seat = row[colIdx];
                            return (
                                <div
                                    key={colIdx}
                                    className={seat ? getSeatClassName(seat) : 'seat-spacer'}
                                    onClick={() => seat && handleSeatClick(seat)}
                                    title={seat ? `${seat.seatNumber}: ${getSeatInfo(seat)}` : ''}
                                >
                                    {seat && seat.seatNumber}
                                </div>
                            );
                        })}
                    </div>
                ))}
            </div>
        </div>
    );
};

function getSeatInfo(seat: Seat): string {
    const features = [];
    if (seat.isWindowSeat) features.push('Window');
    if (seat.isAisleSeat) features.push('Aisle');
    if (seat.hasExtraLegroom) features.push('Extra Legroom');
    if (seat.isExitRowSeat) features.push('Exit row');

    return features.join(', ') || 'Standard Seat';
}

export default SeatMap;