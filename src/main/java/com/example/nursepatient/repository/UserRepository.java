package com.example.nursepatient.repository;

import com.example.nursepatient.entity.User;
import com.example.nursepatient.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRoleAndVille(Role role, String vile);
}
