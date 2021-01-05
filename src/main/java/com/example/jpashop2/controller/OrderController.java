package com.example.jpashop2.controller;

import com.example.jpashop2.domain.*;
import com.example.jpashop2.service.MemberService;
import com.example.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/order")
    public String orderList(Model model, HttpSession httpSession){
        Object temp = httpSession.getAttribute("loginId");
        Long memberId = Long.valueOf(String.valueOf(temp));//Object -> Long 타입변환
        log.info("memberId : " + memberId);

        Member loginMember = memberService.findOne(memberId);
        List<Order> myOrderList = loginMember.getOrders();

        //Iterable<Order> myOrderList = orderService.findMyOrders(memberId); //안됨

        log.info("myOrderList : " + myOrderList);
        model.addAttribute("myOrderList", myOrderList);

        List<Address> addressList = new ArrayList<>();

        for(int i = 0; i < myOrderList.size(); i++){
            Order order = myOrderList.get(i);
            Delivery delivery = order.getDelivery();
            Address address = delivery.getAddress();
            log.info("address : " + address);
            addressList.add(address);
        }
        model.addAttribute("addressList", addressList);

        //myOrderList + addressList 합치는 방법??

        return "orders/orderList";
    }

    @GetMapping("/orderShow/{id}")
    public String orderShow(@PathVariable Long id, Model model){
        Order order = orderService.findOne(id);
        model.addAttribute("order", order);
        return "orders/orderShow";
    }



}
