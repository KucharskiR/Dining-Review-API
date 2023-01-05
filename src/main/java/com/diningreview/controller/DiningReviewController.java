package com.diningreview.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.diningreview.model.Restaurant;
import com.diningreview.model.Review;
import com.diningreview.model.Review.reviewStatus;
import com.diningreview.model.User;
import com.diningreview.repository.RestaurantRepository;
import com.diningreview.repository.ReviewRepository;
import com.diningreview.repository.UsersRepository;

@Controller
public class DiningReviewController {
	private final UsersRepository usersRepository;
	private final RestaurantRepository restaurantRepository;
	private final ReviewRepository reviewRepository;

	public DiningReviewController(UsersRepository usersRepository, RestaurantRepository restaurantRepository,
			ReviewRepository reviewRepository) {
		this.usersRepository = usersRepository;
		this.restaurantRepository = restaurantRepository;
		this.reviewRepository = reviewRepository;
	}

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("restaurantlist", restaurantRepository.getAllByOrderByNameAsc());
		return "index";
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

	@GetMapping("/addReview/{id}")
	public String addReviewRestaurant(@PathVariable(value = "id") long id, Model model) {
		Review newReview = new Review();
		model.addAttribute("newReview", newReview);
		model.addAttribute("Restaurant", restaurantRepository.findById(id));
		return "addReview";
	}

	@PostMapping("/addReview/save")
	public String saveReview(@ModelAttribute("newReview") Review review, RedirectAttributes redirectAttributes) {
		review.setStatus(reviewStatus.Pending);
		Optional<Review> reviewOptional = this.reviewRepository.findByUserName(review.getUserName());
		if (reviewOptional.isPresent()) {
			redirectAttributes.addFlashAttribute("message", "You already added an opinion!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		} else {
			this.reviewRepository.save(review);
			redirectAttributes.addFlashAttribute("message", "Opinion added!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:/";
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

	@PostMapping("/admin/addRestaurant")
	public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
		Optional<Restaurant> restaurantOptional = this.restaurantRepository.findByName(restaurant.getName());
		Restaurant newRestaurant = null;
		if (restaurantOptional.isPresent()) {
			throw new RuntimeException("Restaurant " + restaurant.getName() + " already exist");
		} else
			newRestaurant = this.restaurantRepository.save(restaurant);
		return newRestaurant;
	}
}