package com.skilltracker.engineer.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.skilltracker.engineer.entity.SkillProfile;
import com.skilltracker.engineer.model.SearchCriteria;




@Service
public class SkillProfileQueryService {
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private DynamoDBMapper dynamoDBMapper;

		
	public List<SkillProfile> getskills(String associateId) {
		
		SkillProfile partitionKey = new SkillProfile();
		partitionKey.setAssociateId(associateId);
		
		DynamoDBQueryExpression<SkillProfile>queryExpression = new DynamoDBQueryExpression<SkillProfile>().withHashKeyValues(partitionKey);
		List<SkillProfile> skillProfileList = dynamoDBMapper.query(SkillProfile.class, queryExpression);
		
		return skillProfileList;
		
		
	}
	
	/*
	 * public List<SkillProfile> getSkills(String name,String associateId, String
	 * skillName){ SkillProfile secondaryIndexPartitionKey = new SkillProfile();
	 * Map<String, AttributeValue> attributeValues = new HashMap<>();
	 * attributeValues.put(":associateId", new AttributeValue(associateId));
	 * 
	 * secondaryIndexPartitionKey.setName(name);
	 * DynamoDBQueryExpression<SkillProfile> queryExpression = new
	 * DynamoDBQueryExpression<SkillProfile>()
	 * .withHashKeyValues(secondaryIndexPartitionKey)
	 * .withFilterExpression("associateId = :associateId") .withLimit(10)
	 * .withIndexName("name-associateId-index") .withConsistentRead(false);
	 * 
	 * List<SkillProfile> skillProfileList =
	 * dynamoDBMapper.query(SkillProfile.class, queryExpression); List<SkillProfile>
	 * filteredskillProfileList = new ArrayList<SkillProfile>(); for(SkillProfile
	 * skillProfile:skillProfileList) {
	 * if(!skillProfile.getSkills().stream().filter(m ->
	 * m.get("skillName").equals(skillName)).collect(Collectors.toList()).isEmpty())
	 * { filteredskillProfileList.add(skillProfile); } }
	 * 
	 * 
	 * return skillProfileList;
	 * 
	 * }
	 */
	
	
	
	  public List<SkillProfile> getSkills(String associateId,String skillName,String name){
	  
		  return  profileRepository.getskills(associateId, skillName,name);
	  }
     
	
	 
	
	
	  public List<SkillProfile> getSkills(SearchCriteria searchCriteria){
	  
		  
		  return getSkills(searchCriteria.getAssociateId(),searchCriteria.getSkillName(),searchCriteria.getName());
	  }
	 
}
