package com.skilltracker.engineer.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "skillprofilesearch")
public class SkillProfile {
	@DynamoDBRangeKey(attributeName="associateId")
	private String associateId;
	
	@DynamoDBAttribute
	private String profileName;
	
	@DynamoDBAttribute
	private String emailId;
	
	@DynamoDBHashKey(attributeName="skillName") 
	private String skillName;
	
	
	@DynamoDBAttribute
	private String level;
	
	
	@DynamoDBAttribute

	@JsonRawValue
	private String skills;
	 

	
	/*
	 * @DynamoDBAttribute
	 * 
	 * @JsonRawValue private String skills;
	 */
}
