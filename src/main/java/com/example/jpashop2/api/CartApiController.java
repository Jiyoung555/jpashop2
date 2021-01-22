package com.example.jpashop2.api;
import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.CartForm;
import com.example.jpashop2.dto.OrderForm;
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
public class CartApiController {
    private final CartService cartService;
    private final MemberService memberService;

    //시큐리티로 인해 로그인 이메일 제출
    //카트 담기 (**내가) //storeShow에서 카트 담기 - 1개의 아이템
    @PostMapping("/api/cart")
    public String insertCart(@RequestBody CartForm form, HttpSession httpSession){



        //Long memberId = form.getMemberId();
        //String memberEmail = form.getMemberEmail();//시큐리티로 인해 로그인 이메일 제출
        String memberEmail = (String) httpSession.getAttribute("loginEmail");
        log.info("로그인 이메일 세션 : " + memberEmail);

        if(memberEmail == null) {
            //return "redirect:/login";
            return "LOGIN";
        }


        log.info("form : " + form.toString());
        Long itemId = form.getItemId();
        int cartCount = form.getCartCount();

        //Member loginMember = memberService.findOne(memberId);//로그인 Member
        Member loginMember = memberService.findByEmail(memberEmail);
        List<Cart> myCarts = loginMember.getCarts();//회원의 기존 카트 모두 가져오기
        Long memberId = loginMember.getId();


        if(myCarts.size() != 0){//[1]기존 카트에 물건 있으면
            List<CartItem> cartItems = new ArrayList<>();//기존 카트 아이템 리스트 준비 //null로 하지마

            for(int i = 0; i < myCarts.size(); i++){
                Cart cart = myCarts.get(i);//각 기존 카트당

                if(cart.getStatus() == OrderStatus.IN_CART){//카트인 상태일 때만 (OUT, ORDERED 말고)
                    cartItems = cart.getCartItems();//기존 카트 아이템 가져와서, 리스트에 담기
                    System.out.println("IN_CART 상태 개수 : " + cartItems.size());
                }

            }

            if(cartItems.size() != 0) { //[1]-1. 카트에 IN_CART인 것들 있으면

                for(int k = 0; k < cartItems.size(); k++){
                    CartItem cartItem = cartItems.get(k); //기존 카트 아이템 리스트에서, 기존 아이템 하나씩 꺼냄

                    Long myItemId= cartItem.getItem().getId();

                    if(myItemId == itemId){ //1. 이미 기존 카트에 존재 <- 현재 새롭게 카트에 담으려는 아이템이
                        int result = cartService.updateCart(cartItem.getId(), cartCount);//카트update -> 수량만 변경

                        if(result == 1) {
                            return "UPDATED";
                        } else {
                            return "FAILED";

                        }
                        //DB에서 확인해보세요
                /*
                SELECT *
                        FROM CART_ITEM AS CI
                JOIN CART AS C
                ON CI.cart_id = C.cart_id;
                */

                    } else {//2.처음 담는 아이템이면
                        cartService.cart(memberId, itemId, cartCount);//카트 담기
                        return "CARTED";
                    }
                }

            } else { //[1]-2. 카트에 있긴 있는데, IN_CART 아닌 것들만 있으면 -> 카트 빈 거랑 마찬가지
                cartService.cart(memberId, itemId, cartCount);//카트 담기
                return "CARTED";
            }


        } else {//[2]기존 카트에 물건 없으면
            cartService.cart(memberId, itemId, cartCount);//카트 담기
            return "CARTED";
        }

        return "FAILED";
        //return cartService.cart(memberId, itemId, cartCount);


    }

    @DeleteMapping("/api/cart/{cartId}")
    public Long destroyCart(@PathVariable Long cartId) {
        cartService.cancelCart(cartId);
        return cartId;
    }

}
