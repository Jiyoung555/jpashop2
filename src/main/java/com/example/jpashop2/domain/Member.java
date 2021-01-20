package com.example.jpashop2.domain;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/*
update member
set role = 'ROLE_ADMIN'
where member_id = 1;
*/
@Entity
@Getter
@Setter//보통 entity는 누가 함부로 못 바꾸게, 세터 안 만들지만
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id") //테이블에서의 컬럼명
    private Long id;

    @Column//JPA UNIQUE COLUMN설정하기!!
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
    //여기는 주인이 아님. 따라서 FK는 주인 Order에 추가한 member에서 관리하겠다

    @OneToMany(mappedBy = "member")
    private List<Cart> carts = new ArrayList<>();

    //추가중..
    @OneToMany(mappedBy = "member") //cascade는??
    private List<Talk> talks = new ArrayList<>();



}
