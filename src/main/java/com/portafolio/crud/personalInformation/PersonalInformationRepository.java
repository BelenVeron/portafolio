package com.portafolio.crud.personalInformation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long>{

	Optional<PersonalInformation> findById(Long id);
	
	void deleteById(Long id);

	Optional<PersonalInformation> findByUserId(Long id);

}
