package com.bench.webflux.handler;

import com.bench.shared.UserDTO;
import com.bench.webflux.repo.UserR2dbcRepository;
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
    public Flux<UserDTO> list(@RequestParam(defaultValue = "50") int limit) {
        return repo.findAll().take(limit).map(e -> new UserDTO(e.getId(), e.getEmail(), e.getFullName(), e.getCreatedAt()));
    }
}
