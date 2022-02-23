package com.portafolio.crud.cloudinary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByOrderById();

	Optional<Image> findById(Long id);

	void deleteById(Long id);

	boolean existsById(Long id);
}