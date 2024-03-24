package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Guest;
import com.hotelmanagement.hotel_management.services.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestControllerTest {

    @Mock
    private GuestService guestService;

    @InjectMocks
    private GuestController guestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showAll() {
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("John", "Doe", "AB124453", "+1347944680"));
        when(guestService.getGuests()).thenReturn(guests);
        Model model = mock(Model.class);

        String viewName = guestController.showAll(model);

        assertEquals("guests", viewName);
        verify(guestService, times(1)).getGuests();
        verify(model, times(1)).addAttribute("guests", guests);
    }

    @Test
    void add() {
        String firstName = "John";
        String lastName = "Doe";
        String passportInfo = "AB124453";
        String contactNumber = "+1347944680";

        String viewName = guestController.add(firstName, lastName, passportInfo, contactNumber);

        assertEquals("redirect:/guests", viewName);
        verify(guestService, times(1)).add(firstName, lastName, passportInfo, contactNumber);
    }

    @Test
    void delete() {
        int guestId = 1;

        String viewName = guestController.delete(guestId);

        assertEquals("redirect:/guests", viewName);
        verify(guestService, times(1)).delete(guestId);
    }

    @Test
    void edit() {
        int guestId = 1;
        String firstName = "John";
        String lastName = "Doe";
        String passportInfo = "AB124453";
        String contactNumber = "+1347944680";

        String viewName = guestController.edit(guestId, firstName, lastName, passportInfo, contactNumber);

        assertEquals("redirect:/guests", viewName);
        verify(guestService, times(1)).edit(guestId, firstName, lastName, passportInfo, contactNumber);
    }

    @Test
    void search() {
        String query = "John";
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("John", "Doe", "AB124453", "+1347944680"));
        when(guestService.searchGuests(query)).thenReturn(guests);
        Model model = mock(Model.class);

        String viewName = guestController.search(query, model);

        assertEquals("guests", viewName);
        verify(guestService, times(1)).searchGuests(query);
        verify(model, times(1)).addAttribute("guests", guests);
    }
}