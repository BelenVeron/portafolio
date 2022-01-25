package com.portafolio.crud.personalInformation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Integer>{

	PersonalInformation findById(int id);

	void deleteById(int id);

	boolean existsByUserId(int id);

	PersonalInformation findByUserId(int id);

}
