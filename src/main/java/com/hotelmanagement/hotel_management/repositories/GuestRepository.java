package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

    List<Guest> findByFirstNameContainingOrLastNameContainingOrPassportInfoContainingOrContactNumberContaining(String firstName, String lastName, String passportInfo, String contactNumber);

}