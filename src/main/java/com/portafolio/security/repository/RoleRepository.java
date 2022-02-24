package com.portafolio.security.repository;

import com.portafolio.security.entity.Role;
import com.portafolio.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

	Optional<Role> findById(Long id);

}
