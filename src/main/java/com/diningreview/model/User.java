package com.diningreview.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {
	public User() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private @Getter Long id;
	private @Getter @Setter String name;
	private @Getter @Setter String city;
	private @Getter @Setter String state;
	private @Getter @Setter String zipcode;
	private @Getter @Setter Boolean peanutInterested;
	private @Getter @Setter Boolean eggInterested;
	private @Getter @Setter Boolean dairyInterested;
	
	public User (String name, String city, String state, String zipcode, Boolean peanutInterested,
			Boolean eggInterested, Boolean dairyInterested) {
		this.name = name;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.peanutInterested = peanutInterested;
		this.eggInterested = eggInterested;
		this.dairyInterested = dairyInterested;
	}
	
}