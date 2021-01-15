package com.example.jpashop2.api;

import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.CartForm;
import com.example.jpashop2.dto.OrderForm;
import com.example.jpashop2.dto.OrderSearch;
import com.example.jpashop2.service.CartService;
import com.example.jpashop2.service.MemberService;
import com.example.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderService orderService;
    private final CartService cartService;
    private final MemberService memberService;

    @PostMapping("/api/order")
    public Long insertOrder(@RequestBody OrderForm form){
        log.info("form : " + form.toString());

        List<Long> itemIdArr = new ArrayList<>();//**
        List<Integer> countArr = new ArrayList<>();//**

        Long memberId = form.getMemberId();
        Long itemId = form.getItemId();
        int count = form.getCount();

        itemIdArr.add(itemId);
        countArr.add(count);

        return orderService.order(memberId, itemIdArr, countArr);//**여러 item 선택할 경우로 수정함
    }

    @DeleteMapping("/api/order/{orderId}")
    public Long destroyOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return orderId;
    }


    //store에서 체크박스 구매 (개수는 아이템당 1개)
    @PostMapping("/api/checkedStoreToOrder")
    public Long checkedStoreToOrder(@RequestBody OrderForm form, HttpSession httpSession){
        log.info("form : " + form);
        List<Long> itemIdArr = form.getItemIdArr();//store 여러개 -> order

        Object temp = httpSession.getAttribute("loginId");//로그인 Id
        Long memberId = Long.valueOf(String.valueOf(temp));//Object -> Long 타입변환
        log.info("memberId : " + memberId);
        Member loginMember = memberService.findOne(memberId);//로그인 Member

        List<Integer> countArr = new ArrayList<>();
        for (int i = 0; i < itemIdArr.size(); i++) {
            countArr.add(1);
        }

        return orderService.order(memberId, itemIdArr, countArr);

    }

    //cartList에서 체크한 여러 개 주문하기
    @PostMapping("/api/checkedCartToOrder")
    public Long checkedCartToOrder(@RequestBody OrderForm form) {
        log.info("form : " + form);
        List<Long> cartIdArr = form.getCartIdArr();//cart 여러개 -> order
        Long memberId = null;
        List<Long> itemIdArr = new ArrayList<>();//**
        List<Integer> countArr = new ArrayList<>();//**

        for (int i = 0; i < cartIdArr.size(); i++) {
            Long cartId = cartIdArr.get(i);
            Cart cart = cartService.findOne(cartId); //cart 1개
            memberId = cart.getMember().getId();

            List<CartItem> cartItems = cart.getCartItems();//cart 1개당, item들

            for (int k = 0; k < cartItems.size(); k++) {
                CartItem cartItem = cartItems.get(k);

                Long itemId = cartItem.getItem().getId();
                int count = cartItem.getCartCount();

                itemIdArr.add(itemId);//**
                countArr.add(count);//**
            }
        }
        return orderService.cartArrToOrder(cartIdArr, memberId, itemIdArr, countArr);
        //return "success";
        //orderService.cartToOrder(cartId, memberId, itemId, count);
        //이건 cart당 상품 1개인 경우라서...

        //return "fail";//실패
    }

    //아이템 1개 주문하는 경우 (cartShow / cartList에서 개당 구매)
    @PostMapping("/api/cartToOrder")
    public String cartToOrder(@RequestBody CartForm form){
        log.info("form : " + form.toString());
        Long cartId = form.getCartId();
        Cart cart = cartService.findOne(cartId);
        Long memberId = cart.getMember().getId();//**

        List<CartItem> cartItems = cart.getCartItems();
        for(int i = 0; i < cartItems.size(); i++){
            CartItem cartItem = cartItems.get(i);
            Long itemId = cartItem.getItem().getId();//**
            int count = cartItem.getCartCount();//**

            orderService.cartToOrder(cartId, memberId, itemId, count);//이건 상품 1개인 경우
            return "success";
        }

        return "fail";//실패
    }


//    @PostMapping("/api/orderSearch")
//    public void orderSearch(@RequestBody OrderSearch orderSearch){
//        log.info("검색값 : " + orderSearch.getSearchType(), orderSearch.getSearchKeyword());
//        List<Order> orders = orderService.findOrdersBySearch(orderSearch);
//        log.info("결과값 : " + orders);
//    }




}

