package threeoceans.fitness.ru.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class SubscriptionRequest {

    private String client;

    private String discipline;

    private LocalDate expired;

    private Integer numOfWorkouts;


}
