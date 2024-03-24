package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Guest;
import com.hotelmanagement.hotel_management.repositories.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGuests() {
        List<Guest> expectedGuests = new ArrayList<>();
        when(guestRepository.findAll()).thenReturn(expectedGuests);

        List<Guest> actualGuests = guestService.getGuests();

        assertEquals(expectedGuests, actualGuests);
        verify(guestRepository, times(1)).findAll();
    }

    @Test
    void getGuestById() {
        int guestId = 1;
        Guest expectedGuest = new Guest();
        when(guestRepository.findById(guestId)).thenReturn(Optional.of(expectedGuest));

        Guest actualGuest = guestService.getGuestById(guestId);

        assertEquals(expectedGuest, actualGuest);
        verify(guestRepository, times(1)).findById(guestId);
    }

    @Test
    void add() {
        String firstName = "John";
        String lastName = "Doe";
        String passportInfo = "AB124453";
        String contactNumber = "+1347944680";

        guestService.add(firstName, lastName, passportInfo, contactNumber);

        verify(guestRepository, times(1)).save(any(Guest.class));
    }

    @Test
    void delete() {
        int guestId = 1;

        guestService.delete(guestId);

        verify(guestRepository, times(1)).deleteById(guestId);
    }

    @Test
    void edit() {
        int guestId = 1;
        Guest guest = new Guest();
        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        guestService.edit(guestId, "Jane", "Doe", "AB124457", "+5347944680");

        verify(guestRepository, times(1)).save(guest);
        assertEquals("Jane", guest.getFirstName());
        assertEquals("Doe", guest.getLastName());
        assertEquals("AB124457", guest.getPassportInfo());
        assertEquals("+5347944680", guest.getContactNumber());
    }

    @Test
    void searchGuests() {
        String query = "John";
        List<Guest> expectedGuests = new ArrayList<>();
        when(guestRepository.findByFirstNameContainingOrLastNameContainingOrPassportInfoContainingOrContactNumberContaining(query, query, query, query)).thenReturn(expectedGuests);

        List<Guest> actualGuests = guestService.searchGuests(query);

        assertEquals(expectedGuests, actualGuests);
        verify(guestRepository, times(1)).findByFirstNameContainingOrLastNameContainingOrPassportInfoContainingOrContactNumberContaining(query, query, query, query);
    }
}