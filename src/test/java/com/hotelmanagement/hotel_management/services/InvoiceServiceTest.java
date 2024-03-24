package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.repositories.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getInvoices() {
        when(invoiceRepository.findAll()).thenReturn(List.of(new Invoice(), new Invoice()));

        var invoices = invoiceService.getInvoices();

        assertNotNull(invoices);
        assertEquals(2, invoices.size());
    }

    @Test
    void getInvoiceById() {
        int invoiceId = 1;
        Invoice expectedInvoice = new Invoice();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(expectedInvoice));

        var invoice = invoiceService.getInvoiceById(invoiceId);

        assertNotNull(invoice);
        assertEquals(expectedInvoice, invoice);
    }

    @Test
    void delete() {
        int invoiceId = 1;

        invoiceService.delete(invoiceId);

        verify(invoiceRepository, times(1)).deleteById(invoiceId);
    }

    @Test
    void edit() {
        int invoiceId = 1;
        BigDecimal amount = BigDecimal.valueOf(100);
        LocalDate issueDate = LocalDate.now();
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        invoiceService.edit(invoiceId, amount, issueDate);

        assertEquals(amount, invoice.getAmount());
        assertEquals(issueDate, invoice.getIssueDate());
        verify(invoiceRepository, times(1)).save(invoice);
    }

    @Test
    void add() {
        int reservationId = 1;
        BigDecimal amount = BigDecimal.valueOf(100);
        LocalDate issueDate = LocalDate.now();
        Reservation reservation = new Reservation();
        when(reservationService.getReservationById(reservationId)).thenReturn(reservation);

        invoiceService.add(reservationId, amount, issueDate);

        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }
}
