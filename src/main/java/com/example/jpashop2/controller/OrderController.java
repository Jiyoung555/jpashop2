package com.example.jpashop2.controller;
import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.MyOrdersDTO;
import com.example.jpashop2.dto.OrderSearch;
import com.example.jpashop2.service.MemberService;
import com.example.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/payment")
    public String payment(){
        return "payment";
    }

    @GetMapping("/payments/status/all")
    public String paymentResult(){
        return "paymentResult";
    }




    @GetMapping("/member/order")
    public String orderList(Model model, HttpSession httpSession){
        //Object temp = httpSession.getAttribute("loginId");//로그인 Id
        //Long memberId = Long.valueOf(String.valueOf(temp));//Object -> Long 타입변환
        //log.info("memberId : " + memberId);
        //Member loginMember = memberService.findOne(memberId);//로그인 Member
        String loginEmail = (String) httpSession.getAttribute("loginEmail");
        log.info("로그인 세션 이메일 : " + loginEmail);

        if(loginEmail == null) {
            return "login";
        }

        Member loginMember = memberService.findByEmail(loginEmail);
        List<Order> myOrders = loginMember.getOrders();//Order 전부 가져오기
        //Iterable<Order> myOrderList = orderService.findMyOrders(memberId); //안됨

        List<MyOrdersDTO> myOrderDetail = new ArrayList<>();//리스트 틀

        for(int i = 0; i < myOrders.size(); i++){
            Order order = myOrders.get(i);
            Delivery delivery = order.getDelivery();
            List<OrderItem> orderItems = order.getOrderItems();

            String math = "";//null로 하지마세요
            for(int k = 0; k < orderItems.size(); k++) {
                int orderPrice = orderItems.get(k).getOrderPrice();//**
                int count = orderItems.get(k).getCount();
                System.out.println("가격 : " + orderPrice);
                math += "(" + orderPrice + "원x" + count +"개)" ;
            }
            System.out.println("math : " + math);
            MyOrdersDTO myOrder = new MyOrdersDTO(order, delivery, orderItems, math);
            myOrderDetail.add(myOrder);
        }

        model.addAttribute("myOrderDetail", myOrderDetail);
        return "orders/orderList";
    }

    @GetMapping("/member/orderShow/{orderId}")
    public String orderShow(@PathVariable Long orderId, Model model){
        Order order = orderService.findOne(orderId);
        Delivery delivery = order.getDelivery();
        List<OrderItem> orderItems = order.getOrderItems();

        String math = "";//null로 하지마세요
        for(int k = 0; k < orderItems.size(); k++) {
            int orderPrice = orderItems.get(k).getOrderPrice();//**
            int count = orderItems.get(k).getCount();
            System.out.println("가격 : " + orderPrice);
            math += "(" + orderPrice + "원x" + count +"개)" ;
        }

        MyOrdersDTO myOrder = new MyOrdersDTO(order, delivery, orderItems, math);
        model.addAttribute("myOrder", myOrder);
        return "orders/orderShow";
    }


    @GetMapping("/admin/order")
    public String adminOrderList(Model model){
        List<Order> orders = orderService.findOrders();
        List<MyOrdersDTO> everyOrderDetail = new ArrayList<>();//리스트 틀 (워딩은 MyOrdersDTO지만, 모든 회원의 오더)

        for(int i = 0; i < orders.size(); i++){
            Order order = orders.get(i);

            //어드민용
            Long memberId = order.getMember().getId();
            String email = order.getMember().getEmail();
            String member_name = order.getMember().getName();
            Member member = new Member();
            member.setId(memberId);
            member.setEmail(email);
            member.setName(member_name);

            Delivery delivery = order.getDelivery();
            List<OrderItem> orderItems = order.getOrderItems();

            String math = "";
            for(int k = 0; k < orderItems.size(); k++) {
                int orderPrice = orderItems.get(k).getOrderPrice();
                int count = orderItems.get(k).getCount();
                System.out.println("가격 : " + orderPrice);
                math += "(" + orderPrice + "원x" + count +"개)" ;
            }
            System.out.println("math : " + math);
            MyOrdersDTO everyOrder = new MyOrdersDTO(order, member, delivery, orderItems, math);
            everyOrderDetail.add(everyOrder);
        }

        model.addAttribute("everyOrderDetail", everyOrderDetail);
        return "orders/adminOrderList";
    }


    @GetMapping("/admin/orderShow/{orderId}")
    public String adminOrderShow(@PathVariable Long orderId, Model model){
        Order order = orderService.findOne(orderId);
        //어드민용
        Long memberId = order.getMember().getId();
        String email = order.getMember().getEmail();
        String member_name = order.getMember().getName();
        Member member = new Member();
        member.setId(memberId);
        member.setEmail(email);
        member.setName(member_name);

        Delivery delivery = order.getDelivery();
        List<OrderItem> orderItems = order.getOrderItems();

        String math = "";
        for(int k = 0; k < orderItems.size(); k++) {
            int orderPrice = orderItems.get(k).getOrderPrice();//**
            int count = orderItems.get(k).getCount();
            System.out.println("가격 : " + orderPrice);
            math += "(" + orderPrice + "원x" + count +"개)" ;
        }

        MyOrdersDTO oneOrder = new MyOrdersDTO(order, member, delivery, orderItems, math);
        model.addAttribute("oneOrder", oneOrder);
        return "orders/adminOrderShow";
    }


    //검색 결과 보여줌
    @PostMapping("/admin/orderSearch")
    public String orderSearch(OrderSearch orderSearch, Model model){
        log.info("검색타입 : " + orderSearch.getSearchType());
        log.info("검색키워드 : " + orderSearch.getSearchKeyword());

        List<Order> orders = orderService.findOrdersBySearch(orderSearch);

        List<MyOrdersDTO> searchOrderDetail = new ArrayList<>();//리스트 틀
        for(int i = 0; i < orders.size(); i++){
            Order order = orders.get(i);

            //어드민용
            Long memberId = order.getMember().getId();
            String email = order.getMember().getEmail();
            String member_name = order.getMember().getName();
            Member member = new Member();
            member.setId(memberId);
            member.setEmail(email);
            member.setName(member_name);

            Delivery delivery = order.getDelivery();
            List<OrderItem> orderItems = order.getOrderItems();

            String math = "";
            for(int k = 0; k < orderItems.size(); k++) {
                int orderPrice = orderItems.get(k).getOrderPrice();
                int count = orderItems.get(k).getCount();
                System.out.println("가격 : " + orderPrice);
                math += "(" + orderPrice + "원x" + count +"개)" ;
            }
            System.out.println("math : " + math);
            MyOrdersDTO everyOrder = new MyOrdersDTO(order, member, delivery, orderItems, math);
            searchOrderDetail.add(everyOrder);
        }

        model.addAttribute("searchOrderDetail", searchOrderDetail);
        return "orders/adminOrderSearch";
    }


}
