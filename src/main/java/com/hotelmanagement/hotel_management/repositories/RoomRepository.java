package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByStatusIgnoreCase(String vacant);
}