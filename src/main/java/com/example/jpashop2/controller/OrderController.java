package com.example.jpashop2.controller;
import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.MyOrdersDTO;
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
        Object temp = httpSession.getAttribute("loginId");//로그인 Id
        Long memberId = Long.valueOf(String.valueOf(temp));//Object -> Long 타입변환
        log.info("memberId : " + memberId);
        Member loginMember = memberService.findOne(memberId);//로그인 Member
        List<Order> myOrders = loginMember.getOrders();//Order 전부 가져오기
        //Iterable<Order> myOrderList = orderService.findMyOrders(memberId); //안됨

        List<MyOrdersDTO> myOrderDetail = new ArrayList<>();//리스트 틀

        for(int i = 0; i < myOrders.size(); i++){
            Order order = myOrders.get(i);
            Delivery delivery = order.getDelivery();
            List<OrderItem> orderItems = order.getOrderItems();

            MyOrdersDTO myOrder = new MyOrdersDTO(order, delivery, orderItems);
            myOrderDetail.add(myOrder);
        }

        model.addAttribute("myOrderDetail", myOrderDetail);
        return "orders/orderList";
    }

    @GetMapping("/orderShow/{orderId}")
    public String orderShow(@PathVariable Long orderId, Model model){
        Order order = orderService.findOne(orderId);
        Delivery delivery = order.getDelivery();
        List<OrderItem> orderItems = order.getOrderItems();
        MyOrdersDTO myOrder = new MyOrdersDTO(order, delivery, orderItems);
        model.addAttribute("myOrder", myOrder);
        return "orders/orderShow";
    }



}
