package com.diningreview.repository;

import org.springframework.data.repository.CrudRepository;

import com.diningreview.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{
	
}