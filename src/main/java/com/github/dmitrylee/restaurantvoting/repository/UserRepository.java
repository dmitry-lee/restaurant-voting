package com.github.dmitrylee.restaurantvoting.repository;

import com.github.dmitrylee.restaurantvoting.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}