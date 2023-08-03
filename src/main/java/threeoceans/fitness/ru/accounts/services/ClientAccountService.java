package threeoceans.fitness.ru.accounts.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threeoceans.fitness.ru.accounts.converters.ClientInfoConverter;
import threeoceans.fitness.ru.accounts.converters.SubscriptionConverter;
import threeoceans.fitness.ru.accounts.dto.*;
import threeoceans.fitness.ru.accounts.entities.ClientAccount;
import threeoceans.fitness.ru.accounts.entities.Subscription;
import threeoceans.fitness.ru.accounts.repositories.ClientAccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
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
            resultAccount.setLogin(infoRequest.getLogin());
            resultAccount.setKeypass(String.valueOf(Math.abs(infoRequest.getLogin().hashCode())));
        } else {
            resultAccount=account.get();
        }
        resultAccount.setUsername(infoRequest.getUsername());
        resultAccount.setPhone(infoRequest.getPhone());
        resultAccount.setEmail(infoRequest.getEmail());
        clientAccountRepository.save(resultAccount);
    }

    @Transactional
    public void addSubscription(SubscriptionRequest subRequest){
        ClientAccount client = clientAccountRepository.findByLogin(subRequest.getClient()).get();
        Subscription sub;
        Optional<Subscription> subOpt=client.getSubscriptions().stream()
                .filter(s ->subRequest.getDiscipline().equals(s.getDiscipline())).findFirst();
        if(subOpt.isPresent()){
            sub=subOpt.get();
            sub.setNumOfWorkouts(sub.getNumOfWorkouts()+subRequest.getNumOfWorkouts());
            sub.setExpired(subRequest.getExpired());
        }else{
            sub=new Subscription();
            sub.setReserved(0);
            sub.setClient(client);
            sub.setNumOfWorkouts(subRequest.getNumOfWorkouts());
            sub.setDiscipline(subRequest.getDiscipline());
            sub.setExpired(subRequest.getExpired());
        }

        subscriptionService.save(sub);
    }

    public Optional<Subscription> getSubscription(String login, String discipline) {
        ClientAccount client = clientAccountRepository.findByLogin(login).get();
        return client.getSubscriptions().stream()
                .filter(s -> discipline.equals(s.getDiscipline())).findFirst();
    }

    @Transactional
    public SubScheduleResponse subscribeAtEvent(String login, String discipline) throws Exception{
        ClientAccount client =clientAccountRepository.findByLogin(login).get();
        Subscription sub = client.getSubscriptions().stream()
                .filter(s -> discipline.equals(s.getDiscipline())).findFirst()
                .orElseThrow(()-> new Exception() );
        sub.setReserved(sub.getReserved()+1);
        if (sub.getNumOfWorkouts()< sub.getReserved()){
            throw new Exception(); //количество тренировок не может быть меньше количества зарезервированных
        }

        subscriptionService.save(sub);
        return new SubScheduleResponse(sub.getId(),client.getUsername());
    }

    @Transactional
    public void changeNumOfWorkouts(String login, String discipline, int delta) throws Exception{
        Subscription sub = getSubscription(login,discipline).orElseThrow(()-> new Exception() );
        sub.setNumOfWorkouts(sub.getNumOfWorkouts()+delta);
        if (sub.getNumOfWorkouts()< sub.getReserved()){
            throw new Exception(); //количество тренировок не может быть меньше количества зарезервированных

        }
        if (sub.getNumOfWorkouts()<0){
            throw new Exception(); //у пользователя нет столько тренировок
        }

        if (sub.getNumOfWorkouts()==0){
            subscriptionService.deleteById(sub.getId());
        }else{
            subscriptionService.save(sub);
        }


    }

    public void unsubscribeAtEvent(Long subId){
        Subscription sub = subscriptionService.findById(subId);
        sub.setReserved(sub.getReserved()-1);
        subscriptionService.save(sub);

    }

    public void  confirmWorkout(Long subId){
        Subscription sub = subscriptionService.findById(subId);
        sub.setReserved(sub.getReserved()-1);
        sub.setNumOfWorkouts(sub.getNumOfWorkouts()-1);
        if(sub.getNumOfWorkouts()==0){
            subscriptionService.deleteById(subId);
        }else{
            subscriptionService.save(sub);
        }
    }



}
