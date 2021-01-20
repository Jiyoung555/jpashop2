package com.example.jpashop2.repository;
import com.example.jpashop2.domain.Talk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TalkRepository extends CrudRepository<Talk, Long> {
    @Override
    Optional<Talk> findById(Long aLong);

    @Override
    Iterable<Talk> findAll();
}
