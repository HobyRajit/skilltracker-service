package com.skilltracker.engineer.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "profile")
public class Profile {
	@DynamoDBHashKey(attributeName="associateId")
	private String associateId;
	
	@DynamoDBAttribute
	private String name;
	
	@DynamoDBAttribute
	private String emailId;

}
