package com.skilltracker.engineer.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.skilltracker.engineer.entity.Profile;
import com.skilltracker.engineer.entity.Skill;
import com.skilltracker.engineer.entity.SkillProfile;

@Repository
public class ProfileRepository {
	
	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	public Profile saveProfile(Profile profile) {
		dynamoDBMapper.save(profile);
		return profile;
	}
	
	public Profile getProfileById(String associateId) {
		return dynamoDBMapper.load(Profile.class,associateId);
		
	} 
	
	public String deleteProfileById(String associateId) {
		
		dynamoDBMapper.batchDelete(dynamoDBMapper.load(Profile.class,associateId));
		return "Profile Id :"+ associateId +"deleted!";
	}
	

	public String updateProfile(String associateId, Skill skill ) {
		
		dynamoDBMapper.save(skill,new DynamoDBSaveExpression().withExpectedEntry("associateId",new ExpectedAttributeValue(
				new AttributeValue().withS(associateId)
				)));
		return associateId;
	}
	
	public String saveSkill(String associateId, Skill skill ) {
		
		dynamoDBMapper.save(skill);
		return associateId;
	}
	
	public void saveSkillProfile(String associateId, SkillProfile skillProfile) {
		dynamoDBMapper.save(skillProfile);
	}
	
	public String updateSkill(String associateId,Skill skill) {
		dynamoDBMapper.save(skill,new DynamoDBSaveExpression().withExpectedEntry("associateId",new ExpectedAttributeValue(
				new AttributeValue().withS(associateId)
				)));
		return associateId;
	}
	
	/*
	 * public List<SkillProfile> getskills(String associateId) {
	 * 
	 * SkillProfile partitionKey = new SkillProfile();
	 * partitionKey.setAssociateId("480848");
	 * 
	 * DynamoDBQueryExpression<SkillProfile>queryExpression = new
	 * DynamoDBQueryExpression<SkillProfile>().withHashKeyValues(partitionKey);
	 * List<SkillProfile> skillProfileList =
	 * dynamoDBMapper.query(SkillProfile.class, queryExpression);
	 * 
	 * return skillProfileList;
	 * 
	 * 
	 * }
	 */
	
	public void updateSkillProfile(String associateId,SkillProfile skillProfile) {
		dynamoDBMapper.save(skillProfile,new DynamoDBSaveExpression().withExpectedEntry("associatedId",
				new ExpectedAttributeValue(new AttributeValue().withS(associateId))));
	}
	
	
	public List<SkillProfile> getskills(String associateId,String skillName,String name) {
		List<SkillProfile> skillProfileList = null;
		
		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		
		  SkillProfile skillProfile = new SkillProfile();
		  if(!skillName.isEmpty()) {
		  skillProfile.setSkillName(skillName);
		  }
		  if(!associateId.isEmpty()) {
		  skillProfile.setAssociateId(associateId);
		  }
		  
		  
		  
		  
		/*
		 * DynamoDBQueryExpression<SkillProfile>queryExpression = new
		 * DynamoDBQueryExpression<SkillProfile>().
		 * withKeyConditionExpression("skillName = :val1 and associateId = :val2")
		 * .withExpressionAttributeValues(eav);
		 */
		  if(!name.isEmpty()) {
			  eav.put(":val1", new AttributeValue().withS(name));
			
		  DynamoDBQueryExpression<SkillProfile>queryExpression = new DynamoDBQueryExpression<SkillProfile>()
				  .withHashKeyValues(skillProfile)
				  .withFilterExpression("profileName = :val1")
		            .withExpressionAttributeValues(eav);
		  skillProfileList = dynamoDBMapper.query(SkillProfile.class, queryExpression);
		  }else {
			  DynamoDBQueryExpression<SkillProfile>queryExpression = new DynamoDBQueryExpression<SkillProfile>()
					  .withHashKeyValues(skillProfile);
		  skillProfileList = dynamoDBMapper.query(SkillProfile.class, queryExpression);
		  }
		
		
		return skillProfileList;
		
		
	}
	
	
	

}
