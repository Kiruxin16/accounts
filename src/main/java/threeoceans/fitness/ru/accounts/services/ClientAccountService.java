package threeoceans.fitness.ru.accounts.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threeoceans.fitness.ru.accounts.converters.ClientInfoConverter;
import threeoceans.fitness.ru.accounts.converters.SubscriptionConverter;
import threeoceans.fitness.ru.accounts.dto.*;
import threeoceans.fitness.ru.accounts.entities.ClientAccount;
import threeoceans.fitness.ru.accounts.entities.Subscription;
import threeoceans.fitness.ru.accounts.exceptions.ReservationException;
import threeoceans.fitness.ru.accounts.exceptions.ResourceNotFoundException;
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
    public void addSubscription(SubscriptionToProductRequest subRequest){
        ClientAccount client = clientAccountRepository.findByLogin(subRequest.getLogin()).get();
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
    public ResponseEntity<?> subscribeAtEvent(String login, String discipline) {
        ClientAccount client =clientAccountRepository.findByLogin(login).get();
        Subscription sub = client.getSubscriptions().stream()
                .filter(s -> discipline.equals(s.getDiscipline())).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("У пользователя нет подходящего абонемента.") );

        if (sub.getNumOfWorkouts()<= sub.getReserved()){
            throw new ReservationException("количество доступных тренировок не может быть меньше количества зарезервированных.");
        }
        sub.setReserved(sub.getReserved()+1);

        subscriptionService.save(sub);
        return new ResponseEntity<>(new SubScheduleResponse(sub.getId(),client.getUsername()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> changeNumOfWorkouts(String login, String discipline, int delta){
        Subscription sub = getSubscription(login,discipline)
                .orElseThrow(()-> new ResourceNotFoundException("учетная запись не найдена. обратитесь в техподдерку")  );
        sub.setNumOfWorkouts(sub.getNumOfWorkouts()+delta);
        if (sub.getNumOfWorkouts()< sub.getReserved()){
            throw new ReservationException("количество тренировок не может быть меньше количества зарезервированных");
        }
        if (sub.getNumOfWorkouts()<0){
            throw new ResourceNotFoundException("у пользователя нет столько тренировок");
        }

        if (sub.getNumOfWorkouts()==0){
            subscriptionService.deleteById(sub.getId());
        }else{
            subscriptionService.save(sub);
        }

        return ResponseEntity.ok("изменено");
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
