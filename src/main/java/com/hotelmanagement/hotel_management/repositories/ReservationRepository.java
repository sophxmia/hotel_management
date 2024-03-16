package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}