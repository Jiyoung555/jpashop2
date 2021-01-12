package com.example.jpashop2.dto;
import com.example.jpashop2.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {//검색용 DTO
    String searchType;
    String searchKeyword;
}
