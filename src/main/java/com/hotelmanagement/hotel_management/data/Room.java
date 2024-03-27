package com.hotelmanagement.hotel_management.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a room in the hotel.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Integer id;

    @Column(name = "room_class", length = 50)
    private String roomClass;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "status", length = 10)
    private String status;
    /**
     * The set of reservations associated with this room.
     */
    @OneToMany(mappedBy = "room")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    /**
     * Constructs a new room with the specified details.
     *
     * @param roomClass The class/type of the room.
     * @param capacity  The capacity of the room (number of guests it can accommodate).
     * @param status    The status of the room.
     */
    public Room(String roomClass, Integer capacity, String status) {
        this.roomClass = roomClass;
        this.capacity = capacity;
        this.status = status;
    }
}