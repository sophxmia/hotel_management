package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Reservation;
//import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.repositories.InvoiceRepository;
import com.hotelmanagement.hotel_management.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Service test class to handle operations related to reservations.
 */
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReservations() {
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        var reservations = reservationService.getReservations();

        assertNotNull(reservations);
        assertTrue(reservations.isEmpty());
    }

    @Test
    void getReservationById() {
        int reservationId = 1;
        Reservation expectedReservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(expectedReservation));

        var reservation = reservationService.getReservationById(reservationId);

        assertNotNull(reservation);
        assertEquals(expectedReservation, reservation);
    }

    @Test
    void getReservationsForGuest() {
        int guestId = 1;
        when(reservationRepository.findByGuest_Id(guestId)).thenReturn(Collections.emptyList());

        var reservations = reservationService.getReservationsForGuest(guestId);

        assertNotNull(reservations);
        assertTrue(reservations.isEmpty());
    }

    @Test
    void edit() {
        int reservationId = 1;
        Reservation reservation = new Reservation();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.edit(reservationId, startDate, endDate);

        assertEquals(startDate, reservation.getStartDate());
        assertEquals(endDate, reservation.getEndDate());
        verify(reservationRepository, times(1)).save(reservation);
    }
}