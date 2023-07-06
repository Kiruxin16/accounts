package threeoceans.fitness.ru.accounts.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "client_accounts")
@Data
@NoArgsConstructor
public class ClientAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "keypass")
    private  String keypass;

    @Column(name ="username")
    private  String username;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private  String email;

    @OneToMany(mappedBy = "client",cascade = CascadeType.PERSIST)
    private List<Ticket> tickets;


    @CreationTimestamp
    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name ="updated_at" )
    private LocalDateTime updatedAt;







}