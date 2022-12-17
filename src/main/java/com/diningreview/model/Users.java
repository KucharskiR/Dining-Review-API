package com.diningreview.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @Getter @Setter String name;
	private @Getter @Setter String city;
	private @Getter @Setter String state;
	private @Getter @Setter Long zipcode;
	private @Getter @Setter Boolean peanutInterested;
	private @Getter @Setter Boolean eggInterested;
	private @Getter @Setter Boolean dairyInterested;
}