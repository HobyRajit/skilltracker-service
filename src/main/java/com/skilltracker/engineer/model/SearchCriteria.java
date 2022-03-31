package com.skilltracker.engineer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SearchCriteria {
	private String associateId;
	private String name;
	private String skillName;

}
