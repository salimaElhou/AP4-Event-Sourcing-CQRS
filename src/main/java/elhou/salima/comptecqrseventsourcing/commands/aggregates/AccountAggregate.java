package elhou.salima.comptecqrseventsourcing.commands.aggregates;

import elhou.salima.comptecqrseventsourcing.commoApi.commands.CreateAccountCommand;
import elhou.salima.comptecqrseventsourcing.commoApi.enums.AccountState;
import elhou.salima.comptecqrseventsourcing.commoApi.events.AccountActivatedEvent;
import elhou.salima.comptecqrseventsourcing.commoApi.events.AccountCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double amount;
    private String currency;
    private AccountState status;

    //obligatoire constructure sans param
    public AccountAggregate() {
    }


    //handler -> pour executer une cmd
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCmd) {
        if (createAccountCmd.getAmount()<0) throw new  RuntimeException("impossible to create an account with a null balance!");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCmd.getId(),
                createAccountCmd.getAmount(),
                createAccountCmd.getCurrency(),
                AccountState.CREATED
        ));

    }
//ON -> les changs dans l application
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        //les chanegements
        this.accountId=event.getId();
        this.amount =event.getAmount();
        this.currency=event.getCurrency();
        this.status=AccountState.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountState.ACTIVATED));
    }

    // changer etat de application
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }

}
