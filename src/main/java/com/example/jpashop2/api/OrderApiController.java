package com.example.jpashop2.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderApiController {

    @PostMapping("/api/order")
    public Long insertOrder(@RequestBody OrderForm form){
        log.info("form : " + form.toString());
        Orders order = form.toEntity();
        Orders saved = orderRepository.save(order);
        return saved.getId();
    }

}
