package com.akb.springdatamongodb.entity;

import java.util.Date;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@Document("applicant")
@AllArgsConstructor
@ToString
public class Applicant {
	
	private String id;
	private String fname;
	private String lname;
	private Date dob;
	private String ssn;
	private String email;
	private boolean isPrimary;
	private List<Address> addressList;

}
