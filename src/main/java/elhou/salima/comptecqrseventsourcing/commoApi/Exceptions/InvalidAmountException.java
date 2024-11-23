package elhou.salima.comptecqrseventsourcing.commoApi.Exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message){
        super(message);
    }
}
