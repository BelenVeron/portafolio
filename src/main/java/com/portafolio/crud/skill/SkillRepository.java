package com.portafolio.crud.skill;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{
	
	Optional<Skill> findById(Long id);
	
	void deleteById(Long id);

	Set<Skill> findByUserId(Long id);

}
