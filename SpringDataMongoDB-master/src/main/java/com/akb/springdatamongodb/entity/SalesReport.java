package com.akb.springdatamongodb.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class SalesReport {
 
	 private String brand;
	 
	 private long total;
	 
	 private int salesyear;
}