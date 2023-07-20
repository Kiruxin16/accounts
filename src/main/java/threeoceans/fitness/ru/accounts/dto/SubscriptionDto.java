package threeoceans.fitness.ru.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import threeoceans.fitness.ru.accounts.entities.ClientAccount;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    private Long id;

    private String client;

    private String discipline;

    private LocalDate expired;

    private Integer numOfWorkouts;

}
