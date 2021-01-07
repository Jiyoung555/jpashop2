package com.example.jpashop2.dto;
import com.example.jpashop2.domain.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyCartsDTO {
    Long cartId;
    LocalDateTime cartDate;
    OrderStatus cartStatus;

    Long cartItemId;
    int cartPrice;
    int cartCount;
    int totalPrice;
    String name;//item
    String imageName;

    public MyCartsDTO(Cart cart, List<CartItem> cartItems) {
        this.cartId = cart.getId();
        this.cartDate = cart.getCartDate();
        this.cartStatus = cart.getStatus();

        for(int i = 0; i < cartItems.size(); i++){
            CartItem cartItem = cartItems.get(i);
            this.cartItemId = cartItem.getId();//지금은 Cart당 cartItem 1개라서... 아닌데??
            this.cartPrice = cartItem.getCartPrice();
            this.cartCount = cartItem.getCartCount();
            this.totalPrice = cartItem.getTotalPrice();
            this.name = cartItem.getItem().getName();//item
            this.imageName = cartItem.getItem().getImagename();
        }
    }





}



