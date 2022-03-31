 package com.skilltracker.engineer.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName="skill")
public class Skill {
	@DynamoDBHashKey(attributeName="associateId")
	private String associateId;
	
	@DynamoDBRangeKey(attributeName="skillName")
	private String skillName;
	
	@DynamoDBAttribute(attributeName="level")
	private String level;

}
