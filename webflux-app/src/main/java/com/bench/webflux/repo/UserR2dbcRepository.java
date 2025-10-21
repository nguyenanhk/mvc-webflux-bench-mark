package com.bench.webflux.repo;

import com.bench.webflux.entity.UserRow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Repository
public interface UserR2dbcRepository extends ReactiveCrudRepository<UserRow, Long> {
    Mono<UserRow> findByEmail(String email);

    Flux<UserRow> findAll();

    Flux<UserRow> findAllBy(Pageable pageable);
}
