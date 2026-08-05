package demo.parking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ParkingLotSpringApplication {

    public static void main(String[] args) {
        System.out.println("Parking Lot main inside");
        SpringApplication.run(ParkingLotSpringApplication.class, args);
        System.out.println("Parking Lot main after");
    }

    @Bean
    CommandLineRunner init(ApplicationContext ctx) {
        return args -> {
            System.out.println("Parking Lot CommandLineRunner inside");
        };
    }

}
