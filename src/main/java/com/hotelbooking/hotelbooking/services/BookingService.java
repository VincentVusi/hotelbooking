package com.hotelbooking.hotelbooking.services;

import com.hotelbooking.hotelbooking.model.Booking;
import com.hotelbooking.hotelbooking.model.Guest;
import com.hotelbooking.hotelbooking.repository.BookingRepository;
import com.hotelbooking.hotelbooking.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private BookingRepository bookingRepository;


    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }
    public void saveGuest(Guest guest) {
        guestRepository.save(guest);
    }

    public Guest getGuestById(Long id) {
        return guestRepository.findById(id).orElse(null);
    }

    public void updateGuest(Long id,Guest guest) {
        Guest existingGuest = getGuestById(id);
        existingGuest.setEmail(guest.getEmail());
        existingGuest.setPhone(guest.getPhone());
        guestRepository.save(existingGuest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void updateBooking(Long id,Booking booking) {
        Booking existingBooking = getBookingById(id);
        bookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
