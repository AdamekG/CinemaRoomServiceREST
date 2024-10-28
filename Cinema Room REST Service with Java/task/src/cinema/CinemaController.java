package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaController {
    private final Cinema cinema;

    public CinemaController() {
        this.cinema = new Cinema(9, 9);
    }

    @GetMapping("/api/health")
    public ResponseEntity<String> getServiceHealth() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase (@RequestBody Seat seat) {

        if (seat.getRow() > cinema.getRows() ||
        seat.getColumn() > cinema.getColumns() ||
        seat.getRow() < 1 ||
        seat.getColumn() < 1) {
            ErrorResponse response = new ErrorResponse("The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        for (Seat s : cinema.getSeats()) {
            if (s.equals(seat)) {
                if (!s.isReserved) {
                    ReservedSeat reservedSeat = new ReservedSeat(generateUUID(), s);
                    cinema.getReservedSeats().add(reservedSeat);
                    s.reserve();
                    return new ResponseEntity<>(reservedSeat, HttpStatus.OK);
                }
            }
        }

        ErrorResponse response = new ErrorResponse("The ticket has been already purchased!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Token token) {
        ArrayList<ReservedSeat> reservedSeats = cinema.getReservedSeats();

        for (ReservedSeat seat : reservedSeats) {
            if (seat.getToken().equals(token.getToken())) {
                reservedSeats.remove(seat);
                seat.getTicket().returnTicket();
                return new ResponseEntity<>(Map.of("ticket", seat.getTicket()), HttpStatus.OK);
            }
        }
        ErrorResponse response = new ErrorResponse("Wrong token!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
        if (password != null && password.equals("super_secret")) {
            Map<String, Integer> statistics = new HashMap<>();
            int income = 0;
            for (ReservedSeat seat : cinema.getReservedSeats()) {
                income += seat.ticket.getPrice();
            }
            int available = 0;
            for (Seat seat : cinema.getSeats()) {
                if (!seat.isReserved()) {
                    available++;
                }
            }
            int reserved = cinema.getReservedSeats().size();

            statistics.put("income", income);
            statistics.put("available", available);
            statistics.put("purchased", reserved);

            return new ResponseEntity<>(statistics, HttpStatus.OK);
        } else {
            ErrorResponse response = new ErrorResponse("The password is wrong!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    
    private static UUID generateUUID() {
        return UUID.randomUUID();
    }

}
