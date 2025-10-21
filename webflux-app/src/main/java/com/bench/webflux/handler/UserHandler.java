package com.bench.webflux.handler;

import com.bench.shared.UserDTO;
import com.bench.webflux.entity.UserRow;
import com.bench.webflux.repo.UserR2dbcRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserHandler {
    private final UserR2dbcRepository repo;

    public UserHandler(UserR2dbcRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> byId(@PathVariable Long id) {
        return repo.findById(id)
                .map(e -> new UserDTO(e.getId(), e.getEmail(), e.getFullName(), e.getCreatedAt()));
    }

    @GetMapping
    public Flux<UserDTO> list(@RequestParam Integer page, @RequestParam Integer pageSize) {
        if (page < 0) page = 0;
        if (pageSize <= 0) pageSize = 20;
        if (pageSize > 1000) pageSize = 1000; // prevent abuse

        PageRequest pageable = PageRequest.of(page, pageSize);
        Flux<UserRow> allBy = repo.findAllBy(pageable);
        return allBy
                .map(e -> new UserDTO(e.getId(), e.getEmail(), e.getFullName(), e.getCreatedAt()));
    }
}
