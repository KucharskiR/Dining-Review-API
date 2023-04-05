package com.diningreview.model;


import jakarta.persistence.Column;
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
	@Column(nullable=false)
	private @Getter @Setter String name;
	@Column(nullable=false)
	private @Getter @Setter String password;
	private @Getter @Setter String email;
	private @Getter @Setter String role;
	private @Getter @Setter Boolean peanutInterested;
	private @Getter @Setter Boolean eggInterested;
	private @Getter @Setter Boolean dairyInterested;
	
	
	public User (String name, String password, String email, Boolean peanutInterested,
			Boolean eggInterested, Boolean dairyInterested, String role) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
		this.peanutInterested = peanutInterested;
		this.eggInterested = eggInterested;
		this.dairyInterested = dairyInterested;
	}
	
}