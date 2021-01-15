package com.example.jpashop2.service;
import com.example.jpashop2.domain.*;
import com.example.jpashop2.dto.OrderSearch;
import com.example.jpashop2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final OrderRepositoryTest test;

    //Order 주문하기
    @Transactional
    public Long order(Long memberId, List<Long> itemIdArr, List<Integer> countArr) {
        Member member = memberRepository.findOne(memberId);
        List<Item> itemArr = new ArrayList<>();//아이템들
        List<OrderItem> orderItemArr = new ArrayList<>();//OrderItem 여러개
//        //**항상 item 1개만 주문하는 경우의 코드
//        //OrderItem 주문상품 "먼저" 생성(Order 주문을 위해, Item 상품 먼저 셋팅
//        Item item = itemRepository.findOne(itemId);
//        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
//        orderItemArr.add(orderItem);

        for(int i = 0; i <itemIdArr.size(); i++){
            Item item = itemRepository.findOne(itemIdArr.get(i));
            itemArr.add(item);

            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), countArr.get(i));
            orderItemArr.add(orderItem);
        }

        //Delivery 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress((member.getAddress())); //회원 주소로 배송하기
        delivery.setStatus(DeliveryStatus.READY); //**내가 추가함

        //Order 주문 생성
        Order order = Order.createOrder(member, delivery, orderItemArr);

        //Order 주문 저장
        return orderRepository.save(order);
    }


    //아이템 1개만 구매하는 경우
    @Transactional
    public Long cartToOrder(Long cartId, Long memberId, Long itemId, int count) {
        //Member, Item 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        List<Item> itemArr = new ArrayList<>();//아이템들
        List<OrderItem> orderItemArr = new ArrayList<>();//OrderItem 여러개

        //Delivery 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress((member.getAddress())); //회원 주소로 배송하기
        delivery.setStatus(DeliveryStatus.READY); //**내가 추가함


        //OrderItem 주문상품 "먼저" 생성(Order 주문을 위해, Item 상품 먼저 셋팅)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        orderItemArr.add(orderItem); //**이 경우, 1개라서...

        //Order 주문 생성
        Order order = Order.createOrder(member, delivery, orderItemArr);

        //**Cart 비우기
        Cart cart = cartRepository.findOne(cartId);
        cart.setStatus(OrderStatus.ORDERED);

        //Order 주문 저장
        return orderRepository.save(order);
    }


    //카트 여러개 -> Order (cartList.mustache)
    @Transactional
    public Long cartArrToOrder(List<Long> cartIdArr, Long memberId,
                               List<Long> itemIdArr, List<Integer> countArr) {
        //Member, Item 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        List<Item> itemArr = new ArrayList<>();//아이템들
        List<OrderItem> orderItemArr = new ArrayList<>();//OrderItem 여러개

        for(int i = 0; i <itemIdArr.size(); i++){
            Item item = itemRepository.findOne(itemIdArr.get(i));
            itemArr.add(item);

            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), countArr.get(i));
            orderItemArr.add(orderItem);

            Cart cart = cartRepository.findOne(cartIdArr.get(i));//Cart 비우기
            cart.setStatus(OrderStatus.ORDERED);
        }

        //Delivery 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress((member.getAddress())); //회원 주소로 배송하기
        delivery.setStatus(DeliveryStatus.READY); //**내가 추가함

        //Order 주문 생성
        Order order = Order.createOrder(member, delivery, orderItemArr);

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

        /*생략 가능 (해도 되고, 안 해도 됨)
        -트랜잭셔널 처리했기 때문에, JPA가 알아서 변경을 감지하고 반영함
        -구글링 : jpa Dirty Checking
        */
        //orderRepository.save(order);
    }


//        //사용x
//        //@Transactional(readOnly = true)//위로 뺌
//        public List<Order> findMyOrders(Long memberId){
//            List<Order> myOrderList = orderRepository.findByMemberId(memberId);
//            return myOrderList;
//        }


    //@Transactional(readOnly = true)//위로 뺌
    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }


    //오더 전체 조회(어드민)
    //@Transactional(readOnly = true)//위로 뺌
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    //[참고] Order 주문 검색
    public List<Order> findOrdersBySearch(OrderSearch orderSearch) {//메소드명 findOrders에서 바꿈
        return orderRepository.findAllBySearch(orderSearch);
    }


}
