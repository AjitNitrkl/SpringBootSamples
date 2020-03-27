package com.akb.springdatamongodb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import com.akb.springdatamongodb.entity.Car;
import com.akb.springdatamongodb.entity.SalesReport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Repository
public class CarRepositoryImpl implements CarRepository {
	
	private final MongoTemplate carMongoTemplate;

	 @Override
	 public List aggregationByAll() {
	  Aggregation aggregation = newAggregation(
	    group("brand","salesyear").sum("numberOfCars").as("total"),
	    sort(Sort.Direction.ASC, previousOperation(), "brand")    
	  );
	  AggregationResults groupResults = carMongoTemplate.aggregate(
	    aggregation, Car.class, SalesReport.class);
	  
	  List salesReport = groupResults.getMappedResults();
	  return salesReport;
	 }
	 
	 
	 @Override
	 public List aggregationByYear(int year) {
	  Aggregation aggregation = newAggregation(
	    match(Criteria.where("salesyear").is(year)),
	    group("brand","salesyear").sum("numberOfCars").as("total"),
	    sort(Sort.Direction.ASC, previousOperation(), "brand")    
	  );
	  AggregationResults groupResults = carMongoTemplate.aggregate(
	    aggregation, Car.class, SalesReport.class);
	  
	  List salesReport = groupResults.getMappedResults();
	  return salesReport;
	 }

	@Override
	public void create(Car car) {
		carMongoTemplate.save(car);
	}

	@Override
	public void drop() {
		carMongoTemplate.dropCollection(Car.class);
	}


	@Override
	public boolean saveAll(List<Car> entites) {
		 carMongoTemplate.insertAll(entites);
		 return true;
	}

}
