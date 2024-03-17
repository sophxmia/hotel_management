package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
