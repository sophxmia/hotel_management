package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Reservation entities.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    /**
     * Retrieves a list of reservations associated with a guest using the guest's ID.
     *
     * @param id The ID of the guest.
     * @return A list of reservations associated with the guest.
     */
    List<Reservation> findByGuest_Id(Integer id);

    /**
     * Retrieves a list of reservations associated with a guest using the guest's ID.
     * This method follows the Spring Data JPA naming convention for derived query methods.
     *
     * @param id The ID of the guest.
     * @return A list of reservations associated with the guest.
     */
    List<Reservation> findByGuestId(int id);
}