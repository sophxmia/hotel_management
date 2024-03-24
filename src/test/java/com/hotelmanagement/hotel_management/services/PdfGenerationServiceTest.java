package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Guest;
import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.data.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PdfGenerationServiceTest {

    @Test
    void generateInvoicePdf() throws IOException {
        Invoice invoice = new Invoice();
        Reservation reservation = new Reservation();
        Guest guest = new Guest();
        Room room = new Room();

        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setPassportInfo("AB123456");
        guest.setContactNumber("+1234567890");

        room.setRoomClass("Standard");
        room.setCapacity(2);
        room.setStatus("Vacant");

        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setStartDate(LocalDate.of(2024, 3, 24));
        reservation.setEndDate(LocalDate.of(2024, 3, 30));

        invoice.setReservation(reservation);

        invoice.setAmount(new BigDecimal("500.00"));
        invoice.setIssueDate(LocalDate.now());

        PdfGenerationService pdfGenerationService = new PdfGenerationService();
        byte[] pdfBytes = pdfGenerationService.generateInvoicePdf(invoice);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }
}