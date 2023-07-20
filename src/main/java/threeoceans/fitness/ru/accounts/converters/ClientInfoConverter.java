package threeoceans.fitness.ru.accounts.converters;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import threeoceans.fitness.ru.accounts.dto.ClientAccountDto;
import threeoceans.fitness.ru.accounts.dto.ClientInfoRequest;
import threeoceans.fitness.ru.accounts.dto.ClientInfoResponse;
import threeoceans.fitness.ru.accounts.entities.ClientAccount;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@Data
@RequiredArgsConstructor
public class ClientInfoConverter {

    private final SubscriptionConverter subscriptionConverter;

    public ClientInfoResponse entityToClientInfo(ClientAccount clientAccount){

        ClientInfoResponse accountDto=ClientInfoResponse.builder()
                .login(clientAccount.getLogin())
                .username(clientAccount.getUsername())
                .keypass(clientAccount.getKeypass())
                .email(clientAccount.getEmail())
                .phone(clientAccount.getPhone())
                .build();
        return accountDto;

    }


}
