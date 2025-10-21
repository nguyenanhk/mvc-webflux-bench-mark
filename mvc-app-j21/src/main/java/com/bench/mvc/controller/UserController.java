package com.bench.mvc.controller;

import com.bench.mvc.entity.UserEntity;
import com.bench.mvc.repo.UserRepository;
import com.bench.shared.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public List<UserDTO> list(@RequestParam Integer page, @RequestParam Integer pageSize) {
        if (page < 0) page = 0;
        if (pageSize <= 0) pageSize = 20;
        if (pageSize > 1000) pageSize = 1000; // prevent abuse

        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<UserEntity> pageableResponses = repo.findAll(pageable);

        System.out.println("current thread: " + Thread.currentThread().toString());

        List<UserEntity> content = pageableResponses.getContent();
        return content.stream().map(e -> new UserDTO(e.getId(), e.getEmail(), e.getFullName(), e.getCreatedAt())).toList();
    }

}
