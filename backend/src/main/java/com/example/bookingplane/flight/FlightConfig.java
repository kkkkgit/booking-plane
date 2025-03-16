package com.example.bookingplane.flight;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.Calendar.APRIL;

@Configuration
public class FlightConfig {

    @Bean
    CommandLineRunner commandLineRunner(FlightRepository repository) {
        return args -> {
            Flight B505 = new Flight(
                    "Viin",
                    LocalDate.of(2025, APRIL, 5),
                    LocalTime.of(6, 20),
                    LocalDate.of(2025, APRIL, 5),
                    LocalTime.of(9, 55),
                    150
            );

            Flight A206 = new Flight(
                    "Paris",
                    LocalDate.of(2025, APRIL, 3),
                    LocalTime.of(5, 30),
                    LocalDate.of(2025, APRIL, 3),
                    LocalTime.of(7, 55),
                    180
            );

            repository.saveAll(List.of(B505, A206));
        };
    }
}
