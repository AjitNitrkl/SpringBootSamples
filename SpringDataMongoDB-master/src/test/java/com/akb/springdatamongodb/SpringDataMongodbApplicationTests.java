package com.akb.springdatamongodb;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.akb.springdatamongodb.entity.Applicant;
import com.akb.springdatamongodb.entity.UserTO;
import com.akb.springdatamongodb.repository.ApplicantCustomRepo;
import com.akb.springdatamongodb.repository.ApplicantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataMongodbApplicationTests {
	
	@Autowired
	ApplicantRepository applicationRepo;
	
	@Autowired
	ApplicantCustomRepo applicationCustomRepo;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void getApplicant() {
		List<Applicant> appList = applicationRepo.findAll();
		Optional<Applicant> other = Optional.empty();
		Optional<Applicant> app = Optional.ofNullable(applicationRepo.findById(appList.get(0)
				.getId()).get());
		appList.get(0).setId(null);
		Optional<Applicant> appl = Optional.ofNullable(applicationRepo
				.findById(appList.get(0).getId())).orElse(other);
		System.out.println(appl.isPresent());
		assertThat(app.get().isPrimary(), is(true));
	}

	@Test
	public void searchApplicantByLname(){
		//size determines the max number of elements to be fetched
		//page 1 determines the page number 1
		//here if there are 2 elemets then page 0 contains 1 element since size is 1
		//& page 1 contains 1 element
		Pageable pageable = PageRequest.of(0, 2, Sort.by("fname").ascending());
		applicationCustomRepo.customSearchLname("Behera",pageable)
				.get()
				.forEach(System.out::println);


	}
	
	@Test
	public void getApplicantsById() {
		//Pageable pageable = new PageRequest(1, 1); //new PageRequest(offset,limit)
		Page<Applicant> applicants = applicationCustomRepo
				.customSearch(Arrays.asList("6TDOAWM7IL","HI5YC1SDOZ"));
		applicants.get().forEach(x ->{
			System.out.println("Fetched SSN-"+x.getSsn());
		});
	}
	
	@Test
	public void getAllApplicantPaginated() throws ParseException {
		List<Applicant> appList = applicationCustomRepo.getAllApplicantPaginated(0, 3);
		System.out.println("Applicant Size " +appList.size());
		List<UserTO> appList1 = applicationRepo.getAllApplicantExcludeIncludeFields();
		System.out.println("Applicant  using @Query1 " );
		appList1.forEach(System.out::println);
		List<UserTO> appList2 = applicationRepo.getAllPrimaryApplicant();
		System.out.println("Applicant  using @Query2 ");
		appList2.forEach(System.out::println);
		//SimpleDateFormat sdf = new SimpleDateFormat("YYYY-dd-mm");
		//Date date = sdf.parse("1991-01-01");
		/* LocalDate way */
		LocalDate localDate = LocalDate.of(2000,01,01);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<UserTO> appList3 = applicationRepo.getEligibleApplicant(date);
		System.out.println("Applicant  using @Query3 ");
		appList3.forEach(System.out::println);
	}
	
	@Test
	public void getAllApplicantUsingRegex() {

		applicationCustomRepo
				.getAllApplicantUsingRegex()
				.forEach(System.out::println);

		Optional.of(applicationRepo.getApplicantByFnameStartingWith("R"))
				.map(applicants -> {
					System.out.println("Applicants Starting With S");
					applicants.forEach(System.out::println);
					return applicants;
				});

		applicationRepo
				.getApplicantByLnameEndingWith("d")
				.map(applicants -> {
					System.out.println("Lname Ending  With d");
					applicants.forEach(System.out::println);
					return applicants;
				}).ifPresent(app ->{
					if(app.size() == 0)
						throw new RuntimeException("Lname Ending with a is empty ");
				});

		/*System.out.println("Fetched fname "+appList2.get(0).getFname());
		System.out.println("Applicant Size using regex2 " +appList2.size());
		List<Applicant> appList3 = applicationRepo.getApplicantByLname("Dravid");
		System.out.println("Fetched fname "+appList3.get(0).getFname());
		System.out.println("Applicant Size using regex3 " +appList3.size());*/
	}

}
