package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
