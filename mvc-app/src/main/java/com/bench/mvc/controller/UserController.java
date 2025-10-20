package com.bench.mvc.controller;

import com.bench.shared.UserDTO;
import com.bench.mvc.repo.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{id}")
    public UserDTO byId(@PathVariable Long id) {
        var e = repo.findById(id).orElseThrow();
        return new UserDTO(e.getId(), e.getEmail(), e.getFullName(), e.getCreatedAt());
    }

    @GetMapping
    public List<UserDTO> list(@RequestParam(defaultValue = "50") int limit) {
        return repo.findAll().stream().limit(limit).map(e -> new UserDTO(e.getId(), e.getEmail(), e.getFullName(), e.getCreatedAt())).toList();
    }
}
