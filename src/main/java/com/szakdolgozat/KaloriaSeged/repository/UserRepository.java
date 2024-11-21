package com.szakdolgozat.KaloriaSeged.repository;

import com.szakdolgozat.KaloriaSeged.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for the {@link User} object.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
