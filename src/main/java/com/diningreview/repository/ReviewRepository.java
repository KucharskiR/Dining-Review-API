package com.diningreview.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.diningreview.model.Review;
import com.diningreview.model.Review.reviewStatus;

public interface ReviewRepository extends CrudRepository<Review, Long>{
	Review deleteById(long id);
	Review findById(long id);
	Optional<Review> findByUserNameAndRestaurantId(String userName, Long restaurantId);
	List<Review> findByStatus(reviewStatus status);
	List<Review> findByStatusAndRestaurantId(reviewStatus status, Long restaurantId);
}