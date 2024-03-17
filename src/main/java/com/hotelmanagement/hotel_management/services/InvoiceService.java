package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.repositories.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }
}
