package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    //Order 생성 (Q.및 수정??)
    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    //Order 하나 조회
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId); //엔티티 클래스 형태로 받기, PK를 통해 꺼냄
    }

    //[참고] Search 기능 (검색어에 따라 검색)
    //public List<Order> findAll(OrderSearch orderSearch) {}
}
