package org.example.entities;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rib;
    private double balance;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> accountTransactions;
}
