package com.example.jpashop2.api;

import com.example.jpashop2.domain.Item;
import com.example.jpashop2.dto.OrderForm;
import com.example.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @PostMapping("/api/order")
    public Long insertOrder(@RequestBody OrderForm form){
        log.info("form : " + form.toString());

        Long memberId = form.getMemberId();
        Long itemId = form.getItemId();
        int count = form.getCount();

        return orderService.order(memberId, itemId, count);
    }

    @DeleteMapping("/api/order/{orderId}")
    public Long destroyOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return orderId;
    }

    //안됨 (store)
    @PostMapping("/api/orderCheckbox")
    public Long insertOrderCheckbox(@RequestBody OrderForm form){
        log.info("form : " + form);
        return 0L;
    }

    //안됨 (cart)
    @PostMapping("/api/checkedCartToOrder")
    public Long checkedCartToOrder(@RequestBody OrderForm form){
        log.info("form : " + form);
        return 0L;
    }

}
