package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Service test class to handle operations related to rooms.
 */
class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRooms() {
        List<Room> expectedRooms = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(expectedRooms);

        List<Room> actualRooms = roomService.getRooms();

        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void getRoomById() {
        int roomId = 1;
        Room expectedRoom = new Room();
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(expectedRoom));

        Room actualRoom = roomService.getRoomById(roomId);

        assertEquals(expectedRoom, actualRoom);
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void add() {
        String roomClass = "Standard";
        int capacity = 2;
        String status = "Vacant";

        roomService.add(roomClass, capacity, status);

        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void delete() {
        int roomId = 1;

        roomService.delete(roomId);

        verify(roomRepository, times(1)).deleteById(roomId);
    }

    @Test
    void edit() {
        int roomId = 1;
        Room room = new Room();
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        roomService.edit(roomId, "Deluxe", 3, "Occupied");

        verify(roomRepository, times(1)).save(room);
        assertEquals("Deluxe", room.getRoomClass());
        assertEquals(3, room.getCapacity());
        assertEquals("Occupied", room.getStatus());
    }

    @Test
    void updateRoom() {
        Room room = new Room();

        roomService.updateRoom(room);

        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void getVacantRooms() {
        List<Room> expectedRooms = new ArrayList<>();
        when(roomRepository.findByStatusIgnoreCase("Vacant")).thenReturn(expectedRooms);

        List<Room> actualRooms = roomService.getVacantRooms();

        assertEquals(expectedRooms, actualRooms);
        verify(roomRepository, times(1)).findByStatusIgnoreCase("Vacant");
    }
}