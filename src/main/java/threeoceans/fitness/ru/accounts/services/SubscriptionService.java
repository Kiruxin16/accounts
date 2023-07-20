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


    public Optional<Subscription> getSubscriptionByClientId(Long id){
        subscriptionRepository.findById()
    }

}
