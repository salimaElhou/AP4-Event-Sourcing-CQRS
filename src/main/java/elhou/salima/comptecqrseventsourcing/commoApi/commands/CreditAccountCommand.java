package elhou.salima.comptecqrseventsourcing.commoApi.commands;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


// la 1ere CMD
public class CreditAccountCommand extends BaseCommand<String> {
    //pour cree acc
    @Getter
    private double amount;
    @Getter
    private  String currency;

    public CreditAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
