package kafka.demo.service;

public class InvalidBalanceException extends Exception{

    public InvalidBalanceException(String msg){
        super(msg);
    }

}
