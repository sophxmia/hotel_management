package com.hotelmanagement.hotel_management.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a guest staying in the hotel.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id", nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "passport_info", length = 20)
    private String passportInfo;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;
    /**
     * The set of reservations made by this guest.
     */
    @OneToMany(mappedBy = "guest")
    private Set<Reservation> reservations = new LinkedHashSet<>();

    /**
     * Constructs a new guest with the specified details.
     *
     * @param firstName     The first name of the guest.
     * @param lastName      The last name of the guest.
     * @param passportInfo  The passport information of the guest.
     * @param contactNumber The contact number of the guest.
     */
    public Guest(String firstName, String lastName, String passportInfo, String contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportInfo = passportInfo;
        this.contactNumber = contactNumber;
    }
}