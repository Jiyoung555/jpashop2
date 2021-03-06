package com.example.jpashop2.controller;

import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.MyCartsDTO;
import com.example.jpashop2.dto.MyOrdersDTO;
import com.example.jpashop2.service.CartService;
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
public class CartController {
    private final CartService cartService;
    private final MemberService memberService;

    @GetMapping("/member/cart")
    public String cartList(Model model, HttpSession httpSession){
        //Security 설정후 -> 로그인 세션은 loginEmail만 등록됨

        //**Security 등록으로 인해, 이렇게 수정함
        String loginEmail = (String) httpSession.getAttribute("loginEmail");//로그인 email
        log.info("로그인 이메일 세션 : " + loginEmail);

        if(loginEmail == null) {
            return "login";
        }

        Member loginMember = memberService.findByEmail(loginEmail);
        List<Cart> myCarts = loginMember.getCarts();//회원 Cart 전부 가져오기
        List<MyCartsDTO> myCartDetail = new ArrayList<>(); //리스트 틀

        if(myCarts.size() != 0) { //1. 카트에 물건 있으면
            for(int i = 0; i < myCarts.size(); i++){
                Cart cart = myCarts.get(i);//회원 Cart들 중, 한 카트
                if(cart.getStatus() == OrderStatus.IN_CART) {//카트인 상태인 것만
                    List<CartItem> cartItems = cart.getCartItems();//그 한 카트의, 모든 카트 아이템들
                    MyCartsDTO myCart = new MyCartsDTO(cart, cartItems);
                    myCartDetail.add(myCart);
                }
            }

            model.addAttribute("myCartDetail", myCartDetail);
            return "carts/cartList";


        } else { //2.카트 비었으면
            return "carts/cartList";
        }


    }


    @GetMapping("/member/cartShow/{cartId}")
    public String cartShow(@PathVariable Long cartId, Model model){
        Cart cart = cartService.findOne(cartId);
        List<CartItem> cartItems = cart.getCartItems();
        MyCartsDTO myCart = new MyCartsDTO(cart, cartItems);
        model.addAttribute("myCart", myCart);
        return "carts/cartShow";
    }


}
