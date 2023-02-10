package com.diningreview.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.diningreview.model.Restaurant;
import com.diningreview.model.Review;
import com.diningreview.model.Review.reviewStatus;
import com.diningreview.model.ReviewMod;
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

// Admin Section

	@GetMapping("/admin")
	public String getUsers(Model model) {
		model.addAttribute("userslist", usersRepository.getAllByOrderByNameAsc());
		model.addAttribute("pendingCount", reviewRepository.findByStatus(reviewStatus.Pending).size() == 0 ? 0
				: reviewRepository.findByStatus(reviewStatus.Pending).size());
		return "admin";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteThroughId(@PathVariable(value = "id") long id) {
		usersRepository.deleteById(id);
		return "redirect:/admin";
	}

	@GetMapping("/pendingReviews")
	public String pendingReviews(Model model) {
		List<ReviewMod> pendingReviewsList = new ArrayList<ReviewMod>();

		List<Review> pendingReviews = reviewRepository.findByStatus(reviewStatus.Pending);
		for (Review review : pendingReviews) {

			ReviewMod newReviewMod = new ReviewMod();
			Restaurant restaurantOptional = restaurantRepository.getById(review.getRestaurantId());

			newReviewMod.setUserName(review.getUserName());
			newReviewMod.setRestaurantId(review.getRestaurantId());
			newReviewMod.setPeanutScore(review.getPeanutScore());
			newReviewMod.setEggScore(review.getEggScore());
			newReviewMod.setDairyScore(review.getDairyScore());
			newReviewMod.setComment(review.getComment());
			newReviewMod.setStatus(review.getStatus());
			newReviewMod.setRestaurantName(restaurantOptional.getName());
			newReviewMod.setIdReview(review.getId());

			pendingReviewsList.add(newReviewMod);
		}
		model.addAttribute("pendingReviewsList", pendingReviewsList);
		return "pendingReviews";
	}

	@GetMapping("/acceptReview/{id}")
	public String acceptReview(@PathVariable(value = "id") long id, Model model) {
		Review acceptReview = reviewRepository.findById(id);
		acceptReview.setStatus(reviewStatus.Accepted);
		reviewRepository.save(acceptReview);

		List<Review> reviewsToRecalculate = reviewRepository.findByStatusAndRestaurantId(reviewStatus.Accepted,
				acceptReview.getRestaurantId());
		Restaurant restaurantMod = restaurantRepository.getById(acceptReview.getRestaurantId());
		restaurantMod.setPeanutScore(average(reviewsToRecalculate, "peanutScore"));
		restaurantMod.setEggScore(average(reviewsToRecalculate, "eggScore"));
		restaurantMod.setDairyScore(average(reviewsToRecalculate, "dairyScore"));
		restaurantRepository.save(restaurantMod);

		return "redirect:/pendingReviews";
	}

	@GetMapping("/rejectReview/{id}")
	public String rejectReview(@PathVariable(value = "id") long id, Model model) {
		Review rejectReview = reviewRepository.findById(id);
		rejectReview.setStatus(reviewStatus.Rejected);
		reviewRepository.save(rejectReview);
		return "redirect:/pendingReviews";
	}

//	Restaurant section

	@GetMapping("/addRestaurant")
	public String addRestaurant(Model model) {
		Restaurant newRestaurant = new Restaurant();
		model.addAttribute("newRestaurant", newRestaurant);
		return "addRestaurant";
	}

	@PostMapping("/addRestaurant/save")
	public String saveRestaurant(@ModelAttribute("newRestaurant") Restaurant restaurant,
			RedirectAttributes redirectAttributes) {
		Optional<Restaurant> restaurantOptional = this.restaurantRepository.findByName(restaurant.getName());
		restaurant.setPeanutScore(null);
		restaurant.setEggScore(null);
		restaurant.setDairyScore(null);
		if (restaurantOptional.isPresent()) {
			redirectAttributes.addFlashAttribute("message", "Restaurant already exist!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		} else {
			this.restaurantRepository.save(restaurant);
			redirectAttributes.addFlashAttribute("message", "Restaurant added");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:/admin";
	}

//	Review section

	@GetMapping("/addReview/{id}")
	public String addReviewRestaurant(@PathVariable(value = "id") long id, Model model) {
		Review newReview = new Review();
		model.addAttribute("newReview", newReview);
		model.addAttribute("Restaurant", restaurantRepository.getById(id));
		return "addReview";
	}

	@PostMapping("/addReview/save")
	public String saveReview(@ModelAttribute("newReview") Review review, RedirectAttributes redirectAttributes) {
		
		review.setStatus(reviewStatus.Pending);
		Optional<Review> reviewOptional = this.reviewRepository.findByUserNameAndRestaurantId(review.getUserName(),
				review.getRestaurantId());
		Optional<User> userOptional = this.usersRepository.findByName(review.getUserName());
		
		if (userOptional.isPresent()) {
			if (reviewOptional.isPresent()) {
				redirectAttributes.addFlashAttribute("message", "You already added an opinion for this restaurant!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			} else {
				this.reviewRepository.save(review);
				redirectAttributes.addFlashAttribute("message",
						"Opinion added! It must be accepted by the site Administrator");
				redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			}
		} else {
			redirectAttributes.addFlashAttribute("message",
					"User does not exist. Please use other Username or please Register.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return "redirect:/";
	}

	@GetMapping("/showComments/{id}")
	public String showComments(@PathVariable(value = "id") long id, Model model) {
		List<Review> allComments = reviewRepository.findByStatusAndRestaurantId(reviewStatus.Accepted, id);
		model.addAttribute("allCommentsList", allComments);
		model.addAttribute("restaurantName", restaurantRepository.getById(id).getName());
		return "showComments";
	}

//	User section

	@GetMapping("/newUser")
	public String newUser(Model model) {
		User newUser = new User();
		model.addAttribute("newUser", newUser);
		return "newUser";
	}

	@PostMapping("/newUser/save")
	public String saveUser(@ModelAttribute("newUser") User user, RedirectAttributes redirectAttributes) {
		Optional<User> userOptional = this.usersRepository.findByName(user.getName());
		if (userOptional.isPresent()) {
			redirectAttributes.addFlashAttribute("message", "User already exist!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		} else {
			this.usersRepository.save(user);
			redirectAttributes.addFlashAttribute("message", "User added!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		}
		return "redirect:/newUser";
	}

//	 Methods
	public Double average(List<Review> list, String score) {
		int size = 0;
		double sum = 0;
		double average = 0;
		for (Review review : list) {
			switch (score) {
			case "peanutScore": {
				if (review.getPeanutScore() != null) {
					{
						sum += review.getPeanutScore();
						size++;
						break;
					}
				}
				break;
			}
			case "eggScore": {
				if (review.getEggScore() != null) {
					{
						sum += review.getEggScore();
						size++;
						break;
					}
				}
				break;
			}
			case "dairyScore": {
				if (review.getDairyScore() != null) {
					{
						sum += review.getDairyScore();
						size++;
						break;
					}
				}
				break;
			}

			default:
				throw new IllegalArgumentException("Unexpected value: " + score);
			}
		}

		if (size > 0) {
			average = sum / size;
			average = Math.round(10 * average);
			average /= 10;
		} else
			average = 0;

		return average;

	}

//	@PostMapping("/users/add")
//	public User addUser(@RequestBody User user) {
//		Optional<User> userOptional = this.usersRepository.findByName(user.getName());
//		User newUser = null;
//		if (userOptional.isPresent()) {
//			throw new RuntimeException("User " + user.getName() + " already exist");
//		} else
//			newUser = this.usersRepository.save(user);
//		return newUser;
//	}

//	@PostMapping("/admin/addRestaurant")
//	public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
//		Optional<Restaurant> restaurantOptional = this.restaurantRepository.findByName(restaurant.getName());
//		Restaurant newRestaurant = null;
//		if (restaurantOptional.isPresent()) {
//			throw new RuntimeException("Restaurant " + restaurant.getName() + " already exist");
//		} else
//			newRestaurant = this.restaurantRepository.save(restaurant);
//		return newRestaurant;
//	}
}