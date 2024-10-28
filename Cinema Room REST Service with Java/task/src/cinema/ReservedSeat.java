package cinema;

import java.util.UUID;

public class ReservedSeat {
    UUID token;
    Seat ticket;

    public ReservedSeat(UUID token, Seat seat) {
        this.token = token;
        this.ticket = seat;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
