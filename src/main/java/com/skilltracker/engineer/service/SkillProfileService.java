package com.skilltracker.engineer.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilltracker.engineer.entity.Profile;
import com.skilltracker.engineer.entity.Skill;
import com.skilltracker.engineer.entity.SkillProfile;
import com.skilltracker.engineer.model.ProfileBean;
import com.skilltracker.engineer.model.SkillBean;
import com.skilltracker.engineer.model.SkillProfileBean;


public  class SkillProfileService {
	static Logger logger = LoggerFactory.getLogger(SkillProfileService.class);
	
	@Autowired
	 private static ObjectMapper objectMapper;

		public static Profile buildProfileEntity(ProfileBean profileBean) {
		Profile entityProfile = new Profile();
		BeanUtils.copyProperties(profileBean, entityProfile);
		return entityProfile;

	}
	
	
		public static Skill buildSkillEntity(SkillBean skillBean){
		Skill entitySkill = new Skill();
		BeanUtils.copyProperties(skillBean, entitySkill);
		return entitySkill; 
	}
		public static SkillProfile buildSkillProfileEntity(SkillProfileBean skillProfileBean) {
			SkillProfile skillProfile = new SkillProfile();
			skillProfile.setAssociateId(skillProfileBean.getAssociateId());
			skillProfile.setProfileName(skillProfileBean.getProfileName());
			skillProfile.setEmailId(skillProfileBean.getEmailId());
			skillProfile.setSkillName(skillProfileBean.getSkillName());
			skillProfile.setLevel(skillProfileBean.getLevel());
		/*
		 * try { objectMapper.writeValueAsString(skillProfileBean.getSkills());
		 * }catch(Exception e) {
		 * 
		 * }
		 */
			return skillProfile;
		}
		
		public static SkillProfile buildSkillProfile(String associateId,SkillBean skillBean) {
			Skill skill = buildSkillEntity(skillBean);
			ArrayList<Skill> skills = new ArrayList<Skill>();
			skills.add(skill);
			
			SkillProfile skillProfile = new SkillProfile();
			skillProfile.setAssociateId(associateId);
			try {
			skillProfile.setSkills(objectMapper.writeValueAsString(skills));
			}catch(Exception e) {
				logger.debug("Exception in converting skills array to JSON");
			}
			return skillProfile;
		}
		
		public static SkillProfile buildSkillProfile(ProfileBean profileBean) {
			SkillProfile skillProfile = new SkillProfile();
			BeanUtils.copyProperties(profileBean, skillProfile);
			return skillProfile;
			
		}
		
		
		
		
		
}
