package elhou.salima.comptecqrseventsourcing.commoApi.Exceptions;

public class InssuficientBalanceException extends RuntimeException {
    public InssuficientBalanceException(String message){
        super(message);
    }
}
