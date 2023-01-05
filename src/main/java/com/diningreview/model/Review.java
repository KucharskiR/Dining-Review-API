package com.diningreview.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@AllArgsConstructor
public class Review {
	
	public enum reviewStatus {Accepted, Rejected, Pending};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private @Getter Long id;
	private @Getter @Setter String userName;
	private @Getter @Setter Long restaurantId;
	private @Getter @Setter Double peanutScore;
	private @Getter @Setter Double eggScore;
	private @Getter @Setter Double dairyScore;
	private @Getter @Setter String comment;
	private @Getter @Setter reviewStatus status;
	public Review() {
	}
	
	
}