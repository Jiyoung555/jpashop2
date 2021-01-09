package com.example.jpashop2.domain;
//import com.example.jpashop2.Exception.NotEnoughStockException;
import com.example.jpashop2.Exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private int stockQuantity;

    private String imagename;

    //======================

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
        this.stockQuantity += quantity;
    }

    //** stock 재고 감소 메소드
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) { //재고가 0보다 작으면
            throw new NotEnoughStockException("재고가 부족합니다.");//에러 발생
            //NotEnoughStockException -> 내가 직접 만들기
       }

        this.stockQuantity = restStock;
    }

    //**내가 넣음
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "category_item", //N:N 이어도, 중간 테이블이 자동으로 생성됨. 그 중간 테이블 이름 설정해줌
            joinColumns = @JoinColumn(name = "item_id"),//중간 테이블이 조인되기 위한 FK를 설정
            //외부의 중간 테이블을 붙인다. 중간 테이블의 PK를 통해
            //그 PK가 여기서는 FK가 되고, 그 FK명을 지금 정함
            inverseJoinColumns = @JoinColumn(name = "category_id")) //중간 테이블이 조인되기 위한 FK를 설정
    //외부 테이블 붙임. 외부 테이블의 PK 통해 붙이고
    //이는 여기서 FK가 됨
    private List<Category> categories = new ArrayList<>();

    //**내가 추가 //양방향
    public void addCategory(Category category) {
        this.categories.add(category);
    }




}
