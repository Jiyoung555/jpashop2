package com.example.jpashop2.domain;

public enum OrderStatus {
    IN_CART, //0
    OUT_CART,//1 (**내가 추가)
    ORDERED, //2
    CANCELED //3
    //String으로 DB 저장 안하면, 숫자로 저장됨
}
