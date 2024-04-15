package com.hotelbooking.hotelbooking.services;

import com.hotelbooking.hotelbooking.model.Room;
import com.hotelbooking.hotelbooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private RoomRepository roomRepository;


    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void updateRoom(Long id,Room room) {
        Room existingRoom = findRoomById(id);
        existingRoom.setName(room.getName());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setNumberOfRooms(room.getNumberOfRooms());
        existingRoom.setPrice(room.getPrice());
        roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

}
