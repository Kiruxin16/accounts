package threeoceans.fitness.ru.accounts.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import threeoceans.fitness.ru.accounts.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
}
