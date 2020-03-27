package com.akb.springdatamongodb.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.akb.springdatamongodb.entity.Applicant;
import com.akb.springdatamongodb.entity.UserTO;

public interface ApplicantRepository extends MongoRepository<Applicant,Long>{
	
	Optional<Applicant> findBySsn(String ssn);
	Optional<Applicant> findById(String id);
	
	//fetch only 2 fields 
	@Query(value="{}", fields= "{fname:1,ssn:1}")
	List<UserTO> getAllApplicantExcludeIncludeFields();
	
	@Query("{'isPrimary': true}")
	List<UserTO> getAllPrimaryApplicant();
	
	@Query("{'dob': {$gte: ?0}}")
	List<UserTO> getEligibleApplicant(Date date);
	
	List<Applicant> getApplicantByFnameStartingWith(String str);
	Optional<List<Applicant>> getApplicantByLnameEndingWith(String str);
	
	//@Query("{'lname': ?0}")
	List<Applicant> getApplicantByLname(String lname);
	
	

}
