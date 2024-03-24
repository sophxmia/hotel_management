package com.hotelmanagement.hotel_management.controllers;

//import com.hotelmanagement.hotel_management.data.Invoice;
//import com.hotelmanagement.hotel_management.data.Reservation;
//import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.InvoiceService;
import com.hotelmanagement.hotel_management.services.PdfGenerationService;
import com.hotelmanagement.hotel_management.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

//import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
//import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvoiceControllerTest {

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private PdfGenerationService pdfGenerationService;

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

//    @Test
//    void addInvoice() {
//        int reservationId = 1;
//        Reservation reservation = new Reservation();
//        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);
//        when(reservation.getRoom()).thenReturn(new Room());
//        when(reservation.getRoom().getCapacity()).thenReturn(2);
//
//        String viewName = invoiceController.addInvoice(reservationId);
//
//        assertEquals("redirect:/invoices", viewName);
//        verify(invoiceService, times(1)).add(eq(reservationId), any(BigDecimal.class), any(LocalDate.class));
//    }

//    @Test
//    void calculateInvoiceAmount() {
//        Reservation reservation = new Reservation();
//        reservation.setStartDate(LocalDate.now());
//        reservation.setEndDate(LocalDate.now().plusDays(2));
//        when(reservationService.getReservationById(anyInt())).thenReturn(reservation);
//
//        BigDecimal amount = invoiceController.calculateInvoiceAmount(1);
//
//        assertEquals(BigDecimal.valueOf(50), amount);
//    }

//    @Test
//    void generatePdf() throws IOException {
//        int invoiceId = 1;
//        Invoice invoice = new Invoice();
//        when(invoiceService.getInvoiceById(invoiceId)).thenReturn(invoice);
//        byte[] pdfBytes = new byte[10];
//        when(pdfGenerationService.generateInvoicePdf(invoice)).thenReturn(pdfBytes);
//
//        ResponseEntity<byte[]> responseEntity = invoiceController.generatePdf(invoiceId);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("application/pdf", Objects.requireNonNull(responseEntity.getHeaders().getContentType()).toString());
//        assertEquals("inline; filename=invoice.pdf", responseEntity.getHeaders().getContentDisposition().toString());
//        assertEquals(pdfBytes.length, Objects.requireNonNull(responseEntity.getBody()).length);
//    }
}