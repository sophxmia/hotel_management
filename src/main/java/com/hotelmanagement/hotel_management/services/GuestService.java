package com.hotelmanagement.hotel_management.services;

import com.hotelmanagement.hotel_management.data.Guest;
import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.data.Room;
import com.hotelmanagement.hotel_management.repositories.GuestRepository;
import com.hotelmanagement.hotel_management.repositories.InvoiceRepository;
import com.hotelmanagement.hotel_management.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle operations related to guests.
 */
@Service
@AllArgsConstructor
public class GuestService {
    private GuestRepository guestRepository;
    private InvoiceRepository invoiceRepository;
    private ReservationRepository reservationRepository;
    private RoomService roomService;

    /**
     * Retrieves all guests.
     *
     * @return List of all guests.
     */
    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }

    /**
     * Retrieves a guest by ID.
     *
     * @param id The ID of the guest to retrieve.
     * @return The guest with the specified ID.
     * @throws IllegalArgumentException If no guest is found with the given ID.
     */
    public Guest getGuestById(int id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guest with id " + id + " not found"));
    }

    /**
     * Adds a new guest.
     *
     * @param firstName     The first name of the guest.
     * @param lastName      The last name of the guest.
     * @param passportInfo  The passport information of the guest.
     * @param contactNumber The contact number of the guest.
     */
    public void add(String firstName, String lastName, String passportInfo, String contactNumber) {
        guestRepository.save(new Guest(firstName, lastName, passportInfo, contactNumber));
    }

    /**
     * Deletes a guest by ID.
     *
     * @param id The ID of the guest to delete.
     */
    public void delete(int id) {
        // Отримання всіх резервацій, які посилаються на гостя з вказаним ідентифікатором
        List<Reservation> reservations = reservationRepository.findByGuestId(id);

        // Видалення всіх залежних резервацій та інвойсів
        for (Reservation reservation : reservations) {
            // Отримання всіх інвойсів, які посилаються на резервацію
            List<Invoice> invoices = invoiceRepository.findByReservationId(reservation.getId());

            // Видалення всіх інвойсів, які посилаються на резервацію
            for (Invoice invoice : invoices) {
                invoiceRepository.delete(invoice);
            }

            Room room = reservation.getRoom();
            room.setStatus("Vacant");
            roomService.updateRoom(room);
            reservationRepository.delete(reservation);
        }

        guestRepository.deleteById(id);
    }

    /**
     * Edits a guest's information.
     *
     * @param guestId       The ID of the guest to edit.
     * @param firstName     The updated first name of the guest.
     * @param lastName      The updated last name of the guest.
     * @param passportInfo  The updated passport information of the guest.
     * @param contactNumber The updated contact number of the guest.
     */
    public void edit(int guestId, String firstName, String lastName, String passportInfo, String contactNumber) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new IllegalArgumentException("Invalid guest Id: " + guestId));

        if (firstName != null && !firstName.isEmpty()) {
            guest.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            guest.setLastName(lastName);
        }
        if (passportInfo != null && !passportInfo.isEmpty()) {
            guest.setPassportInfo(passportInfo);
        }
        if (contactNumber != null && !contactNumber.isEmpty()) {
            guest.setContactNumber(contactNumber);
        }
        guestRepository.save(guest);
    }

    /**
     * Searches for guests based on a query string.
     *
     * @param query The query string to search for.
     * @return List of guests matching the search query.
     */
    public List<Guest> searchGuests(String query) {
        return guestRepository.findByFirstNameContainingOrLastNameContainingOrPassportInfoContainingOrContactNumberContaining(query, query, query, query);
    }

}
