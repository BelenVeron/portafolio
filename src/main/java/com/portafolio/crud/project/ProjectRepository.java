package com.portafolio.crud.project;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

	Optional<Project> findById(Long id);
	
	void deleteById(Long id);

	Set<Project> findByUserId(Long id);

}
