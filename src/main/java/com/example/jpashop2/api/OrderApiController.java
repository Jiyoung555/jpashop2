package com.example.jpashop2.api;

import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.OrderForm;
import com.example.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @PostMapping("/api/order")
    public Long insertOrder(@RequestBody OrderForm form){
        log.info("form : " + form.toString());

//        Member member = form.toMember();
//        OrderItem orderItems = form.toOrderItem();
//
//        Delivery delivery = new Delivery();
//        delivery.setStatus(DeliveryStatus.READY);//이넘값 셋팅방법
//
//        Order order = new Order();
//        order.createOrder(member, delivery, orderItems);

        Long memberId = form.getLoginId();
        Long itemId = form.getItemId();
        int count = form.getCount();

        return orderService.order(memberId, itemId, count);
    }



}
