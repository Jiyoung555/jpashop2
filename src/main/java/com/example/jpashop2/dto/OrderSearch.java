package com.example.jpashop2.dto;
import com.example.jpashop2.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {//검색용 DTO
    String searchType;
    String searchKeyword;

    //테스트
    String orderStatus;
    String memberName;
    String memberEmail;


}
