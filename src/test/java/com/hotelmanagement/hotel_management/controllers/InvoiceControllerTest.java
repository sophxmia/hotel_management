package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.InvoiceService;
import com.hotelmanagement.hotel_management.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Controller test class to handle operations related to invoice management.
 */
class InvoiceControllerTest {

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private GuestService guestService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private InvoiceController invoiceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Model model = mock(Model.class);

        String viewName = invoiceController.findAll(model);

        assertEquals("invoices", viewName);
        verify(invoiceService, times(1)).getInvoices();
        verify(model, times(1)).addAttribute("invoices", invoiceService.getInvoices());
    }

    @Test
    void delete() {
        int invoiceId = 1;

        String viewName = invoiceController.delete(invoiceId);

        assertEquals("redirect:/invoices", viewName);
        verify(invoiceService, times(1)).delete(invoiceId);
    }

    @Test
    void edit() {
        int invoiceId = 1;
        BigDecimal amount = BigDecimal.TEN;
        LocalDate issueDate = LocalDate.now();

        String viewName = invoiceController.edit(invoiceId, amount, issueDate);

        assertEquals("redirect:/invoices", viewName);
        verify(invoiceService, times(1)).edit(invoiceId, amount, issueDate);
    }

    @Test
    void showAddForm() {
        Model model = mock(Model.class);

        String viewName = invoiceController.showAddForm(model);

        assertEquals("add_invoice_form", viewName);
        verify(guestService, times(1)).getGuests();
        verify(reservationService, times(1)).getReservations();
    }

    @Test
    void calculateInvoiceAmount() {
        int reservationId = 1;
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(2));
        Room room = new Room();
        room.setCapacity(2);
        reservation.setRoom(room);
        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);

        BigDecimal amount = invoiceController.calculateInvoiceAmount(reservationId);

        assertEquals(BigDecimal.valueOf(200), amount);
    }
}