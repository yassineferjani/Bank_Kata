package org.example.port;

import org.example.data.AccountDTO;

public interface CreditCardServicePort {
    AccountDTO withdrawal(Long rib, double amount);
    AccountDTO deposit(Long rib, double amount);
    void deleteById(Long id);
}
