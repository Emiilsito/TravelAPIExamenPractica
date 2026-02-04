package es.severo.travel_api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "flights",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"flight_number", "departure_date"})
        }
)
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number", length = 10, nullable = false)
    private String flightNumber;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FlightStatus status;

    @ManyToOne()
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @ManyToOne()
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departureAirport;

    @ManyToOne()
    @JoinColumn(name = "arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
