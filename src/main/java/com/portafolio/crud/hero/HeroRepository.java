package com.portafolio.crud.hero;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long>{

	Optional<Hero> findById(Long id);
	
	void deleteById(Long id);

	Optional<Hero> findByUserId(Long id);

}
