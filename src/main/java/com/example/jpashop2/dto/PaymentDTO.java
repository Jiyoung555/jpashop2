package com.example.jpashop2.dto;

import com.example.jpashop2.domain.Member;
import com.example.jpashop2.domain.Payment;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    int amount;

    public Payment toPayment() {
        Payment payment = new Payment();
        payment.setAmount(amount);
        return payment;
    }
}
