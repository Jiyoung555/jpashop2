//package com.example.jpashop2.domain;
//import lombok.Getter;
//import lombok.Setter;
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
////중간 테이블 **내가 만듦
//@Entity
//@Getter
//@Setter
//public class CategoryItem {
//    @Id
//    @GeneratedValue
//    @Column(name = "category_item_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id")
//    private Item item;
//
//    public static CategoryItem createCategoryItem(Item item, Category category) {
//        CategoryItem categoryItem = new CategoryItem();
//        categoryItem.setItem(item);
//        categoryItem.setCategory(category);
//        return categoryItem;
//    }
//
//
//
//}
