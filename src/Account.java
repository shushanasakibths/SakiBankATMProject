public class Account {
    private static int totalAccounts = 0;
    private static double totalBalance = 0;
    private int accountNumber;
    private String name;
    private double balance;
    private Customer owner;
    private static int accountTransactionCount = 0;
    public Account(String name, double initialBalance, Customer owner) {
        this.accountNumber = ++totalAccounts;
        this.name = name;
        this.owner = owner;
        this.balance = initialBalance;
        totalBalance += initialBalance;
    }
    public static int getTotalAccounts() {
        return totalAccounts;
    }
    public static double getTotalBalance() {
        return totalBalance;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public String getAccountHolder() {
        return name;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
        totalBalance += amount;
        accountTransactionCount++;
    }
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            totalBalance -= amount;
            accountTransactionCount++;
            return true;
        } else {
            System.out.println("Insufficient funds for withdrawal.");
            return false;
        }
    }
    public String getBalanceSummary() {
        return name + ": $" + balance;
    }
    public int getAccountTransactionCount() {
        return accountTransactionCount;
    }
}
