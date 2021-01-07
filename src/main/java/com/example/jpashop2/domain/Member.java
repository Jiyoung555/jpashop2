package com.example.jpashop2.domain;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id") //테이블에서의 컬럼명
    private Long id;

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String role;

    @Embedded
    private Address address;
    //Embdeded: 다른 곳에서 가져온, 컬럼의 묶음
    //Address 클래스의 내부 변수들이 합쳐져서 삽입됨 (city, address, zipcode)

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Cart> carts = new ArrayList<>();
}
