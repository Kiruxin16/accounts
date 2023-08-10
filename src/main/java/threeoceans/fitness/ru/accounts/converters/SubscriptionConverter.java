package threeoceans.fitness.ru.accounts.converters;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import threeoceans.fitness.ru.accounts.dto.SubscriptionResponse;
import threeoceans.fitness.ru.accounts.entities.Subscription;

@Component
@Data
@RequiredArgsConstructor
public class SubscriptionConverter {


    public SubscriptionResponse entityToSubscriptionResponse(Subscription subscription){
        SubscriptionResponse subscriptionDto = SubscriptionResponse.builder()
                .id(subscription.getId())
                .disciplineName(subscription.getDiscipline())
                .numOfWorkouts(subscription.getNumOfWorkouts())
                .expired(subscription.getExpired())
                .build();

        return subscriptionDto;
    }
}
