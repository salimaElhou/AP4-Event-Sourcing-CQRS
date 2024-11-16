package elhou.salima.comptecqrseventsourcing.commoApi.events;

import elhou.salima.comptecqrseventsourcing.commoApi.enums.AccountState;
import lombok.Getter;

public class AccountActivatedEvent extends BaseEvent<String> {
    @Getter
    private AccountState status;

    public AccountActivatedEvent(String id,AccountState status) {
        super(id);
        this.status=status;
    }
}
