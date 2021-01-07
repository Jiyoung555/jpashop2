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
public class MyOrdersDTO {
    Long orderId;
    LocalDateTime orderDate;
    OrderStatus orderStatus;//@Enumerated(EnumType.STRING)

    Long deliveryId;
    DeliveryStatus deliveryStatus;
    String zipcode;//address
    String addr1;
    String addr2;

    Long orderItemId;
    int orderPrice;
    int count;//orderCount로 고쳐야하나??
    int totalPrice;
    String name;//item
    String imageName;

    public MyOrdersDTO(Order order, Delivery delivery, List<OrderItem> orderItems) {
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
            this.orderItemId = orderItem.getId();//지금은 Order당 OrderItem 1개라서...
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
            this.totalPrice = orderItem.getTotalPrice();
            this.name = orderItem.getItem().getName();//item
            this.imageName = orderItem.getItem().getImagename();
        }
    }



}
