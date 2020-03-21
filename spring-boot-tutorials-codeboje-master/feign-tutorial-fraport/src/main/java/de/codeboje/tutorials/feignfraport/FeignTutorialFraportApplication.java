package de.codeboje.tutorials.feignfraport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@EnableFeignClients
public class FeignTutorialFraportApplication implements CommandLineRunner {

	@Autowired
	private FlightClient flightClient;

	@Bean
	AuthInterceptor authFeign() {
		return new AuthInterceptor();
	}

	class AuthInterceptor implements RequestInterceptor {

		@Override
		public void apply(RequestTemplate template) {
			template.header("Authorization", "Bearer e4196986-9f3d-308e-b656-dadd08a4dd36");

		}

	}

	public static void main(String[] args) {
		//initCerts(); can be done progrmatically or add in cacerts of jdk
		new SpringApplicationBuilder(FeignTutorialFraportApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	private static void initCerts() {
		try {
			ClassPathResource classPathResource = new ClassPathResource("/keystore.jks");
			InputStream inputStream = classPathResource.getInputStream();
			File keyStoreFile = File.createTempFile("keystore", ".jks");
			filesCopy(inputStream, keyStoreFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void filesCopy(InputStream inputStream, File keyStoreFile) throws IOException {

		try{
			Files.copy(inputStream, keyStoreFile.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			System.setProperty("javax.net.ssl.trustStore", keyStoreFile.getPath());
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			inputStream.close();
		}
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		To execute this login to
		https://developer.fraport.de/store/
		ajitku ---Aj..@1...
		Generate API keys for prod and sandbox and then run this
		 */
		List<String> dest = Arrays.asList(args);


		for (FlightWrapper flightWrapper : flightClient.getDepartingFlights()) {
			Flight flight = flightWrapper.getFlight();

			//if (dest.contains(flight.getArrivalAirport())) {
				System.out.println(String.format("%s\tto\t%s\ton\t%s\twith\t%s", flight.getDepartureAirport(),
						flight.getArrivalAirport(), flight.getDeparture().getScheduled(), flight.getOperatingAirline().getName()));
			//}
		}
		List<String> arrivalAirports = new ArrayList<>();
		Optional.of(flightClient.getDepartingFlights())
				.map(obj -> {
					obj.stream().forEach(airpt -> {
						arrivalAirports.add(airpt.getFlight().getArrivalAirport());
					});
					return arrivalAirports;
				}).orElseThrow(RuntimeException::new);

	}
}
