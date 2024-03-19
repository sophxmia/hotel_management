package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Guest;
//import com.hotelmanagement.hotel_management.data.GuestInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
//    List<GuestInfo> findByIdNotNull();
    List<Guest> findByFirstNameContainingOrLastNameContainingOrPassportInfoContainingOrContactNumberContaining(String firstName, String lastName, String passportInfo, String contactNumber);

}