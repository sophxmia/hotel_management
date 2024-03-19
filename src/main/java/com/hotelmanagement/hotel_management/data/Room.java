package com.hotelmanagement.hotel_management.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "room")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    public Room(String roomClass, Integer capacity, String status) {
        this.roomClass = roomClass;
        this.capacity = capacity;
        this.status = status;
    }
}