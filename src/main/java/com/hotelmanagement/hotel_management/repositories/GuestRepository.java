package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Guest entities.
 */
public interface GuestRepository extends JpaRepository<Guest, Integer> {
    /**
     * Searches for guests whose first name, last name, passport information, or contact number contains the specified strings.
     *
     * @param firstName     The string to search for in the first name.
     * @param lastName      The string to search for in the last name.
     * @param passportInfo  The string to search for in the passport information.
     * @param contactNumber The string to search for in the contact number.
     * @return A list of guests matching the search criteria.
     */
    List<Guest> findByFirstNameContainingOrLastNameContainingOrPassportInfoContainingOrContactNumberContaining(String firstName, String lastName, String passportInfo, String contactNumber);

}