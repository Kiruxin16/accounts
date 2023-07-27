package threeoceans.fitness.ru.accounts.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threeoceans.fitness.ru.accounts.entities.Subscription;
import threeoceans.fitness.ru.accounts.repositories.SubscriptionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;



    public void save(Subscription sub){
        subscriptionRepository.save(sub);
    }

    public Subscription findById(Long id){
        return subscriptionRepository.findById(id).get();
    }

    public void deleteById(Long id){
        subscriptionRepository.deleteById(id);
    }

}
