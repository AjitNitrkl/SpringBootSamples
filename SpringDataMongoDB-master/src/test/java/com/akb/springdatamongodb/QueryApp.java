package com.akb.springdatamongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.akb.springdatamongodb.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;



public class QueryApp {

	public static void main(String[] args) {

		ApplicationContext ctx = 
                         new AnnotationConfigApplicationContext(SpringDataMongodbApplication.class);
		MongoOperations mongoOperation = 
                         (MongoOperations) ctx.getBean("mongoTemplate");



		List<User> users = Arrays.asList(new User[]{
				User.builder().ic("1001").name("sachin").age(10).build(),
				User.builder().ic("1002").name("virat").age(11).build(),
				User.builder().ic("1003").name("saurav").age(12).build(),
				User.builder().ic("1004").name("rahul").age(13).build(),
				User.builder().ic("1005").name("kumble").age(14).build()
		});
		mongoOperation.insert(users, User.class);

		System.out.println("Case 1 - find with BasicQuery example");

		BasicQuery query1 = new BasicQuery("{ age : { $lt : 40 }, name : 'cat' }");
		User userTest1 = mongoOperation.findOne(query1, User.class);

		System.out.println("query1 - " + query1.toString());
		System.out.println("userTest1 - " + userTest1);

		System.out.println("\nCase 2 - find example");

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("name").is("dog").and("age").is(40));

		User userTest2 = mongoOperation.findOne(query2, User.class);
		System.out.println("query2 - " + query2.toString());
		System.out.println("userTest2 - " + userTest2);

		System.out.println("\nCase 3 - find list $inc example");

		List<Integer> listOfAge = new ArrayList<Integer>();
		listOfAge.add(10);
		listOfAge.add(30);
		listOfAge.add(40);

		Query query3 = new Query();
		query3.addCriteria(Criteria.where("age").in(listOfAge));

		List<User> userTest3 = mongoOperation.find(query3, User.class);
		System.out.println("query3 - " + query3.toString());

		for (User user : userTest3) {
			System.out.println("userTest3 - " + user);
		}

		System.out.println("\nCase 4 - find list $and $lt, $gt example");

		Query query4 = new Query();

		// it hits error
		// query4.addCriteria(Criteria.where("age").lt(40).and("age").gt(10));

		query4.addCriteria(
                   Criteria.where("age").exists(true).andOperator(
		         Criteria.where("age").gt(10),
                         Criteria.where("age").lt(40)
	            )
                );

		List<User> userTest4 = mongoOperation.find(query4, User.class);
		System.out.println("query4 - " + query4.toString());

		for (User user : userTest4) {
			System.out.println("userTest4 - " + user);
		}

		System.out.println("\nCase 5 - find list and sorting example");
		Query query5 = new Query();
		query5.addCriteria(Criteria.where("age").gte(30));
		query5.with(new Sort(Sort.Direction.DESC, "age"));

		List<User> userTest5 = mongoOperation.find(query5, User.class);
		System.out.println("query5 - " + query5.toString());

		for (User user : userTest5) {
			System.out.println("userTest5 - " + user);
		}

		System.out.println("\nCase 6 - find by regex example");
		Query query6 = new Query();
		query6.addCriteria(Criteria.where("name").regex("D.*G", "i"));

		List<User> userTest6 = mongoOperation.find(query6, User.class);
		System.out.println("query6 - " + query6.toString());

		for (User user : userTest6) {
			System.out.println("userTest6 - " + user);
		}

		//mongoOperation.dropCollection(User.class);

	}

}