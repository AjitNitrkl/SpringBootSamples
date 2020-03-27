package com.akb.springdatamongodb;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.akb.springdatamongodb.entity.Car;
import com.akb.springdatamongodb.entity.SalesReport;
import com.akb.springdatamongodb.repository.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRepositoryTests {
	
	@Autowired
	CarRepository carRepo;
	
	@Test
	public void testAggregate() {
		carRepo.drop();
		initialze();
		System.out.println("--------------Sales Report Both Year ---------------------------");
		   List<SalesReport> all = carRepo.aggregationByAll();
		   for (SalesReport salesReport : all) {
		    System.out.println(salesReport);
		   }
		   System.out.println("--------------Sales Report 2014-------------------------------");
		   List<SalesReport> byYear = carRepo.aggregationByYear(2014);
		   for (SalesReport salesReport : byYear) {
		    System.out.println(salesReport);
		   }
	}
	
	
	private void initialze(){
		List<Car> carList = new ArrayList<Car>();
		carList.add(Car.builder()
						  .id("1")
						  .brand("Volkswagen")
						  .model("Polo")
						  .numberOfCars(59600)
						  .salesyear(2014).build());
		
		carList.add(Car.builder()
				  .id("2")
				  .brand("Volkswagen")
				  .model("Polo")
				  .numberOfCars(29010)
				  .salesyear(2015).build());
		carList.add(Car.builder()
				  .id("3")
				  .brand("Volkswagen")
				  .model("Jetta")
				  .numberOfCars(25000)
				  .salesyear(2014).build());
		
		carList.add(Car.builder()
				  .id("4")
				  .brand("Volkswagen")
				  .model("Jetta")
				  .numberOfCars(16200)
				  .salesyear(2015).build());
		carList.add(Car.builder()
					.id("5")
				  .brand("Maruti Suzuki")
				  .model("Swift")
				  .numberOfCars(168000)
				  .salesyear(2014).build());
		carList.add(Car.builder()
				  .id("6")
				  .brand("Maruti Suzuki")
				  .model("Swift")
				  .numberOfCars(118000)
				  .salesyear(2015).build());
		 
		carList.add(Car.builder()
				.id("7")
				  .brand("Maruti Suzuki")
				  .model("Ertiga")
				  .numberOfCars(80000)
				  .salesyear(2014).build());
		carList.add(Car.builder()
				.id("8")
				  .brand("Maruti Suzuki")
				  .model("Ertiga")
				  .numberOfCars(42000)
				  .salesyear(2015).build());
		 
		carList.add(Car.builder()
				.id("9")
				  .brand("Hyundai")
				  .model("i20")
				  .numberOfCars(45000)
				  .salesyear(2014).build());
		 
		carList.add(Car.builder()
				.id("10")
				  .brand("Hyundai")
				  .model("i20")
				  .numberOfCars(19000)
				  .salesyear(2015).build());
		 
		carList.add(Car.builder()
				  .id("11")
				  .brand("Hyundai")
				  .model("i10")
				  .numberOfCars(95000)
				  .salesyear(2014).build());
		 
		carList.add(Car.builder()
				.id("12")
				  .brand("Hyundai")
				  .model("i10")
				  .numberOfCars(55000)
				  .salesyear(2014).build());
		
		carRepo.saveAll(carList);

	}

}
