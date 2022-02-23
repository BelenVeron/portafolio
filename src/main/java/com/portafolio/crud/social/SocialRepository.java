package com.portafolio.crud.social;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepository extends JpaRepository<Social, Long>{
	
	Optional<Social> findById(Long id);
	
	void deleteById(Long id);

	Set<Social> findByUserId(Long id);

}
