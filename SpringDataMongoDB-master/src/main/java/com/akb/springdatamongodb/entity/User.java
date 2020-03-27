package com.akb.springdatamongodb.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
public class User {
		
	@Id
	private String id;
	
	@Indexed
	private String ic;
	
	private String name;	
	
	private int age;


}