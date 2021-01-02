package com.example.jpashop2.domain;
//import com.example.jpashop2.Exception.NotEnoughStockException;
import com.example.jpashop2.Exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //이 테이블은 상속관계를 가질 겁니다
@DiscriminatorColumn(name = "dtype")
//DB에 Item테이블이 하나 생김 //거기에 자식들의 컬럼이 다 들어가있음
@Getter
@Setter
public abstract class Item {//추상 클래스 //자식 클래스 - Album, Book, Movie
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuuantity;

    private String imagename;

    // 이미지 업로드 후, 파일명 저장
    public void uploadImage(MultipartFile files, String uploadPath2) throws IOException {
        UUID uuid = UUID.randomUUID(); // 랜덤 난수 생성
        String filename = uuid.toString() + "_" + files.getOriginalFilename(); //업로드 될 파일명
        String filePath = uploadPath2 + File.separator + filename; //절대경로
        File file = new File(filePath); //File 객체 생성
        files.transferTo(file); //절대경로에 집어넣기 (transferTo 기본 메소드는)
        this.imagename = filename;//업로드한 파일명으로 → this에 저장

    }

    //** stock 재고 증가 메소드
    public void addStock(int quantity) {
        this.stockQuuantity += quantity;
    }

    //** stock 재고 감소 메소드
    public void removeStock(int quantity) {
        int restStock = this.stockQuuantity - quantity;

        if (restStock < 0) { //재고가 0보다 작으면
            throw new NotEnoughStockException("재고가 부족합니다.");//에러 발생
            //NotEnoughStockException -> 내가 직접 만들기
       }

        this.stockQuuantity = restStock;
    }

}
