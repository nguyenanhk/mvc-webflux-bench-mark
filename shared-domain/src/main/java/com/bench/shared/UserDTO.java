package com.bench.shared;

import java.time.Instant;

public record UserDTO(Long id, String email, String fullName, Instant createdAt) {
}
