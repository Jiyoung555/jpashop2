package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.domain.Order;
import com.example.jpashop2.domain.OrderItem;
import com.example.jpashop2.dto.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    //Order 생성 (및 수정)
    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    //Order 하나 조회
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId); //엔티티 클래스 형태로 받기, PK를 통해 꺼냄
    }


//    //**내가 추가 (사용x!!!)
//    //memberId값을(FK) 통해, 내 Order들 가져오기 //member_id가 없어서 안된다함. 나중에 해결
//    public List<Order> findByMemberId(Long memberId) {
//        return em.createQuery("select o.id, o.orderDate from Order o where o.member_id = :id", Order.class)
//                .setParameter("id", memberId)
//                .getResultList();
//
//    }

    //오더 모두 조회(어드민)
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
        //Order -> Orders 해야하나??
    }



    //**Search 기능 (검색어에 따라 검색)
    public List<Order> findAllBySearch(OrderSearch orderSearch) {
        String searchType = orderSearch.getSearchType();

        List<Order> orders = new ArrayList<>();

        if (searchType.equals("member_name")) {
            orders = em.createQuery("select o from Order o join o.member m" +
                    " where m.name like :name", Order.class)
                    .setParameter("name", orderSearch.getSearchKeyword())
                    .setFirstResult(0) // 페이징 시 사용
                    .setMaxResults(1000) // 최대 1000건
                    .getResultList();

        } else if (searchType.equals("orderStatus")) { //Q. 왜 안 되지??
            orders = em.createQuery("select o from Order o where o.status = :status",
                    Order.class)
                    .setParameter("status", orderSearch.getSearchKeyword())
//                    .setFirstResult(0) // 페이징 시 사용
//                    .setMaxResults(1000) // 최대 1000건
                    .getResultList();
        }

        return orders;


//        //TO DO: 추후 QueryDSL로 변경!
//        return em.createQuery("select o from Order o join o.member m" +
//                " where o.status = :status " +
//                " and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult(0) // 페이징 시 사용
//                .setMaxResults(1000) // 최대 1000건
//                .getResultList();

    }



}
