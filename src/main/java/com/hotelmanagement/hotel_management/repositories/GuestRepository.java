package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
}