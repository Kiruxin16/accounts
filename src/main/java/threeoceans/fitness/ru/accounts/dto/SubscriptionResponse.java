package threeoceans.fitness.ru.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class SubscriptionResponse {

    private  Long id;

    private String disciplineName;

    private Integer numOfWorkouts;

    private LocalDate expired;

}
