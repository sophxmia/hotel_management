package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsForGuest(int guestId) {
        return reservationRepository.findByGuest_Id(guestId);
    }

    public void delete(int id) {
        reservationRepository.deleteById(id);
    }

    public void edit(int reservationId, LocalDate startDate, LocalDate endDate) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id: " + reservationId));
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservationRepository.save(reservation);
    }
}
