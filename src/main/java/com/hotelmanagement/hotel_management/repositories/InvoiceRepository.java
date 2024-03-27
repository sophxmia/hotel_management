package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    void deleteByReservationId(int reservationId);
}