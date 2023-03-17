package by.a1.unauthorizeddeliveries;

import by.a1.unauthorizeddeliveries.exception.ServiceException;
import by.a1.unauthorizeddeliveries.service.ParseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class UnauthorizedDeliveriesApplication {
    private final Environment env;
    private final List<ParseService> parseServices;
    public static void main(String[] args) {
        SpringApplication.run(UnauthorizedDeliveriesApplication.class, args);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void initialParse() throws IOException {
        if(Arrays.asList(env.getActiveProfiles())
                .contains("test")) {
            return;
        }
        for (ParseService parseService : parseServices) {
            parseService.parseToDB();
        }
    }
}
