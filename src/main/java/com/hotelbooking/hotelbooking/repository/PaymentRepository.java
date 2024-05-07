package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.Guest;
import com.hotelbooking.hotelbooking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

