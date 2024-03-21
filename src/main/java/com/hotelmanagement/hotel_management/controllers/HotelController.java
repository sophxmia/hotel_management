package com.hotelmanagement.hotel_management.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


@Controller
@AllArgsConstructor
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("")
    public String getGuestsReservationsAndInvoices(Model model) {
        String sql = "SELECT " +
                "G.guest_id, G.first_name, G.last_name, G.passport_info, G.contact_number, " +
                "R.reservation_id, R.start_date, R.end_date, " +
                "Ro.room_id, Ro.room_class, Ro.capacity, Ro.status, " +
                "I.invoice_id, I.amount, I.issue_date " +
                "FROM Guests G " +
                "LEFT JOIN Reservations R ON G.guest_id = R.guest_id " +
                "LEFT JOIN Rooms Ro ON R.room_id = Ro.room_id " +
                "LEFT JOIN Invoices I ON R.reservation_id = I.reservation_id";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        model.addAttribute("hotel", rows);
        return "hotel";
    }

}
