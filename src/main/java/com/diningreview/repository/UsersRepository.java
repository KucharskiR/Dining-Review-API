package com.diningreview.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.diningreview.model.User;

public interface UsersRepository extends CrudRepository<User, Long>{
	Optional<User> findByName(String name);
	List<User> getAllByOrderByNameAsc();
	User deleteById(long id);
}