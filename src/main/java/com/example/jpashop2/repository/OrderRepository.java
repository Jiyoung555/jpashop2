package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.domain.Order;
import com.example.jpashop2.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

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


    //**내가 추가 (사용x!!!)
    //memberId값을(FK) 통해, 내 Order들 가져오기 //member_id가 없어서 안된다함. 나중에 해결
    public List<Order> findByMemberId(Long memberId) {
        return em.createQuery("select o.id, o.orderDate from Order o where o.member_id = :id", Order.class)
                .setParameter("id", memberId)
                .getResultList();

    }





    //[참고] Search 기능 (검색어에 따라 검색)
    //public List<Order> findAll(OrderSearch orderSearch) {}
}
