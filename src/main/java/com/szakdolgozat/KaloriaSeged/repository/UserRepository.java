package com.szakdolgozat.KaloriaSeged.repository;

import com.szakdolgozat.KaloriaSeged.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
