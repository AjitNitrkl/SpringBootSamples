package com.akb.springdatamongodb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akb.springdatamongodb.entity.Applicant;

public interface ApplicantCustomRepo {
	
	boolean updateSSN(String appId, String ssn); 
	Page<Applicant> customSearch(List<String> search);
	Page<Applicant> customSearchLname(String search, Pageable pageable);
	List<Applicant> getAllApplicantPaginated(int pageNumber, int pageSize);
	List<Applicant> getAllApplicantUsingRegex();

}
