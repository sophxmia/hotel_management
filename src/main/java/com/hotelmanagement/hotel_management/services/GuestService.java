package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Guest;
import com.hotelmanagement.hotel_management.repositories.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {
    private GuestRepository guestRepository;

    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }

    public void add(String firstName, String lastName, String passportInfo, String contactNumber) {
        guestRepository.save(new Guest(firstName, lastName, passportInfo, contactNumber));
    }

    public void delete(int id) {
        guestRepository.deleteById(id);
    }

    public List<Guest> searchGuests(String query) {
        return guestRepository.findByFirstNameContainingOrLastNameContainingOrPassportInfoContainingOrContactNumberContaining(query, query, query, query);
    }

}
