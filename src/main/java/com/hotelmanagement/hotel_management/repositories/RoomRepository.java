package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Room entities.
 */
public interface RoomRepository extends JpaRepository<Room, Integer> {
    /**
     * Retrieves a list of rooms based on their status while ignoring the case of the status string.
     *
     * @param vacant The status of the room.
     * @return A list of rooms with the specified status, ignoring case.
     */
    List<Room> findByStatusIgnoreCase(String vacant);
}