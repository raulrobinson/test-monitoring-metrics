package co.com.telefonica.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The type App ws application.
 */
@EnableFeignClients
@SpringBootApplication
public class AppWsApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */	public static void main(String[] args) {
		SpringApplication.run(AppWsApplication.class, args);
	}
	
}
