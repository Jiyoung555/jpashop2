package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Cart;
import com.example.jpashop2.domain.CartItem;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final EntityManager em;

    public Long save(Cart cart) {
        em.persist(cart);
        return cart.getId();
    }

    public Cart findOne(Long cartId) {
        return em.find(Cart.class, cartId);
    }
    
    public CartItem findCartItem(Long cartItemId){
        return em.find(CartItem.class, cartItemId);
    }


    public int update(CartItem cartItem, int cartCount2) {

        String sql =
                "UPDATE CartItem ci SET ci.cartCount = ci.cartCount + :cartCount2 WHERE ci.id = :targetId";

        int resultCount = em.createQuery(sql)
                .setParameter("cartCount2", cartCount2)
                .setParameter("targetId", cartItem.getId())
                .executeUpdate();

        System.out.println("결과값 : " + resultCount);//성공시 1리턴
        return resultCount;

    }
}
