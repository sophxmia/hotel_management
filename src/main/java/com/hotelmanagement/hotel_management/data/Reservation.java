package com.hotelmanagement.hotel_management.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Integer id;
    /**
     * The guest who made this reservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private Guest guest;
    /**
     * The room reserved by this reservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    /**
     * The set of invoices associated with this reservation.
     */
    @OneToMany(mappedBy = "reservation")
    private Set<Invoice> invoices = new LinkedHashSet<>();

    /**
     * Constructs a new reservation with the specified details.
     *
     * @param guest     The guest who made this reservation.
     * @param room      The room reserved by this reservation.
     * @param startDate The start date of the reservation.
     * @param endDate   The end date of the reservation.
     */
    public Reservation(Guest guest, Room room, LocalDate startDate, LocalDate endDate) {
        this.guest = guest;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}