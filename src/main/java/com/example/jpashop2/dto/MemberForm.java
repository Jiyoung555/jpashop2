package com.example.jpashop2.dto;

import com.example.jpashop2.domain.Address;
import com.example.jpashop2.domain.Member;
import lombok.*;

//@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {
    String email;
    String password;
    String name;
    String role;
    String zipcode;
    String addr1;
    String addr2;

    public Member toMember() {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setRole(role);
        return member;
    }

    public Address toAddress() {
        Address address = new Address();
        address.setZipcode(zipcode);
        address.setAddr1(addr1);
        address.setAddr2(addr2);
        return address;
    }

    /* //builder로 하니, Address 부분을 엔티티화 할 줄 모르겠음 //builder없이 위 코드로 함
    public Member toEntity() {
        return Member.builder()//엔티티 클래스에 @Builder 있어야함
                .id(null)
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .zipcode(zipcode)
                .addr1(addr1)
                .addr2(addr2)
                .build();
    }
    */

}