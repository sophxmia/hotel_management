package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    @GetMapping("")
    public String findAll(Model model){
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "invoices";
    }
}
