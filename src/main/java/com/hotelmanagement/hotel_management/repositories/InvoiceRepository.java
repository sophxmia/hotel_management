package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Invoice entities.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    /**
     * Retrieves a list of invoices associated with a reservation using the reservation's ID.
     *
     * @param id The ID of the reservation.
     * @return A list of invoices associated with the reservation.
     */
    List<Invoice> findByReservationId(Integer id);

    /**
     * Retrieves a single invoice associated with a reservation using the reservation's ID.
     * This method follows the Spring Data JPA naming convention for derived query methods.
     *
     * @param reservationId The ID of the reservation.
     * @return The invoice associated with the reservation.
     */
    Invoice findByReservation_Id(int reservationId);

    /**
     * Retrieves a single invoice associated with a reservation using the reservation's ID.
     * This method follows a slightly different naming convention for the method name.
     *
     * @param reservationId The ID of the reservation.
     * @return The invoice associated with the reservation.
     */
    Invoice findByReservationId(int reservationId);
}