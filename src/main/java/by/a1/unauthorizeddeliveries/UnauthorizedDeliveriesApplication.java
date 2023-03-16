package by.a1.unauthorizeddeliveries;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UnauthorizedDeliveriesApplication {
    public static void main(String[] args) {
        SpringApplication.run(UnauthorizedDeliveriesApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
