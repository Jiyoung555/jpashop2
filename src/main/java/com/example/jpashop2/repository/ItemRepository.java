package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    //Item 생성 or (변화 있으면) 수정
    public void save(Item item) {
        em.persist(item);//이걸 추천
        /*
        updateForm 제출시

        1) persist로 통일하면
        [지영, 28살] 이었는데, [지영, 29살] 제출하면  -> [지영, 29살]로 수정됨
        [  , 29살] 제출해도 ->  [지영, 29살]로 수정됨

        2) merge 사용시

        근데, merge 사용시, [   , 29살] 제출하면 -> [  , 29살]로 변경됨..
        */


//        if (item.getId() == null) {//이미 본 item 없으면
//            em.persist(item); //persist: EntityManager 통해서, DB에 저장(item 엔티티를)
//        } else {//있으면
//            em.merge(item); //merge: 수정하겠다(바꾼 item 엔티티로)
//        }
    }

    //Item 하나 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
        //파라미터 두개(엔티티 클래스 형태로 받기, PK를 통해 꺼냄)
    }

    //Item 전체 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class) // JPQL쿼리문
                .getResultList();
    }

}
