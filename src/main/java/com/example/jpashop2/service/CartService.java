package com.example.jpashop2.service;

import com.example.jpashop2.domain.*;
import com.example.jpashop2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public Long cart(Long memberId, Long itemId, int cartCount) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        CartItem cartItem = CartItem.createCartItem(item, item.getPrice(), cartCount);
        Cart cart = Cart.createCart(member, cartItem); //delivery는 x
        return cartRepository.save(cart);
    }

    @Transactional
    public int updateCart(Long cartItemId, int cartCount) {
        CartItem cartItem = cartRepository.findCartItem(cartItemId);
        return cartRepository.update(cartItem, cartCount);//성공시 1리턴
    }

    @Transactional
    public void cancelCart(Long cartId) {
        Cart cart = cartRepository.findOne(cartId);
        cart.cancel();
    }


    //@Transactional(readOnly = true)//위로 뺌
    public Cart findOne(Long cartId) {
        return cartRepository.findOne(cartId);
    }



}
