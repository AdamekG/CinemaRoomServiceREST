package cinema;

public class Seat {
    private int row;
    private int column;
    private int price;
    boolean isReserved = false;

    public Seat(int row, int place) {
        this.row = row;
        this.column = place;
        this.price = row <= 4 ? 10 : 8;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getPrice() {
        return price;
    }

    boolean isReserved() {
        return isReserved;
    }

    void reserve() {
        isReserved = true;
    }

    void returnTicket() {
        isReserved = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (row != seat.row) return false;
        return column == seat.column;
    }
}
