package elhou.salima.comptecqrseventsourcing.query.service;

import elhou.salima.comptecqrseventsourcing.commoApi.events.AccountDebitedEvent;
import elhou.salima.comptecqrseventsourcing.commoApi.queries.GetAccountByIdQuery;
import elhou.salima.comptecqrseventsourcing.commoApi.queries.GetAllAccountsQuery;
import elhou.salima.comptecqrseventsourcing.query.entities.Account;
import elhou.salima.comptecqrseventsourcing.query.entities.Operation;
import elhou.salima.comptecqrseventsourcing.query.repositories.AccountRepository;
import elhou.salima.comptecqrseventsourcing.query.repositories.OperationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import elhou.salima.comptecqrseventsourcing.commoApi.enums.OperationType;
import elhou.salima.comptecqrseventsourcing.commoApi.events.AccountActivatedEvent;
import elhou.salima.comptecqrseventsourcing.commoApi.events.AccountCreatedEvent;
import elhou.salima.comptecqrseventsourcing.commoApi.events.AccountCreditedEvent;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("********************************************");
        log.info("accountCreatedEvent: received!");
        Account account=new Account();
        account.setId(event.getId());
        account.setAmount(event.getAmount());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("********************************************");
        log.info("accountActivatedEvent received!");
        Account account=accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);




    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("********************************************");
        log.info("accountDebitedEvent received!");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation=new Operation();
        operation.setOperationType(OperationType.DEBIT);
        operation.setAmount(event.getBalance());
        operation.setCreatedAt(event.getDate());
        operation.setAccount(account);
        operationRepository.save(operation);
        operationRepository.flush();
        account.setAmount(account.getAmount()-event.getBalance());
        accountRepository.save(account);
        accountRepository.flush();

    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("********************************************");
        log.info("accountDebitedEvent received!");
        Account account=accountRepository.findById(event.getId()).get();
        Operation operation=new Operation();
        operation.setOperationType(OperationType.CREDIT);
        operation.setAmount(event.getBalance());
        operation.setCreatedAt(event.getDate());
        operation.setAccount(account);
        operationRepository.save(operation);
        operationRepository.flush();
        account.setAmount(account.getAmount()+event.getBalance());
        accountRepository.save(account);
        accountRepository.flush();

    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }

}