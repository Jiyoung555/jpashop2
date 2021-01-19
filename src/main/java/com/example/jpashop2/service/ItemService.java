package com.example.jpashop2.service;
import com.example.jpashop2.domain.*;
import com.example.jpashop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //Item 생성 (저장)
    @Transactional//읽기 전용 아님
    public Long saveItem(Item item) {
        itemRepository.save(item); //save 메소드를 리턴값 없는 void로 만들어놨음
        return item.getId();
    }

    //Item 전체 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //Item 하나 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
