package com.hotelmanagement.hotel_management.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Controller test class to handle operations related to hotel management.
 */
class HotelControllerTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private HotelController hotelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGuestsReservationsAndInvoices() {
        Model model = mock(Model.class);
        List<Map<String, Object>> expectedRows = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("guest_id", 1);
        row.put("first_name", "John");
        expectedRows.add(row);
        when(jdbcTemplate.queryForList(anyString())).thenReturn(expectedRows);

        String viewName = hotelController.getGuestsReservationsAndInvoices(model);

        assertEquals("hotel", viewName);
        verify(jdbcTemplate, times(1)).queryForList(anyString());
        verify(model, times(1)).addAttribute(eq("hotel"), eq(expectedRows));
    }

    @Test
    void searchGuest() {
        Model model = mock(Model.class);
        String searchQuery = "John";
        List<Map<String, Object>> expectedRows = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("guest_id", 1);
        row.put("first_name", "John");
        expectedRows.add(row);
        when(jdbcTemplate.queryForList(anyString())).thenReturn(expectedRows);

        String viewName = hotelController.searchGuest(searchQuery, model);

        assertEquals("hotel", viewName);
        verify(jdbcTemplate, times(1)).queryForList(anyString());
        verify(model, times(1)).addAttribute(eq("hotel"), eq(expectedRows));
    }
}