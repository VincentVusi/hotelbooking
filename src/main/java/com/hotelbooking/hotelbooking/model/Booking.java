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
    private List<AvailableRoom> availableRooms;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double totalCost;
    private String bookingNumber;
    @ManyToOne
    private Guest guest;

    public void setAvailableRooms(List<AvailableRoom> availableRooms) {
        this.availableRooms = availableRooms;
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

    public List<AvailableRoom> getAvailableRooms() {
        return availableRooms;
    }

    public void setRoom(List<AvailableRoom> rooms) {
        this.availableRooms = rooms;
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
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}