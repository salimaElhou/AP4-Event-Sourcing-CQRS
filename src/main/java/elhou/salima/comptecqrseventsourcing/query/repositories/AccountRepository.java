package elhou.salima.comptecqrseventsourcing.query.repositories;

import elhou.salima.comptecqrseventsourcing.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
