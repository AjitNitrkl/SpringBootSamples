package com.akb.springdatamongodb.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akb.springdatamongodb.entity.AppResponse;
import com.akb.springdatamongodb.entity.Applicant;
import com.akb.springdatamongodb.repository.ApplicantCustomRepo;
import com.akb.springdatamongodb.repository.ApplicantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping(path="/app", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class AppController {
	
	private final ApplicantRepository applicantRepository;
	
	private final ApplicantCustomRepo applicantCustomRepository;
	
	@PostMapping("/applicant")
	public AppResponse postApplicant(@Valid @RequestBody Applicant applicant){
		String id = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
		applicant.setId(id);
		Applicant app =  applicantRepository.save(applicant);
		if(applicantRepository.findById(id).isPresent())
			log.info("Applicant saved succesfully");
		return AppResponse.builder().id(app.getId()).status("Pending").build();
	}
	
	@PatchMapping("/applicant/id/{id}/ssn/{ssn}")
	public AppResponse updateApplicantSsn(@PathVariable(value="id") String id,
			@PathVariable(value="ssn") String ssn) {
		applicantCustomRepository.updateSSN(id, ssn);
		return AppResponse.builder().id(id).status("Updated").build();
	}

}
