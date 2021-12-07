package Bank_Java;
import java.util.*;
import java.io.*;

public class Bank {
    private ArrayList<Account> Accounts;
    private String Name;
    private int AccountNumberLength;
    private float minBalance;
    private float dailyLimit;

    public Bank(String name,int acnl,float minBalance,float dailyLimit) throws Exception{
        this.Accounts = new ArrayList<Account>();
        this.Name = name;
        if(acnl >= 10){
            throw new Exception("Account number lenght cannot exceed 9 digits.");
        }
        this.AccountNumberLength = acnl;
        this.minBalance = minBalance;
        this.dailyLimit = dailyLimit;
        System.out.println("Welcome to "+name);
    }

    private int getRandom(){
        String account = "";
        Random rand = new Random();
        for(int i=0;i<this.AccountNumberLength;i++){
            account += rand.nextInt(10);
        }
        return Integer.parseInt(account);
    }

    public Account findAccount(int acc){
        Account a = null;
        for(int i=0;i<Accounts.size();i++){
            if(Accounts.get(i).getAccountNumber()==acc){
                a = Accounts.get(i);
            }
        }
        return a;
    }

    public Account getAccount(String name,String key){
        int acn = this.getRandom();
        Account a = new Account(acn,key,name,this.Name,this.minBalance,this);
        System.out.println("Name "+name+" Acc. No.: "+acn);
        return a;
    }

    public void AddAccount(Account a){
        this.Accounts.add(a);
    }

    public void NewAccount(){
        System.out.println("Create New Account");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name : ");
        String name = sc.nextLine();
        int random = this.getRandom();
        
        System.out.println("Enter a password : ");
        Console c = System.console();
        char[] ch = c.readPassword();
        String key = new String(ch);
        Account a = new Account(random,key,name,this.Name,this.minBalance,this);
        System.out.println("Name "+name+" Acc. No.: "+random);
        this.Accounts.add(a);
    }

    public float minBalance(){
        return this.minBalance;
    }

    public float dailyLimit(){
        return this.dailyLimit;
    }

    private void Login(int acc) throws Exception{
        Account a = null;
        if(this.Accounts.size()==0){
            throw new Exception("No account to login");
        }
        for(int i=0;i<this.Accounts.size();i++){
            if(this.Accounts.get(i).getAccountNumber()==acc){
                a = this.Accounts.get(i);
            }
        }
        a.VerifyAndViewOptins();
    }

    public void GetInLoop() throws Exception{
        Scanner sc = new Scanner(System.in);
        Printer.Print("1.Login 2.New Account 3.Exit", Printer.TEXT_YELLOW);
        int rec = sc.nextInt();
        switch(rec){
            case 1:
                System.out.println("Enter your account number : ");
                int acc = sc.nextInt();
                this.Login(acc);
                GetInLoop();
            break;

            case 2:
                System.out.println("New Account");
                this.NewAccount();
                GetInLoop();
            break;

            case 3:
                System.out.print("Thank You !");
                return;

            default:
                Printer.Print("Wrong choice", Printer.TEXT_RED);
                GetInLoop();
            break;
        }
    }
}
