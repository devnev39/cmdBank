package Bank_Java;
import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account {
    private int ACN;
    private String Key;
    private String Name;
    private float CurrentBalance;
    private String HomeBranch;
    private float withdrawLimit;
    private int tries;
    private boolean isLoggedIn;
    private ArrayList<Transaction> Transactions;
    private Bank bank;


    public Account(int acn,String key,String name,String homebranch,float defult,Bank bank){
        this.ACN = acn;
        this.Name = name;
        this.HomeBranch = homebranch;
        this.CurrentBalance = 0.0f;
        this.CurrentBalance = defult;
        this.Key = key;
        this.tries = 0;
        Transactions = new ArrayList<Transaction>();
        this.bank = bank;
    }

    private int Deposit(){
        Scanner sc = new Scanner(System.in);
        Printer.Print("Desposit", Printer.TEXT_GREEN);
        System.out.println("Enter the amt. you want to deposit : ");
        int rec = sc.nextInt();
        if(rec<=0){
            Printer.Print("Enter valid amt. !", Printer.TEXT_RED);
            return 0;
        }
        this.CurrentBalance += rec;
        this.withdrawLimit = this.CurrentBalance - this.bank.minBalance();
        CheckBalance();
        return 1;
    }

    private int WithDraw(){
        Scanner sc = new Scanner(System.in);
        Printer.Print("Withdraw", Printer.TEXT_GREEN);
        System.out.println("Enter the amt. you want to withdraw : ");
        int rec = sc.nextInt();
        // bias = 500
        // if currentbalance - rec <= 500
        if(this.CurrentBalance-rec<=this.bank.minBalance()){
            System.out.println("Your withdrawl limit is : "+this.withdrawLimit+"\nCannot exceed that limit !");
            return 0;
        }
        if(rec > this.bank.dailyLimit()){
            Printer.Print("Cannot withdraw more than 10,000", Printer.TEXT_RED);
            return 0;
        }
        this.CurrentBalance -= rec;
        this.withdrawLimit = this.CurrentBalance - this.bank.minBalance();
        Transactions.add(new Transaction(this, null, rec, this.getCurrentDateTime()));
        CheckBalance();
        return 1;
    }

    private int CheckBalance(){
        Printer.Print("Your current balance is : "+this.CurrentBalance, Printer.TEXT_GREEN);
        return 1;
    }
    
    private void getInfo(){
        System.out.println("Account No. : "+ACN);
        System.out.println("Name : "+Name);
        System.out.println("Current Balance : "+CurrentBalance);
        System.out.println("Withdrawl Limit : "+withdrawLimit);
        System.out.println("Bank Name : "+HomeBranch);
    }

    private void initTransaction(){
        Scanner sc = new Scanner(System.in);
        Printer.Print("Transaction", Printer.TEXT_GREEN);
        System.out.println("Enter receiver's account number : ");
        int acc = sc.nextInt();
        if(acc==this.ACN){
            Printer.Print("Sakali Jast Zali Ka BC", Printer.TEXT_RED);
            return;
        }
        Account a = this.bank.findAccount(acc);
        if(a==null){
            Printer.Print("Account not found !", Printer.TEXT_RED);
            return;
        }
        System.out.println("Enter amount to send : ");
        float amt = sc.nextFloat();
        if(this.withdrawLimit>amt){
            a.CurrentBalance += amt;
            this.CurrentBalance -= amt;
            this.withdrawLimit = this.CurrentBalance - this.bank.minBalance();

            Transactions.add(new Transaction(this,a,amt,this.getCurrentDateTime()));
            a.Transactions.add(new Transaction(this, a, amt, this.getCurrentDateTime()));
            Printer.Print(Transactions.get(Transactions.size()-1).toString(), Printer.TEXT_GREEN);
            System.out.println("Success !");
        }else{
            System.out.println("Cannot exceed the withdrawl limit on your account !");
        }
    }

    private void viewTransactions(){
        if(Transactions.isEmpty()){
            Printer.Print("No transactions for AC : "+ACN, Printer.TEXT_RED);
            ViewOptions();
        }
        Printer.Print("Transactions for AC : "+ACN, Printer.TEXT_GREEN);
        for(int i=0;i<Transactions.size();i++){
            Printer.Print(Transactions.get(i).toString(), Printer.TEXT_CYAN);
        }
        ViewOptions();
    }

    private void ViewOptions(){
        if(!isLoggedIn){
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose : 1.Deposit  2.Withdraw  3.Check Balance  4.Get Info \n5.Init Transaction  6.List Transactions  7.Log Out");
        int rec = sc.nextInt();
        switch(rec){
            case 1:
                if(this.Deposit()==1){
                    Printer.Print("Success !", Printer.TEXT_GREEN);
                }
                else{
                    Printer.Print("Failed !", Printer.TEXT_RED);
                }
                ViewOptions();
            break;

            case 2:
                if(this.WithDraw()==1){
                    Printer.Print("Success !", Printer.TEXT_GREEN);
                }
                else{
                    Printer.Print("Failed !", Printer.TEXT_RED);
                }
                ViewOptions();
            break;

            case 3:
                if(this.CheckBalance()==1){
                    Printer.Print("Success !", Printer.TEXT_GREEN);
                }
                else{
                    Printer.Print("Failed !", Printer.TEXT_RED);
                }
                ViewOptions();
            break;
            

            case 4:
                this.getInfo();
                ViewOptions();
            break;

            case 5:
                this.initTransaction();
                ViewOptions();
            break;

            case 6:
                this.viewTransactions();
            break;

            case 7:
                this.isLoggedIn = false;
                ViewOptions();
            break;

            default:
                System.out.println("Invalid Choice !");
                this.isLoggedIn = false;
            break;
        }
    }

    public int getAccountNumber(){
        return this.ACN;
    }

    public void VerifyAndViewOptins() throws Exception{
        System.out.println("Enter your password : ");
        Console c = System.console();
        char[] ch = c.readPassword();
        String key = new String(ch);

        if(key.equals(this.Key)){
            this.isLoggedIn = true;
            this.ViewOptions();
        }else{    
            tries+=1;
            if(tries==3){
                this.isLoggedIn = false;
                throw new Exception("Max no. of chances reached ! Exiting.....");
            }
            System.out.println("Try again ! Chances left : "+(3-tries));
            VerifyAndViewOptins();
        }
    }

    public String getCurrentDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        return dt.format(formatter);
    }

    @Override
    public String toString() {
        String str = this.Name+" [ "+this.ACN+" ]";
        return str;
    }
}

// password -> hash(password) -> store
