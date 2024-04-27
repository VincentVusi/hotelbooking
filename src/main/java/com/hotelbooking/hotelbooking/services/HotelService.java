package com.hotelbooking.hotelbooking.services;

import com.hotelbooking.hotelbooking.model.AvailableRoom;
import com.hotelbooking.hotelbooking.model.Hotel;
import com.hotelbooking.hotelbooking.model.Image;
import com.hotelbooking.hotelbooking.model.Room;
import com.hotelbooking.hotelbooking.repository.AvailableRoomRepository;
import com.hotelbooking.hotelbooking.repository.HotelRepository;
import com.hotelbooking.hotelbooking.repository.ImageRepository;
import com.hotelbooking.hotelbooking.repository.RoomRepository;
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
        roomRepository.deleteById(id);
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

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
    public List<Room> getAllRoomsForThisHotel(Long id){
        return roomRepository.getAllRoomsForThisHotel(id);
    }

    public AvailableRoom getAvailableRoom(Long roomId , String roomNumber){
        return availableRoomRepository.findTopByRoomIdAndRoomNumberNotAndOccupiedTrue(roomId,roomNumber);
    }
    public void saveImage(Image image){
        imageRepository.save(image);
    }

}
