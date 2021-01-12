package com.example.jpashop2.dto;
import com.example.jpashop2.domain.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyOrdersDTO {
    Long orderId;
    LocalDateTime orderDate;
    OrderStatus orderStatus;//@Enumerated(EnumType.STRING)

    Long memberId;//member정보 (adminOrderList 조회시에만 사용)
    String email;
    String member_name;

    Long deliveryId;
    DeliveryStatus deliveryStatus;
    String zipcode;//address
    String addr1;
    String addr2;

//    Long orderItemId;
//    int orderPrice;
//    int count;//orderCount로 고쳐야하나??
//    int totalPrice;
//    String name;//item
//    String imageName;

    List<Long> orderItemIdArr = new ArrayList<>();//그냥 =null로 해두면, 에러남(NullPointerException)
    List<Integer> orderPriceArr = new ArrayList<>();
    List<Integer> countArr = new ArrayList<>();//orderCount로 고쳐야하나??
    int totalPrice;
    List<String> nameArr = new ArrayList<>();//item
    List<String> imageNameArr = new ArrayList<>();

    String math = ""; //여러 아이템의 가격 x 수량

    //생성자1
    public MyOrdersDTO(Order order, Delivery delivery, List<OrderItem> orderItems, String math) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();

        this.deliveryId = delivery.getId();
        this.deliveryStatus = delivery.getStatus();
        this.zipcode = delivery.getAddress().getZipcode();
        this.addr1 = delivery.getAddress().getAddr1();
        this.addr2 = delivery.getAddress().getAddr2();

        for(int i = 0; i < orderItems.size(); i++){
            OrderItem orderItem = orderItems.get(i);
            this.orderItemIdArr.add(orderItem.getId());
            this.orderPriceArr.add(orderItem.getOrderPrice());
            this.countArr.add(orderItem.getCount());

            this.totalPrice += orderItem.getTotalPrice();

            this.nameArr.add(orderItem.getItem().getName());//item
            this.imageNameArr.add(orderItem.getItem().getImagename());
        }

        this.math = math;

//        for(int i = 0; i < orderItems.size(); i++){
//            OrderItem orderItem = orderItems.get(i);
//            this.orderItemId = orderItem.getId();//지금은 Order당 OrderItem 1개라서...
//            this.orderPrice = orderItem.getOrderPrice();
//            this.count = orderItem.getCount();
//            this.totalPrice = orderItem.getTotalPrice();
//            this.name = orderItem.getItem().getName();//item
//            this.imageName = orderItem.getItem().getImagename();
//        }
    }


    //생성자2_어드민용
    public MyOrdersDTO(Order order, Member member, Delivery delivery, List<OrderItem> orderItems, String math) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();

        this.memberId = member.getId();
        this.email = member.getEmail();
        this.member_name = member.getName();

        this.deliveryId = delivery.getId();
        this.deliveryStatus = delivery.getStatus();
        this.zipcode = delivery.getAddress().getZipcode();
        this.addr1 = delivery.getAddress().getAddr1();
        this.addr2 = delivery.getAddress().getAddr2();

        for(int i = 0; i < orderItems.size(); i++){
            OrderItem orderItem = orderItems.get(i);
            this.orderItemIdArr.add(orderItem.getId());
            this.orderPriceArr.add(orderItem.getOrderPrice());
            this.countArr.add(orderItem.getCount());

            this.totalPrice += orderItem.getTotalPrice();

            this.nameArr.add(orderItem.getItem().getName());//item
            this.imageNameArr.add(orderItem.getItem().getImagename());
        }

        this.math = math;
    }


}
