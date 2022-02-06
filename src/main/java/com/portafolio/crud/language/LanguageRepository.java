package com.portafolio.crud.language;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository  extends JpaRepository<Language, Long>{
	
	Optional<Language> findById(Long id);
	
	void deleteById(Long id);

	Set<Language> findByUserId(Long id);

}
