package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.AvailableRoom;
import com.hotelbooking.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableRoomRepository extends JpaRepository<AvailableRoom, Long> {
    @Query("SELECT av FROM AvailableRoom av WHERE av.room.id = ?1 AND av.roomNumber <> ?2 AND av.occupied = true")
    AvailableRoom findTopByRoomIdAndRoomNumberNotAndOccupiedTrue(Long roomId, String roomNumber);

    @Query("SELECT av FROM AvailableRoom av WHERE av.room.id = ?1 AND av.occupied = false")
    AvailableRoom findTopByRoomIdAndOccupiedFalse(Long roomId);

    @Query("SELECT av FROM AvailableRoom av WHERE av.occupied = true")
    List<AvailableRoom> findAllOccupiedTrue();

    @Query("SELECT av FROM AvailableRoom av WHERE av.occupied = false")
    List<AvailableRoom> findAllOccupiedFalse();
    List<AvailableRoom> findByRoomId(Long roomId);

    List<AvailableRoom> findByRoomNumberContaining(String roomNumber);

}

