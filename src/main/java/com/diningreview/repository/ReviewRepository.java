package com.diningreview.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.diningreview.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>{
	Review deleteById(long id);
	Optional<Review> findByUserName(String userName);
}