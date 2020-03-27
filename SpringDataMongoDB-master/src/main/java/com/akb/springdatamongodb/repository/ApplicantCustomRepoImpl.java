package com.akb.springdatamongodb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
//import org.springframework.data.mongodb.repository.Query;
import com.akb.springdatamongodb.entity.Applicant;
import com.mongodb.client.result.UpdateResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Repository
public class ApplicantCustomRepoImpl implements ApplicantCustomRepo{
	
	private final MongoTemplate applicantMongoTemplate;
	
	private final ApplicantRepository applicantRepository;
	
	Query query;
	

	@Override
	public boolean updateSSN(String appId, String ssn) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(appId));
		Update update = new Update().set("ssn", ssn);
		UpdateResult result = applicantMongoTemplate.updateFirst(query, update, Applicant.class);
		return result.getModifiedCount()>0 ?true:false;
	}
	public static String getIds(String value) {
		return value.replace("id", "");
	}
	
	@Override
	public Page<Applicant> customSearch(List<String> search){
		List<String> ids = search.stream()
		.map(ApplicantCustomRepoImpl::getIds)
		.collect(Collectors.toList());

		List<Applicant> appList = new ArrayList<Applicant>();
		search.stream().forEach(id -> {
					appList.add(applicantRepository.findById(id).get());
					});

		return new PageImpl<>(appList);
	}

	@Override
	public Page<Applicant> customSearchLname(String search, Pageable pageable){
		Query query = new Query().addCriteria(Criteria.where("lname").regex(search));
		query.with(pageable);
		List<Applicant> appList = applicantMongoTemplate.find(query, Applicant.class);
		return new PageImpl<>(appList);
	}

	
	//Alternate of the above
	@Override
	public List<Applicant> getAllApplicantPaginated(int pageNumber, int pageSize){
		Query query = new Query();
		query.skip(pageNumber * pageSize);
		query.limit(pageSize);
		
		return applicantMongoTemplate.find(query, Applicant.class);
	}
	@Override
	public List<Applicant> getAllApplicantUsingRegex() {
		//fname ends with a
		System.out.println("Fname Ending with a ");
		Query query = new Query().addCriteria(Criteria.where("fname").regex("a$"));
		return applicantMongoTemplate.find(query, Applicant.class);
	}
	
	
	

}
