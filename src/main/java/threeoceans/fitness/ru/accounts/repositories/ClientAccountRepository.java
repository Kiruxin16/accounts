package threeoceans.fitness.ru.accounts.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import threeoceans.fitness.ru.accounts.entities.ClientAccount;

import java.util.Optional;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount,Long> {

    public Optional<ClientAccount> findByLogin(String login);

}
