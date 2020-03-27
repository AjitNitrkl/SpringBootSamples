package com.akb.springdatamongodb.entity;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AppResponse {
	
	private String id;
	private String status;

}
