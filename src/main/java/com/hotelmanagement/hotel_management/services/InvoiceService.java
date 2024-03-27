package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.repositories.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private ReservationService reservationService;

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Integer id) {
        return invoiceRepository.findById(id).orElse(null);
    }


    public void delete(int id) {
        invoiceRepository.deleteById(id);
    }

    public void edit(int invoiceId, BigDecimal amount, LocalDate issueDate) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id: " + invoiceId));

        BigDecimal currentAmount = invoice.getAmount();
        LocalDate currentIssueDate = invoice.getIssueDate();

        if (amount != null) {
            invoice.setAmount(amount);
        } else {
            invoice.setAmount(currentAmount);
        }
        if (issueDate != null) {
            invoice.setIssueDate(issueDate);
        } else {
            invoice.setIssueDate(currentIssueDate);
        }
        invoiceRepository.save(invoice);
    }

    public void add(int reservationId, BigDecimal amount, LocalDate issueDate) {
        Reservation reservation = reservationService.getReservationById(reservationId);

        Invoice invoice = new Invoice();
        invoice.setReservation(reservation);
        invoice.setAmount(amount);
        invoice.setIssueDate(issueDate);

        invoiceRepository.save(invoice);
    }
}
