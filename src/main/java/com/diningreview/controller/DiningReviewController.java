package com.diningreview.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diningreview.model.User;
import com.diningreview.repository.UsersRepository;

@Controller
public class DiningReviewController {
	private final UsersRepository usersRepository;

	public DiningReviewController(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@GetMapping("/admin")
	public String getUsers(Model model) {
		model.addAttribute("userslist", usersRepository.getAllByOrderByNameAsc());
		return "admin";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteThroughId(@PathVariable(value = "id") long id) {
		usersRepository.deleteById(id);
		return "redirect:/admin";
	}

	@PostMapping("/users/add")
	public User addUser(@RequestBody User user) {
		Optional<User> userOptional = this.usersRepository.findByName(user.getName());
		User newUser = null;
		if (userOptional.isPresent()) {
			throw new RuntimeException("User " + user.getName() + " already exist");
		} else 
			newUser = this.usersRepository.save(user);
		return newUser;
	}
	private List<User> getUsersList() {
		List<User> userList = this.usersRepository.getAllByOrderByNameAsc();
		return userList;
	}

}