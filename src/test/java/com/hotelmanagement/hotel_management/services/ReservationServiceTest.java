package com.hotelmanagement.hotel_management.services;

//import com.hotelmanagement.hotel_management.data.Guest;
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

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

//    @Mock
//    private GuestService guestService;
//
//    @Mock
//    private RoomService roomService;
//
//    @Mock
//    private InvoiceRepository invoiceRepository;

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

//    @Test
//    void delete() {
//        int reservationId = 1;
//        Reservation reservation = new Reservation();
//        Room room = new Room();
//        room.setStatus("Occupied");
//        reservation.setRoom(room);
//        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
//
//        reservationService.delete(reservationId);
//
//        verify(invoiceRepository, times(1)).deleteById(anyInt());
//        verify(reservationRepository, times(1)).deleteById(reservationId);
//        assertEquals("Vacant", room.getStatus());
//    }

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

//    @Test
//    void add() {
//        int guestId = 1;
//        int roomId = 1;
//        String startDateStr = "2024-03-30";
//        String endDateStr = "2024-03-31";
//        Guest guest = new Guest();
//        Room room = new Room();
//        when(guestService.getGuestById(guestId)).thenReturn(guest);
//        when(roomService.getRoomById(roomId)).thenReturn(room);
//
//        reservationService.add(guestId, roomId, startDateStr, endDateStr);
//
//        verify(reservationRepository, times(1)).save(any(Reservation.class));
//        assertEquals("Occupied", room.getStatus());
//    }
}