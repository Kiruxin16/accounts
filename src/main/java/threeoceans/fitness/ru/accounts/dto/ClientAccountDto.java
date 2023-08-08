package threeoceans.fitness.ru.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAccountDto {

    private Long id;

    private String login;

    private  String keypass;

    private  String username;

    private String phone;

    private  String email;

    private List<SubscriptionDto> subscriptions;

}
