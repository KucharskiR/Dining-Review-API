package com.diningreview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "restaurant_table")
public class Restaurant {
	public Restaurant() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private @Getter Long id;

	private @Getter @Setter String name;
	private @Getter @Setter Double peanutScore;
	private @Getter @Setter Double eggScore;
	private @Getter @Setter Double dairyScore;

}