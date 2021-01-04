package com.example.jpashop2.repository;

import com.example.jpashop2.domain.Category;
import com.example.jpashop2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final EntityManager em;

    public Long save(Category category) {
        em.persist(category);
        return category.getId();
    }

}

