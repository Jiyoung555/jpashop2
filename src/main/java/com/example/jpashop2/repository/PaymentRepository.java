package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
