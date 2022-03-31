package com.skilltracker.engineer.model;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillProfileBean {
	
	private String associateId;
	
	
	private String  profileName;
	
	
	private String emailId;
	
	private String skillName;
	
	private String level;
	
	private JsonNode  skills;
	 

}
