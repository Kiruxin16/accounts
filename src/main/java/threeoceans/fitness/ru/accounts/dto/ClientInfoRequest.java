package threeoceans.fitness.ru.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClientInfoRequest {

    private String login;

    private  String username;

    private String phone;

    private  String email;
}
