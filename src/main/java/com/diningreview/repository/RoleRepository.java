package com.diningreview.repository;

import org.springframework.data.repository.CrudRepository;

import com.diningreview.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}