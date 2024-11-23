package elhou.salima.comptecqrseventsourcing.commoApi.dtos;

import lombok.Data;

@Data
public class CreditAccountRequestDTO {
    private String  accountId;
    private String currency;
    private double initialBalance;
}
