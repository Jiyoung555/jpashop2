package com.example.jpashop2.domain;

public enum OrderStatus {//String으로 DB 저장 안하면, 숫자로 저장됨
    IN_CART, //0
    OUT_CART,//1 (**내가 추가)
    ORDERED, //2 //나중에 PAID로 고치기..
    CANCELED, //3
    PAID //4

}
