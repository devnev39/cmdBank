package Bank_Java;

public class Main{
    public static void main(String[] args) {
        try {
            Bank bank = new Bank("Shreekar Bank Ltd", 7,500.0f,10000.0f);
            Account a1 = bank.getAccount("Abhi", "password@123");
            Account a2 = bank.getAccount("Mitesh", "local123");
            Account a3 = bank.getAccount("Prathamesh", "post444");

            bank.AddAccount(a1);
            bank.AddAccount(a2);
            bank.AddAccount(a3);

            bank.GetInLoop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}