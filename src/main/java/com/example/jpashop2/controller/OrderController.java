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
        //List<String> mathArr = new ArrayList<>();//**


        for(int i = 0; i < myOrders.size(); i++){
            Order order = myOrders.get(i);
            Delivery delivery = order.getDelivery();
            List<OrderItem> orderItems = order.getOrderItems();

            String math = "";//null로 하지마세요
            for(int k = 0; k < orderItems.size(); k++) {
                int orderPrice = orderItems.get(k).getOrderPrice();//**
                int count = orderItems.get(k).getCount();
                System.out.println("가격 : " + orderPrice);
                //String math = orderPrice + "원x" + count +"개";
                //mathArr.add(math);
                math += "(" + orderPrice + "원x" + count +"개)" ;
            }
            System.out.println("math : " + math);
            MyOrdersDTO myOrder = new MyOrdersDTO(order, delivery, orderItems, math);
            myOrderDetail.add(myOrder);
        }



        model.addAttribute("myOrderDetail", myOrderDetail);
        //model.addAttribute("math", math);
        return "orders/orderList";
    }

    @GetMapping("/orderShow/{orderId}")
    public String orderShow(@PathVariable Long orderId, Model model){
        Order order = orderService.findOne(orderId);
        Delivery delivery = order.getDelivery();
        List<OrderItem> orderItems = order.getOrderItems();

        String math = "";//null로 하지마세요
        for(int k = 0; k < orderItems.size(); k++) {
            int orderPrice = orderItems.get(k).getOrderPrice();//**
            int count = orderItems.get(k).getCount();
            System.out.println("가격 : " + orderPrice);
            //String math = orderPrice + "원x" + count +"개";
            //mathArr.add(math);
            math += "(" + orderPrice + "원x" + count +"개)" ;
        }

        MyOrdersDTO myOrder = new MyOrdersDTO(order, delivery, orderItems, math);//**math 아직 정의는 안 함..
        model.addAttribute("myOrder", myOrder);
        return "orders/orderShow";
    }



}
