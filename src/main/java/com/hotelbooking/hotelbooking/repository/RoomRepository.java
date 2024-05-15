package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = ?1")
    List<Room> getAllRoomsForThisHotel(Long id);
    List<Room> findByNameContaining(String name);
    List<Room> findByDescriptionContaining(String description);
    List<Room> findByPrice(Double price);
    // This method assumes that the Room model has a Hotel object and the Hotel model has a name field
    List<Room> findByHotelNameContaining(String hotelName);
    List<Room> findByHotelId(Long hotelId);

}

