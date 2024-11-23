package elhou.salima.comptecqrseventsourcing.commoApi.events;

import lombok.Getter;

import java.util.Date;

public class AccountCreditedEvent extends BaseEvent<String>{


    @Getter private double balance;
    @Getter private String currency;
    @Getter private Date date;
    public AccountCreditedEvent(String id, double balance, String currency, Date date) {
        super(id);
        this.balance = balance;
        this.currency = currency;
        this.date = date;
    }
}
