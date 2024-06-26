package com.hotelbooking.hotelbooking.services;

import com.hotelbooking.hotelbooking.model.*;
import com.hotelbooking.hotelbooking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AvailableRoomRepository availableRoomRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void updateRoom(Long id,Room room) {
        Room existingRoom = getRoomById(id);
        existingRoom.setName(room.getName());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setPrice(room.getPrice());
        roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {

        List<AvailableRoom> availableRooms = availableRoomRepository.findByRoomId(id);
        List<Booking> bookings = bookingRepository.findByRoomId(id);

        // Set the hotel field of each room to null
        for (AvailableRoom availableRoom : availableRooms) {
            availableRoomRepository.delete(availableRoom);
        }
        for (Booking booking : bookings) {
            bookingRepository.delete(booking);
        }
        roomRepository.deleteById(id);
    }

    public List<AvailableRoom> getAllAvailableRooms() {
        return availableRoomRepository.findAll();
    }
    public void saveAvailableRoom(AvailableRoom availableRoom) {
        availableRoomRepository.save(availableRoom);
    }

    public AvailableRoom getAvailableRoomById(Long id) {
        return availableRoomRepository.findById(id).orElse(null);
    }

    public void updateAvailableRoom(Long id,AvailableRoom availableRoom) {
        AvailableRoom existingAvailableRoom = getAvailableRoomById(id);
        existingAvailableRoom.setRoomNumber(availableRoom.getRoomNumber());
        existingAvailableRoom.setRoom(availableRoom.getRoom());
        existingAvailableRoom.setOccupied(availableRoom.getOccupied());
        availableRoomRepository.save(existingAvailableRoom);
    }

    public void deleteAvailableRoom(Long id) {
        availableRoomRepository.deleteById(id);
    }

    public List<Room> findByNameContaining(String name) {
        return roomRepository.findByNameContaining(name);
    }
    public List<Room> findByDescription(String description) {
        return roomRepository.findByDescriptionContaining(description);
    }

    public List<Room> findByPrice(Double price) {
        return roomRepository.findByPrice(price);
    }

    public List<Room> findByHotelName(String hotelName) {
        return roomRepository.findByHotelNameContaining(hotelName);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public void updateHotel(Long id,Hotel hotel) {
        Hotel existingHotel = getHotelById(id);
        existingHotel.setName(hotel.getName());
        existingHotel.setLocation(hotel.getLocation());
        existingHotel.setPhoneNumber(hotel.getPhoneNumber());
        existingHotel.setEmail(hotel.getEmail());
        hotelRepository.save(existingHotel);
    }

    public void deleteHotel(Long hotelId) {
        // Find all rooms associated with the hotel
        List<Room> rooms = roomRepository.findByHotelId(hotelId);

        // Set the hotel field of each room to null
        for (Room room : rooms) {
            roomRepository.deleteById(room.getId());
        }

        // Now it's safe to delete the hotel
        hotelRepository.deleteById(hotelId);
    }
    public List<Room> getAllRoomsForThisHotel(Long id){
        return roomRepository.getAllRoomsForThisHotel(id);
    }

    public AvailableRoom getAvailableRoom(Long roomId , String roomNumber){
        return availableRoomRepository.findTopByRoomIdAndRoomNumberNotAndOccupiedTrue(roomId,roomNumber);
    }
    public AvailableRoom getEmptyRoom(Long roomType){
        return availableRoomRepository.findTopByRoomIdAndOccupiedFalse(roomType);
    }
    public List<AvailableRoom> getAllUnoccupiedRooms(){
        return availableRoomRepository.findAllOccupiedFalse();
    }
    public List<AvailableRoom> getAllOccupiedRooms(){
        return availableRoomRepository.findAllOccupiedTrue();
    }

    public List<AvailableRoom> getByRoomNumberContaining(String roomNumber){
        return availableRoomRepository.findByRoomNumberContaining(roomNumber);
    }

    public void saveImage(Image image){
        imageRepository.save(image);
    }

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
    public List<Hotel> findByName(String name) {
        return hotelRepository.findByNameContaining(name);
    }

    public List<Hotel> findByLocation(String location) {
        return hotelRepository.findByLocationContaining(location);
    }

    public List<Hotel> findByPhoneNumber(String phoneNumber) {
        return hotelRepository.findByPhoneNumberContaining(phoneNumber);
    }
    public List<Booking> getAllBookingByUserId(Long id)
    {
        return bookingRepository.findByAppUserId(id);
    }
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }


    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }


}
