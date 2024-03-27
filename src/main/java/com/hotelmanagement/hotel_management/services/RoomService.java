package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle operations related to rooms.
 */
@Service
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;

    /**
     * Retrieves all rooms.
     *
     * @return List of all rooms.
     */
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    /**
     * Retrieves a room by its ID.
     *
     * @param id The ID of the room to retrieve.
     * @return The room with the specified ID.
     * @throws IllegalArgumentException if the room with the given ID is not found.
     */
    public Room getRoomById(int id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + id + " not found"));
    }

    /**
     * Adds a new room.
     *
     * @param roomClass The class/type of the room.
     * @param capacity  The capacity/number of guests the room can accommodate.
     * @param status    The status of the room (e.g., "Vacant", "Occupied").
     */
    public void add(String roomClass, Integer capacity, String status) {
        roomRepository.save(new Room(roomClass, capacity, status));
    }

    /**
     * Deletes a room by its ID.
     *
     * @param id The ID of the room to delete.
     */
    public void delete(int id) {
        roomRepository.deleteById(id);
    }

    /**
     * Edits an existing room.
     *
     * @param roomId    The ID of the room to edit.
     * @param roomClass The updated class/type of the room.
     * @param capacity  The updated capacity/number of guests the room can accommodate.
     * @param status    The updated status of the room.
     */
    public void edit(int roomId, String roomClass, Integer capacity, String status) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id: " + roomId));

        String currentRoomClass = room.getRoomClass();
        Integer currentCapacity = room.getCapacity();
        String currentStatus = room.getStatus();

        if (roomClass != null && !roomClass.isEmpty()) {
            room.setRoomClass(roomClass);
        } else {
            room.setRoomClass(currentRoomClass);
        }

        if (capacity != null && capacity > 0) {
            room.setCapacity(capacity);
        } else {
            room.setCapacity(currentCapacity);
        }

        if (status != null && !status.isEmpty()) {
            room.setStatus(status);
        } else {
            room.setStatus(currentStatus);
        }

        roomRepository.save(room);
    }

    /**
     * Updates an existing room.
     *
     * @param room The room object to update.
     */
    public void updateRoom(Room room) {
        roomRepository.save(room);
    }

    /**
     * Retrieves all vacant rooms.
     *
     * @return List of vacant rooms.
     */
    public List<Room> getVacantRooms() {
        return roomRepository.findByStatusIgnoreCase("Vacant");
    }

}
