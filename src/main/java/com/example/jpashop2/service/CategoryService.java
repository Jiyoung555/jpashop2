package com.example.jpashop2.service;


import com.example.jpashop2.domain.Category;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.repository.CategoryRepository;
import com.example.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
