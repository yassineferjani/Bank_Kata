package org.example.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private Long id;
    private TransactionType transactionType;
    private Double amount;
    private Date dateTransaction;
    private Double oldBalance;
    private Double newBalance;
    private Long accountId;

}
