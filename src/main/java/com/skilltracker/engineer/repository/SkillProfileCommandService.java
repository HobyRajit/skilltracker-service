package com.skilltracker.engineer.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilltracker.engineer.entity.Profile;
import com.skilltracker.engineer.entity.Skill;
import com.skilltracker.engineer.entity.SkillProfile;
import com.skilltracker.engineer.model.ProfileBean;
import com.skilltracker.engineer.model.SkillBean;
import com.skilltracker.engineer.service.SkillProfileService;

@Service
public class SkillProfileCommandService {
	
	Logger logger = LoggerFactory.getLogger(SkillProfileCommandService.class);
	@Autowired
	private KafkaTemplate<String,SkillProfile> kafkaTemplate;

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${kafka.topic}")
	private String kafkaTopic;

	
	public SkillProfile saveSkillProfile(SkillProfile newSkillProfile) {
		
		List<SkillProfile> skillprofileList = profileRepository.getskills(newSkillProfile.getSkillName(),newSkillProfile.getAssociateId(),newSkillProfile.getProfileName());
		
		Optional<List<SkillProfile>> skillProfileListNullable = Optional.ofNullable(skillprofileList);
		ArrayList<SkillBean> newSkillList = new ArrayList<SkillBean>();
		if (skillProfileListNullable.isPresent() && !skillprofileList.isEmpty()) {
			
			SkillProfile skillProfile = skillprofileList.get(0);
			/*
			 * List<Map<String,String>> filteredSkill =
			 * skillProfile.getSkills().stream().filter(m ->!
			 * m.get("skillName").equalsIgnoreCase(newSkillProfile.getSkills().get(0).get(
			 * "skillName"))).collect(Collectors.toList());
			 * filteredSkill.addAll(newSkillProfile.getSkills());
			 * skillProfile.setSkills(filteredSkill);
			 * skillProfile.getSkills().addAll(newSkillProfile.getSkills());
			 */
			try {
			JsonNode skills = objectMapper.readTree(skillProfile.getSkills());
			
			int size = skills.size();
			for(int i=0;i<size;i++) {
				
				SkillBean skill = new SkillBean();
				skill.setSkillName(skills.get(i).get("skillName").textValue());
				skill.setLevel(skills.get(i).get("level").textValue());
				newSkillList.add(skill);
				
			}
			SkillBean skill = new SkillBean();
			skill.setSkillName(newSkillProfile.getSkillName());
			skill.setLevel(newSkillProfile.getLevel());
			newSkillList.add(skill);
			String newSkillsjsonRaw= objectMapper.writeValueAsString(objectMapper.valueToTree(newSkillList));
			logger.debug("NewSkillset :"+newSkillsjsonRaw);
			skillprofileList.get(0).setSkills(newSkillsjsonRaw);
			}catch(Exception e) {
				logger.error("Exception while converting JsonProfile skills to JSnode");
			}
			profileRepository.saveSkillProfile(newSkillProfile.getAssociateId(), newSkillProfile);
		} else {
			Skill newSkill = new Skill();
			newSkill.setSkillName(newSkillProfile.getSkillName());
			newSkill.setLevel(newSkillProfile.getLevel());
			ArrayList<Skill> newSkills = new ArrayList<Skill>();
			newSkills.add(newSkill);
			try {
				newSkillProfile.setSkills(objectMapper.writeValueAsString(objectMapper.valueToTree(newSkills)));
			}catch(Exception e) {
				logger.debug("Exception in creating JsonNode from skill object");
			}
			profileRepository.saveSkillProfile(newSkillProfile.getAssociateId(),
					newSkillProfile);
			
		}
		return newSkillProfile;
	}
	
	
	public Profile saveProfile(ProfileBean profileBean) {
		logger.debug("Saving the profile in repository");
		SkillProfile skillProfile = SkillProfileService.buildSkillProfile(profileBean);
		kafkaTemplate.send(kafkaTopic,skillProfile);
		return profileRepository.saveProfile(SkillProfileService.buildProfileEntity(profileBean));
	}

	public Profile getProfileById(String associateId) {
		logger.debug("Getting the profile in repository");
		return profileRepository.getProfileById(associateId);

	}

	public String deleteProfileById(String associateId) {
		logger.debug("Delete profile in repository");
		profileRepository.deleteProfileById(associateId);
		return "Profile Id :" + associateId + "deleted!";
	}
	
	
	public String updateProfile(String associateId, SkillBean skillBean ) {
		Skill skill =SkillProfileService.buildSkillEntity(skillBean);
		skill.setAssociateId(associateId);
		profileRepository.updateProfile(associateId,skill);
				return associateId;
	}
	
	public String saveSkill(String associateId, SkillBean skillBean) {
		Skill skill =SkillProfileService.buildSkillEntity(skillBean);
		skill.setAssociateId(associateId);
		SkillProfile skillProfile = SkillProfileService.buildSkillProfile(associateId,skillBean);
		kafkaTemplate.send(kafkaTopic,skillProfile);
		
		profileRepository.saveSkill(associateId, skill);

		return associateId;
	}
	
	public String updateSkill(String associateId,SkillBean skillBean) {
		Skill skill =SkillProfileService.buildSkillEntity(skillBean);
		skill.setAssociateId(associateId);
		SkillProfile skillProfile = SkillProfileService.buildSkillProfile(associateId,skillBean);
		kafkaTemplate.send(kafkaTopic,skillProfile);
		profileRepository.updateSkill(associateId, skill);
		
		return associateId;
	}

}