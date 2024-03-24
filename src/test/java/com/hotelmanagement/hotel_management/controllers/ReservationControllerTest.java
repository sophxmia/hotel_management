package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.ReservationService;
import com.hotelmanagement.hotel_management.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;

    @Mock
    private GuestService guestService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        when(reservationService.getReservations()).thenReturn(reservations);
        Model model = mock(Model.class);

        String viewName = reservationController.findAll(model);

        assertEquals("reservations", viewName);
        verify(reservationService, times(1)).getReservations();
        verify(model, times(1)).addAttribute("reservations", reservations);
    }

    @Test
    void reservationsForGuest() {
        int guestId = 1;
        List<Reservation> reservationsForGuest = new ArrayList<>();
        reservationsForGuest.add(new Reservation());
        when(reservationService.getReservationsForGuest(guestId)).thenReturn(reservationsForGuest);
        Model model = mock(Model.class);

        String viewName = reservationController.reservationsForGuest(guestId, model);

        assertEquals("guest_reservation", viewName);
        verify(reservationService, times(1)).getReservationsForGuest(guestId);
        verify(model, times(1)).addAttribute("reservations", reservationsForGuest);
    }

    @Test
    void delete() {
        int reservationId = 1;

        String viewName = reservationController.delete(reservationId);

        assertEquals("redirect:/reservations", viewName);
        verify(reservationService, times(1)).delete(reservationId);
    }

    @Test
    void edit() {
        int reservationId = 1;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        String viewName = reservationController.edit(reservationId, startDate, endDate);

        assertEquals("redirect:/reservations", viewName);
        verify(reservationService, times(1)).edit(reservationId, startDate, endDate);
    }

    @Test
    void showAddForm() {
        Model model = mock(Model.class);

        String viewName = reservationController.showAddForm(model);

        assertEquals("add_reservation_form", viewName);
        verify(guestService, times(1)).getGuests();
        verify(roomService, times(1)).getVacantRooms();
    }
}