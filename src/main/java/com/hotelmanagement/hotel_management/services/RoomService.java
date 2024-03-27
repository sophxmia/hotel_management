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

    public Room getRoomById(int id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + id + " not found"));
    }
    public void add(String roomClass, Integer capacity, String status) {
        roomRepository.save(new Room(roomClass, capacity, status));
    }

    public void delete(int id) {
        roomRepository.deleteById(id);
    }

    public void edit(int roomId, String roomClass, Integer capacity, String status) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id: " + roomId));

        // Отримати поточні значення полів, якщо вони порожні
        String currentRoomClass = room.getRoomClass();
        Integer currentCapacity = room.getCapacity();
        String currentStatus = room.getStatus();

        // Перевірка та збереження нових значень або збереження попередніх значень, якщо поля порожні
        if(roomClass != null && !roomClass.isEmpty()){
            room.setRoomClass(roomClass);
        } else {
            room.setRoomClass(currentRoomClass);
        }

        if(capacity != null && capacity > 0){
            room.setCapacity(capacity);
        } else {
            room.setCapacity(currentCapacity);
        }

        if(status != null && !status.isEmpty()) {
            room.setStatus(status);
        } else {
            room.setStatus(currentStatus);
        }

        roomRepository.save(room);
    }

    public void updateRoom(Room room) {
        roomRepository.save(room);
    }
    public List<Room> getVacantRooms() {
        return roomRepository.findByStatusIgnoreCase("Vacant");
    }

}
