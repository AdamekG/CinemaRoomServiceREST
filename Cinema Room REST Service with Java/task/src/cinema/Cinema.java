package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Cinema {
    private int rows;
    private int columns;
    private final ArrayList<Seat> seats;
    @JsonIgnore
    private final ArrayList<ReservedSeat> reservedSeats;
    public Cinema(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        seats = new ArrayList<>(rows * columns);
        this.reservedSeats = new ArrayList<>();

        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                seats.add(new Seat(row, column));
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }
    public ArrayList<ReservedSeat> getReservedSeats() {
        return reservedSeats;
    }
}
