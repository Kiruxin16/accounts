package threeoceans.fitness.ru.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class SubscriptionToProductRequest {

    private String login;

    private String discipline;

    private LocalDate expired;

    private Integer numOfWorkouts;


}
