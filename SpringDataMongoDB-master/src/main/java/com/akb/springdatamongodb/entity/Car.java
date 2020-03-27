package com.akb.springdatamongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "cars")
public class Car {
	 @Id
	 private String id;
	 private String brand;
	 private String model;
	 private int numberOfCars;
	 private int salesyear;
}