package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoomControllerTest {
    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Model model = mock(Model.class);

        String viewName = roomController.findAll(model);

        assertEquals("rooms", viewName);
        verify(roomService, times(1)).getRooms();
        verify(model, times(1)).addAttribute("rooms", roomService.getRooms());
    }

    @Test
    void add() {
        String roomClass = "Deluxe";
        int capacity = 2;
        String status = "Available";

        String viewName = roomController.add(roomClass, capacity, status);

        assertEquals("redirect:/rooms", viewName);
        verify(roomService, times(1)).add(roomClass, capacity, status);
    }

    @Test
    void delete() {
        int roomId = 1;

        String viewName = roomController.delete(roomId);

        assertEquals("redirect:/rooms", viewName);
        verify(roomService, times(1)).delete(roomId);
    }

    @Test
    void edit() {
        int roomId = 1;
        String roomClass = "Suite";
        int capacity = 3;
        String status = "Occupied";

        String viewName = roomController.edit(roomId, roomClass, capacity, status);

        assertEquals("redirect:/rooms", viewName);
        verify(roomService, times(1)).edit(roomId, roomClass, capacity, status);
    }
}