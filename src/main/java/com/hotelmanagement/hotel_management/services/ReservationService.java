package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.data.Guest;
import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.repositories.InvoiceRepository;
import com.hotelmanagement.hotel_management.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;
    private GuestService guestService;
    private RoomService roomService;
    private InvoiceRepository invoiceRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation with id " + id + " not found"));
    }

    public List<Reservation> getReservationsForGuest(int guestId) {
        return reservationRepository.findByGuest_Id(guestId);
    }

    public void delete(int id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id: " + id));

        Set<Invoice> invoices = reservation.getInvoices();

        for (Invoice invoice : invoices) {
            invoiceRepository.deleteById(invoice.getId());
        }

        Room room = reservation.getRoom();
        room.setStatus("Vacant");
        roomService.updateRoom(room);

        reservationRepository.deleteById(id);
    }

    public void edit(int reservationId, LocalDate startDate, LocalDate endDate) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id: " + reservationId));

        LocalDate currentStartDate = reservation.getStartDate();
        LocalDate currentEndDate = reservation.getEndDate();

        if (startDate != null) {
            reservation.setStartDate(startDate);
        } else {
            reservation.setStartDate(currentStartDate);
        }
        if (endDate != null) {
            reservation.setEndDate(endDate);
        } else {
            reservation.setStartDate(currentEndDate);
        }
        reservationRepository.save(reservation);
    }

    public void add(int guestId, int roomId, String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        Guest guest = guestService.getGuestById(guestId);
        Room room = roomService.getRoomById(roomId);

        if (!isRoomAvailable(roomId)) {
            throw new IllegalArgumentException("Room is not available for the specified dates.");
        }

        Reservation reservation = new Reservation(guest, room, startDate, endDate);
        room.setStatus("Occupied");
        roomService.updateRoom(room);
        reservationRepository.save(reservation);
    }

    private boolean isRoomAvailable(int roomId) {
        Room room = roomService.getRoomById(roomId);

        return room.getStatus().equalsIgnoreCase("Vacant");
    }
}
