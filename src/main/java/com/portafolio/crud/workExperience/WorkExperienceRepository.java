package com.portafolio.crud.workExperience;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long>{

	Optional<WorkExperience> findById(Long id);
	
	void deleteById(Long id);

	Set<WorkExperience> findByUserId(Long id);
}
