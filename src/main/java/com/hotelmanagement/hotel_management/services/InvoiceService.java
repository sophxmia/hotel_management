package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.repositories.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class to handle operations related to invoices.
 */
@Service
@AllArgsConstructor
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private ReservationService reservationService;

    /**
     * Retrieves all invoices.
     *
     * @return List of all invoices.
     */
    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    /**
     * Retrieves an invoice by ID.
     *
     * @param id The ID of the invoice to retrieve.
     * @return The invoice with the specified ID, or null if not found.
     */
    public Invoice getInvoiceById(Integer id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves an invoice by reservation ID.
     *
     * @param reservationId The ID of the reservation associated with the invoice.
     * @return The invoice associated with the specified reservation.
     */
    public Invoice getInvoiceByReservationId(int reservationId) {
        return invoiceRepository.findByReservationId(reservationId);
    }

    /**
     * Deletes an invoice by ID.
     *
     * @param id The ID of the invoice to delete.
     */
    public void delete(int id) {
        invoiceRepository.deleteById(id);
    }

    /**
     * Edits an existing invoice.
     *
     * @param invoiceId The ID of the invoice to edit.
     * @param amount    The updated amount for the invoice.
     * @param issueDate The updated issue date for the invoice.
     */
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

    /**
     * Updates the amount for an invoice associated with a reservation.
     *
     * @param reservationId The ID of the reservation.
     * @param newAmount     The new amount for the invoice.
     */
    public void updateAmountForReservation(int reservationId, BigDecimal newAmount) {
        Invoice invoice = invoiceRepository.findByReservation_Id(reservationId);
        if (invoice != null) {
            invoice.setAmount(newAmount);
            invoiceRepository.save(invoice);
        }
    }

    /**
     * Adds a new invoice for a reservation.
     *
     * @param reservationId The ID of the reservation.
     * @param amount        The amount for the invoice.
     * @param issueDate     The issue date for the invoice.
     */
    public void add(int reservationId, BigDecimal amount, LocalDate issueDate) {
        Reservation reservation = reservationService.getReservationById(reservationId);

        Invoice invoice = new Invoice();
        invoice.setReservation(reservation);
        invoice.setAmount(amount);
        invoice.setIssueDate(issueDate);

        invoiceRepository.save(invoice);
    }
}
