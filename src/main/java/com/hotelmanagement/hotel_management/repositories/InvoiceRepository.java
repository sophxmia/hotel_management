package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByReservationId(Integer id);

    Invoice findByReservation_Id(int reservationId);
    Invoice findByReservationId(int reservationId);
}