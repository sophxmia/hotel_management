package com.hotelmanagement.hotel_management.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an invoice for a reservation in the hotel.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false)
    private Integer id;
    /**
     * The reservation associated with this invoice.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    /**
     * The amount to be paid for this invoice.
     */
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;
    /**
     * The date when the invoice was issued.
     */
    @Column(name = "issue_date")
    private LocalDate issueDate;

    /**
     * Constructs a new invoice with the specified details.
     *
     * @param reservation The reservation associated with this invoice.
     * @param amount      The amount to be paid for this invoice.
     * @param issueDate   The date when the invoice was issued.
     */
    public Invoice(Reservation reservation, BigDecimal amount, LocalDate issueDate) {
        this.reservation = reservation;
        this.amount = amount;
        this.issueDate = issueDate;
    }

}