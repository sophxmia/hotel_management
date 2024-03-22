package com.hotelmanagement.hotel_management.repositories;

import com.hotelmanagement.hotel_management.data.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByGuest_Id(Integer id);

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId")
    List<Reservation> findByRoomId(Integer roomId);


}