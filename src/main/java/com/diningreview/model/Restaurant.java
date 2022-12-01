package com.diningreview.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Restaurant {
	private Long id;
	
	private @Getter @Setter String name;
	private @Getter @Setter Double peanutScore;
	private @Getter @Setter Double eggScore;
	private @Getter @Setter Double dairyScore;
	
}