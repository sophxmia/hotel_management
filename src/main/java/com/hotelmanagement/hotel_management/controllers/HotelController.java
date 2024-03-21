package com.hotelmanagement.hotel_management.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/search")
    public String searchGuest(@RequestParam("searchQuery") String searchQuery, Model model) {
        String sqlSearch = "SELECT " +
                "G.guest_id, G.first_name, G.last_name, G.passport_info, G.contact_number, " +
                "R.reservation_id, R.start_date, R.end_date, " +
                "Ro.room_id, Ro.room_class, Ro.capacity, Ro.status, " +
                "I.invoice_id, I.amount, I.issue_date " +
                "FROM Guests G " +
                "LEFT JOIN Reservations R ON G.guest_id = R.guest_id " +
                "LEFT JOIN Rooms Ro ON R.room_id = Ro.room_id " +
                "LEFT JOIN Invoices I ON R.reservation_id = I.reservation_id " +
                "WHERE " +
                "G.first_name LIKE '%" + searchQuery + "%' OR " +
                "G.last_name LIKE '%" + searchQuery + "%' OR " +
                "G.passport_info LIKE '%" + searchQuery + "%' OR " +
                "G.contact_number LIKE '%" + searchQuery + "%' OR " +
                "R.start_date LIKE '%" + searchQuery + "%' OR " +
                "R.end_date LIKE '%" + searchQuery + "%' OR " +
                "Ro.room_class LIKE '%" + searchQuery + "%' OR " +
                "Ro.capacity LIKE '%" + searchQuery + "%' OR " +
                "Ro.status LIKE '%" + searchQuery + "%' OR " +
                "I.amount LIKE '%" + searchQuery + "%' OR " +
                "I.issue_date LIKE '%" + searchQuery + "%'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlSearch);
        model.addAttribute("hotel", rows);
        return "hotel";
    }
}
