package com.diningreview.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
public class Reviews {
	
	private enum reviewStatus {Accepted, Rejected, Pending};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @Getter @Setter String userName;
	private @Getter @Setter Long restaurantId;
	private @Getter @Setter Double peanutScore;
	private @Getter @Setter Double eggScore;
	private @Getter @Setter Double dairyScore;
	private @Getter @Setter String comment;
	private @Getter @Setter reviewStatus status;
	
	
}