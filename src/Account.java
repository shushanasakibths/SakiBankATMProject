public class Account {
    private static int totalAccounts = 0;
    private static double totalBalance = 0;
    private String name;
    private double balance;
    private Customer owner;
    public Account(String name, double initialBalance, Customer owner) {
        this.name = name;
        this.owner = owner;
        this.balance = initialBalance;
        totalBalance += initialBalance;
    }
    public String getAccountType() {
        return name;
    }
    public double getBalance() {
        return balance;
    }
    public String getBalanceSummary() {
        return name + ": $" + balance;
    }
    public void deposit(double amount) {
        balance += amount;
        totalBalance += amount;
    }
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            totalBalance -= amount;
            return true;
        } else {
            return false;
        }
    }
}
