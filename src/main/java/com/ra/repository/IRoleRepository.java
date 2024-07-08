package com.ra.repository;

import com.ra.constants.RoleName;
import com.ra.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface IRoleRepository extends JpaRepository<Roles,Long> {
	Optional<Roles> findByRoleName(RoleName roleName);
}
