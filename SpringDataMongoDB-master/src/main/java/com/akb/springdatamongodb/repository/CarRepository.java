package com.akb.springdatamongodb.repository;
import java.util.List;

import com.akb.springdatamongodb.entity.Car;
 
public interface CarRepository {
 
	 public List aggregationByAll();
	 public List aggregationByYear(int year);
	 public void create(Car car);
	 public void drop();
	 public boolean saveAll(List<Car> entites);
}