package com.example.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//**내가 만듦
@Entity
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int cartPrice;
    private int cartCount;

    public static CartItem createCartItem(Item item, int cartPrice, int cartCount) {
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setCartPrice(cartPrice);
        cartItem.setCartCount(cartCount);
        //item.removeStock(cartCount);
        return cartItem;
    }

//    public void cancel() {
//        getItem().addStock(cartCount);
//    }

    public int getTotalPrice() {
        return getCartPrice() * getCartCount();
    }

}
