package com.hotelbooking.hotelbooking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<Room> rooms;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double totalCost;
    private String bookingNumber;
    @ManyToOne
    private Guest guest;

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRoom(List<Room> rooms) {
        this.rooms = rooms;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public Double getTotalCost() {
        double totalRoomPrice = 0.0; // Initialize the total price

        for (Room room : this.rooms) {
            totalRoomPrice += room.getPrice();
        }

        return totalRoomPrice;
    }

}