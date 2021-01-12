package com.example.jpashop2.domain;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//@ManyToOne과 @JoinColumn은 한 셋트!
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        member.getCarts().add(this);
    }
    //========================
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);//양방향
    }
    //==========================

    private LocalDateTime cartDate;

    @Enumerated(EnumType.STRING)//추가후 DB drop 또 해야됨
    private OrderStatus status;

    //==================

    //**생성 메소드
    public static Cart createCart(Member member, CartItem... cartItems) {
        Cart cart = new Cart();
        cart.setMember(member);

        for (CartItem cartItem : cartItems) {
            cart.addCartItem(cartItem);
        }

        cart.setStatus(OrderStatus.IN_CART);
        cart.setCartDate(LocalDateTime.now());

        return cart;
    }

    public void cancel() {
//        if(status == OrderStatus.ORDERED) {
//            throw new IllegalStateException("결제된 상품은 카트 취소할 수 없습니다.");
//        }

        this.setStatus(OrderStatus.OUT_CART);

//        for (CartItem cartItem : cartItems) {
//            cartItem.cancel();
//        }

    }

    public void ordered() {
        this.setStatus(OrderStatus.ORDERED);
    }


    public int getTotalPrice() {
        int totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getTotalPrice(); //아이템의 각 가격을 더하기
        }

        return totalPrice;
    }


}