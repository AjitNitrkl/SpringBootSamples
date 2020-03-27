package com.akb.springdatamongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class UserTO {
	private String fname;
	private String ssn;
	private Date dob;
	private String email;

}
