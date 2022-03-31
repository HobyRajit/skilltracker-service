package com.skilltracker.engineer.model;



import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileBean {
	
	private String associateId;
	
	
	private String name;
	
	
	private String emailId;

}
