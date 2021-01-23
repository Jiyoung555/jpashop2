package com.example.jpashop2.service;
import com.example.jpashop2.domain.Payment;
import com.example.jpashop2.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public Long create(Payment payment) {
        Payment saved = paymentRepository.save(payment);
        return saved.getId();
    }
}
