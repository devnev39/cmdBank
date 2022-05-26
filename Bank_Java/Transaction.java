package Bank_Java;

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
        String str = "";
        if(this.Reciever==null){
            str = "From : "+this.Sender.toString()+"| To : Withdrawal (SELF) "+"| Amt : "+this.Amount+"| At "+this.DateTime;
        }else{
            str = "From : "+this.Sender.toString()+"| To : "+this.Reciever.toString()+"| Amt : "+this.Amount+"| At "+this.DateTime;
        }
        return str;
    }
}
