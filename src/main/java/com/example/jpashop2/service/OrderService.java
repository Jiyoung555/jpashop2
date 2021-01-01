package com.example.jpashop2.service;

import com.example.jpashop2.domain.*;
import com.example.jpashop2.repository.ItemRepository;
import com.example.jpashop2.repository.MemberRepository;
import com.example.jpashop2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //Order 주문하기
    @Transactional
    public Long order(Long memberId, Long itemId, int count) { //파라미터: 누가, 뭐를, 얼마나
        //Member, Item 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //Delivery 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress((member.getAddress())); //회원 주소로 배송하기

        //OrderItem 주문상품 "먼저" 생성(Order 주문을 위해, Item 상품 먼저 셋팅)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //Order 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //Order 주문 저장
        return orderRepository.save(order);
    }

    //Order 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //Order 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //Order 취소
        order.cancel();

        /*생략 가능
        -트랜잭셔널 처리했기 때문에, JPA가 알아서 변경을 감지하고 반영함
        -구글링 : jpa Dirty Checking
        */
        // orderRepository.save(order); //해도 되고, 안 해도 됨
        }


    /*
    //[참고] Order 주문 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
    */

}
