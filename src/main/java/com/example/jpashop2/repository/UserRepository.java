package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//Security 연습중..

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    //findByName이라고 해야됨 (findByUserName 이런 식으로 안 됨)
    //Member엔티티의 name 필드를 통해 찾아오는 거임**

    @Transactional(readOnly = true) //MyLoginSuccessHandler에서 리파지터리 사용할 때, 영속성 에러나서 트랜잭션 추가함
    //jpa lazy 바인딩 하려면, 트랜잭션 안에서 처리해야 원칙임.
    Optional<Member> findByEmail(String email); //테스트..


}

