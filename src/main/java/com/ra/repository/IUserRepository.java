package com.ra.repository;

import com.ra.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<Users,Long> {
	Optional<Users> findByEmail(String email);
	boolean existsByEmail(String email);
}
