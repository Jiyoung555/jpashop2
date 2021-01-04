package com.example.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    //**내가 함
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        this.items.add(item);
    }

//    @ManyToMany//N:N는 실무 사용 x 참고만
//    @JoinTable(name = "category_item", //N:N 이어도, 중간 테이블이 자동으로 생성됨. 그 중간 테이블 이름 설정해줌
//            joinColumns = @JoinColumn(name = "category_id"),//중간 테이블이 조인되기 위한 FK를 설정
//                                                            //외부의 중간 테이블을 붙인다. 중간 테이블의 PK를 통해
//                                                            //그 PK가 여기서는 FK가 되고, 그 FK명을 지금 정함
//            inverseJoinColumns = @JoinColumn(name = "item_id")) //중간 테이블이 조인되기 위한 FK를 설정
//                                                                //외부 테이블 붙임. 외부 테이블의 PK 통해 붙이고
//                                                                //이는 여기서 FK가 됨
//    private List<Item> items = new ArrayList<>();




    //===이 Category 클래스는, 부모도 가지고, 자식도 가짐===

    //이 카테고리의, 상위 부모 카테고리
    @ManyToOne(fetch=FetchType.LAZY)//~ToOne이면 꼭 LAZY 추가
    @JoinColumn(name = "parent_id")
    private Category parent; //본 클래스를 붙인다(자신이 자신을 셀프 참조), 다른 변수명을 통해

    //이 카테고리의, 하위 자식 카테고리
    //관계의 주인 - Category의 "parent" (JoinColumn 가지니까)
    @OneToMany(mappedBy = "parent") //child로 바꾸는건, DB변경 안됨 (주인 아니라서)
    private List<Category> child = new ArrayList<>();


    //**양방향: 부모 카테고리에 + 자식 카테고리 추가
    //동시에, 자식 카테고리에도 + 부모를 연결
    public void addChildCategory(Category child) {
        this.child.add(child);//child 배열 필드에 추가
        child.setParent(this); //아래 setParent()를 통해, parent 필드값 변경
        //**parent, child 엔티티 전부, 이 클래스로 만들어짐 (내가 부모도 되고 자식도 되고..)
    }

    private void setParent(Category parent) {//**parent용 setter
       this.parent = parent;
    }
}