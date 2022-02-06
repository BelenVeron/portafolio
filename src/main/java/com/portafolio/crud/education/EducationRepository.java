package com.portafolio.crud.education;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository  extends JpaRepository<Education, Long>{
	
	Optional<Education> findById(Long id);
	
	void deleteById(Long id);

	Set<Education> findByUserId(Long id);

}
