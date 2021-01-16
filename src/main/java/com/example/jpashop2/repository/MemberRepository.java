package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor //3.final 필드의 생성자를 만들며, 자동으로 그 객체 얻어옴
public class MemberRepository {//extends 안하는 방법으로 해봅시다 //인터페이스 말고 클래스

    //@PersistenceContext //1.EntityManager 얻어오기 -> 2.@Autowired 쓰면, @PersistenceContext 안 써도 자동으로 얻어옴
    //@Autowired //2.자동 주입 -> 3.@RequiredArgsConstructor 쓰면, final 필드의 생성자를 만들면서, 자동으로 그 객체 얻어옴
    private final EntityManager em;//3.final 필드로 셋팅 //EntityManager: DAO처럼 DB 접근

    //멤버 생성 & 수정
    public Long save(Member member) {
        em.persist(member); //EntityManager 통해서 . DB에 반영하겠다 (member 엔티티를)
        //persist 메소드 역할
        //1.생성: 기존 id값 없으면 (null) -> DB 들어가는 순간 생성
        //2.수정: 기존 id값 있으면 -> 이를 수정
        return member.getId();
    }

    //멤버 1명 조회
    public Member findOne(Long id) {//id 값으로 조히
        return em.find(Member.class, id);
        //EntityManager의 find 메소드 사용 //파라미터 두개(엔티티 클래스 형태로 받기, PK를 통해 꺼냄)
    }


    //멤버 모두 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        //EntityManager의 createQuery 메소드 사용 =>괄호 안에, 쿼리문 쓸 수 있음
        //쿼리문: Member 테이블을 m이라 부를 것이며, 그 m에서 다 꺼내와라 (=>SQL문과 비슷한 JPQL문임)
        //반환값은 Member 타입으로 받음
        //리스트 결과값

        //이전 방법: 리파지터리 interface -> findById, findAll 메소드 사용
    }

    //이름을 통해 Member 가져오기
    public List<Member> findByNames(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name) // :name에 삽입될 값 설정 (SQL문의 WHERE 기능)
                .getResultList(); //리스트 결과값 반환
    }

    //이메일 통해 Member 가져오기
    public List<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email) //SQL문의 WHERE 기능
                .getResultList(); //리스트 결과값 반환
    }

}
