package threeoceans.fitness.ru.accounts.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientAccount client;


    @Column(name="discipline")
    private String discipline;

    @Column(name="number_of_workouts")
    private Integer numOfWorkouts;

    @Column(name="expired")
    private LocalDate expired;


}
