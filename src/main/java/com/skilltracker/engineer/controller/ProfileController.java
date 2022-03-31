package com.skilltracker.engineer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilltracker.engineer.entity.Profile;
import com.skilltracker.engineer.entity.SkillProfile;
import com.skilltracker.engineer.model.ProfileBean;
import com.skilltracker.engineer.model.SearchCriteria;
import com.skilltracker.engineer.model.SkillBean;
import com.skilltracker.engineer.model.SkillProfileBean;
import com.skilltracker.engineer.repository.ProfileRepository;
import com.skilltracker.engineer.repository.SkillProfileCommandService;
import com.skilltracker.engineer.repository.SkillProfileQueryService;
import com.skilltracker.engineer.service.SkillProfileService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
public class ProfileController {
	Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private SkillProfileQueryService skillProfileQueryService;

	@Autowired
	private SkillProfileCommandService skillCommandService;
	
	
	/*
	 * @GetMapping("/health") public String health() { return "Service is up"; }
	 */

	@ApiOperation(value="Add Profile with a skill ")
	@PostMapping("/add/skillprofile")
	public SkillProfileBean saveSkillProfile(@RequestBody SkillProfileBean skillProfileBean) {
		logger.debug("Saving the SkillProfile");
		skillCommandService.saveSkillProfile(SkillProfileService.buildSkillProfileEntity(skillProfileBean));
		return skillProfileBean;
	}

	@ApiOperation(value="Add a Profile ")
	@PostMapping("/add/profile")
	public Profile saveProfile(@RequestBody ProfileBean profileBean) {
		// return profileRepository.saveProfile(profile);
		logger.debug("Saving the Profile");
		return skillCommandService.saveProfile(profileBean);

	}

	@ApiOperation(value="Search a Profile with Id ")
	@GetMapping("/get/profile/{id}")
	public Profile getProfileById(@PathVariable("id") String associateId) {
		logger.debug("Getting the Profile");
		return profileRepository.getProfileById(associateId);
	}

	/*
	 * @GetMapping("/get/skills/{id}") public List<SkillProfile>
	 * geSkills(@PathVariable("id") String associateId) {
	 * logger.debug("Getting the Skills"); return
	 * profileRepository.getskills(associateId); }
	 */
	@ApiOperation(value="Search a Profile with a skill criteria ")
	@PostMapping("/get/skills")
	public ResponseEntity<List<SkillProfile>>getSkills(@RequestBody SearchCriteria  searchCriteria){
		logger.debug("Getting the Skills with search criteria");
		ResponseEntity<List<SkillProfile>> responseEntity = new ResponseEntity<List<SkillProfile>>(skillProfileQueryService.getSkills(searchCriteria),HttpStatus.OK);
		 return  responseEntity;
	  }
	 
	
	
	/*
	 * @GetMapping("/get/skills/{id}") public List<SkillProfile>
	 * geSkills(@PathVariable("id") String associateId) {
	 * logger.debug("Getting the Skills"); return
	 * profileRepository.getskills(associateId); }
	 */
	@ApiOperation(value="Search a Profile with a skill criteria ")
	@GetMapping("/get/skills/{searchCriteria}")
	public ResponseEntity<List<SkillProfile>>getSkills(@PathVariable("searchCriteria")  String searchCriteria){
		logger.debug("Getting the Skills with search criteria");
		ObjectMapper mapper = new ObjectMapper();
		SearchCriteria searchCriteriaObj = null;
		try {
			searchCriteriaObj = mapper.readValue(searchCriteria, SearchCriteria.class);
		}catch(Exception e) {
			logger.debug("Exception in deserializing searchCriteria");
		}
		ResponseEntity<List<SkillProfile>> responseEntity = new ResponseEntity<List<SkillProfile>>(skillProfileQueryService.getSkills(searchCriteriaObj),HttpStatus.OK);
		 return  responseEntity;
	  }
	 
	
	
	@ApiOperation(value="Delete a profile ")

	@DeleteMapping("delete/profile/{id}")
	public String deleteProfileById(@PathVariable("id") String associateId) {
		return profileRepository.deleteProfileById(associateId);
	}

	@ApiOperation(value="Update a profile ")
	@PutMapping("/update/profile/{id}")
	public String updateProfile(@PathVariable("id") String associateId, @RequestBody SkillBean skillBean) {
		logger.debug("Adding the skills");
		return skillCommandService.saveSkill(associateId, skillBean);

	}

	@ApiOperation(value="Update the skilld ")
	@PutMapping("/update/skill/{id}")
	public String updateSkill(@PathVariable("id") String associateId, @RequestBody SkillBean skillBean) {
		return skillCommandService.updateSkill(associateId, skillBean);

	}

}
