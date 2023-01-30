package com.diningreview.model;

import lombok.Getter;
import lombok.Setter;

public class ReviewMod extends Review {
	
	private @Getter @Setter String restaurantName;
	public @Setter 		Long id;
	public @Setter 		Long idReview;
	
	public ReviewMod(Long id, String userName, Long restaurantId, Double peanutScore, Double eggScore, Double dairyScore, String comment,
	       reviewStatus status, String restaurantName, Long idReview) {
		
		super();
		this.restaurantName = restaurantName;
		this.idReview = idReview;
	}
	public ReviewMod() {
		
	}
}
