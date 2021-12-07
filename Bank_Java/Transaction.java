package Bank_Java;
import java.io.*;
import java.util.*;

public class Transaction {
    private Account Sender;
    private Account Reciever;
    private float Amount;
    private String DateTime;

    public Transaction(Account Sender,Account Receiver,float amt,String datetime){
        this.Sender = Sender;
        this.Reciever = Receiver;
        this.Amount = amt;
        this.DateTime = datetime;
    }

    @Override
    public String toString() {
        String str = "From : "+this.Sender.toString()+" To : "+this.Reciever.toString()+" Amt : "+this.Amount+" at "+this.DateTime;
        return str;
    }
}
