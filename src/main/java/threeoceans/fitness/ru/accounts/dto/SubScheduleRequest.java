package threeoceans.fitness.ru.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubScheduleRequest {
    private String login;
    private String discipline;
}
