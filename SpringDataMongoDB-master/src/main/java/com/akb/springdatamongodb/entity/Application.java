package com.akb.springdatamongodb.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Document(collection="application")
@Data
@Builder
public class Application {
	
	@Id
	  @Field("_id")
	  private String id;
	  private String status;
	  @Indexed
	  private String idempotenceId;
	  private List<Applicant> applicantList;
	  private String product;
	  

}
