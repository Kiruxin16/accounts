package threeoceans.fitness.ru.accounts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threeoceans.fitness.ru.accounts.converters.ClientInfoConverter;
import threeoceans.fitness.ru.accounts.converters.SubscriptionConverter;
import threeoceans.fitness.ru.accounts.dto.*;
import threeoceans.fitness.ru.accounts.entities.ClientAccount;
import threeoceans.fitness.ru.accounts.repositories.ClientAccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientAccountService {


    private final SubscriptionService subscriptionService;
    private final ClientAccountRepository clientAccountRepository;
    private final ClientInfoConverter clientInfoConverter;
    private final SubscriptionConverter subscriptionConverter;

    public ClientInfoResponse getClientInfo(String login){
        return clientAccountRepository.findByLogin(login).map(clientInfoConverter::entityToClientInfo).get();
    }

    @Transactional
    public List<SubscriptionResponse> getClientSubscriptions(String login){
        ClientAccount account = clientAccountRepository.findByLogin(login).get();
        return account.getSubscriptions().stream().map(subscriptionConverter::entityToSubscriptionResponse).collect(Collectors.toList());
    }

    public void createOrUpdateAccount(ClientInfoRequest infoRequest){
        Optional<ClientAccount> account = clientAccountRepository.findByLogin(infoRequest.getLogin());
        ClientAccount resultAccount;
        if (account.isEmpty()){
            resultAccount = new ClientAccount();
            Random random = new Random();
            resultAccount.setLogin(infoRequest.getLogin());
            resultAccount.setKeypass(String.valueOf(random.nextDouble(8999999999d))+1000000000d);
        } else {
            resultAccount=account.get();
            resultAccount.setUsername(infoRequest.getUsername());
            resultAccount.setPhone(infoRequest.getPhone());
            resultAccount.setEmail(infoRequest.getEmail());
        }
        clientAccountRepository.save(resultAccount);
    }

    public void addSubscription(SubscriptionRequest subRequest){
        ClientAccount client = clientAccountRepository.findByLogin(subRequest.getClient())
    }


}
