package com.example.jpashop2.Exception;


public class NotEnoughStockException extends RuntimeException {
    //RunTimeException을 상속받고, 메소드 오버라이딩만 해줌
    //우클릭 - generate 해서, 오버라이딩 할 메소드 받을 수 있음

    public NotEnoughStockException() {
        super();
    }
    public NotEnoughStockException(String message) {
        super(message);
    }
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}