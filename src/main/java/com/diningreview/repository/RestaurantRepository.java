package com.diningreview.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.diningreview.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{
	Restaurant deleteById(long id);
	List<Restaurant> getAllByOrderByNameAsc();
	Optional<Restaurant> findByName(String name);
	Restaurant getById(long id);
	
}