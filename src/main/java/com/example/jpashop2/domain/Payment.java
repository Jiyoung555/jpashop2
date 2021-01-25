package com.example.jpashop2.domain;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

//SELECT *
//FROM ORDERS AS O /*ORDERS가 FK 가짐*/
//JOIN PAYMENT AS P
//ON O.payment_id = P.payment_id;

//Order, Member, Payment 테이블 3개 조인
//SELECT * /*O.*, M.name, P.amount*/
//FROM ORDERS AS O
//LEFT OUTER JOIN MEMBER AS M ON O.member_id = M.member_id
//LEFT OUTER JOIN PAYMENT AS P ON O.payment_id = P.payment_id;
@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    private String imp_uid;//고유ID
    private String merchant_uid;//상점 거래 ID
    private Long apply_num;//카드 승인번호
    private int amount;
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private Order order;
}
