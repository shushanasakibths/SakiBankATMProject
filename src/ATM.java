
import java.util.Scanner;
public class ATM {
    private Customer customer;
    private Account checkingsAccount;
    private Account savingsAaccount;
    private TransactionHistory transactionHistory;
    private Scanner scanner;

    public ATM() {
        this.transactionHistory = new TransactionHistory();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        welcomeUser();
        makeCustomer();
        promptPIN();
        mainMenu();
    }

    private void welcomeUser() {
        System.out.println("Welcome to the ATM!");
    }

    private void makeCustomer() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Choose a PIN: ");
        int pin = scanner.nextInt();
        scanner.nextLine();
        this.customer = new Customer(name, pin);
        checkingsAccount = new Account("Checking Account", 0, customer);
        savingsAaccount = new Account("Savings Account", 0, customer);
        System.out.println(ConsoleUtility.GREEN + "Customer created successfully." + ConsoleUtility.RESET);
    }

    private void promptPIN() {
        int enteredPIN;
        do {
            System.out.print("Enter your PIN: ");
            enteredPIN = scanner.nextInt();
            if (enteredPIN != customer.getPin()) {
                System.out.println(ConsoleUtility.RED + "Invalid PIN. Try again." + ConsoleUtility.RESET);
            }
        } while (enteredPIN != customer.getPin());
        System.out.println(ConsoleUtility.GREEN + "PIN verified. Access granted." + ConsoleUtility.RESET);
    }

    private Account askAccount() {
        System.out.println("Would you like to perform this action for your " + ConsoleUtility.BLUE + "(c)heckings" + ConsoleUtility.RESET + " or " + ConsoleUtility.GREEN + "(s)avings " + ConsoleUtility.RESET + "account?");
        String account = scanner.nextLine();
        if (account.toLowerCase().equals("c")) {
            return checkingsAccount;
        } else if (account.toLowerCase().equals("s")) {
            return savingsAaccount;
        } else {
            System.out.println(ConsoleUtility.RED + "Invalid choice. Please try again." + ConsoleUtility.RESET);
        }
        return checkingsAccount;
    }

    private void mainMenu() {
        boolean continueTransaction = true;
        while (continueTransaction) {
            try {
                Thread.sleep(2000);  // 2000 milliseconds, or 2 seconds
            } catch (Exception e) {
                System.out.println("error");
            }
            System.out.println();
            System.out.println("Main Menu:");
            System.out.println(ConsoleUtility.RED + "1." + ConsoleUtility.RESET + " Withdraw money");
            System.out.println(ConsoleUtility.LIGHT_RED + "2." + ConsoleUtility.RESET + " Deposit money");
            System.out.println(ConsoleUtility.YELLOW + "3." + ConsoleUtility.RESET + " Transfer money between accounts");
            System.out.println(ConsoleUtility.GREEN + "4." + ConsoleUtility.RESET + " Get account balances");
            System.out.println(ConsoleUtility.BLUE + "5." + ConsoleUtility.RESET + " Get transaction history");
            System.out.println(ConsoleUtility.LIGHT_PURPLE + "6." + ConsoleUtility.RESET + " Change PIN");
            System.out.println(ConsoleUtility.PURPLE + "7." + ConsoleUtility.RESET + " Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    performWithdrawal(askAccount());
                    System.out.println();
                    break;
                case 2:
                    performDeposit(askAccount());
                    System.out.println();
                    break;
                case 3:
                    performTransfer();
                    System.out.println();
                    break;
                case 4:
                    getAccountBalances();
                    System.out.println();
                    break;
                case 5:
                    System.out.println(transactionHistory.getFormattedHistory());
                    System.out.println();
                    break;
                case 6:
                    changePIN();
                    System.out.println();
                    break;
                case 7:
                    exitATM();
                    continueTransaction = false;
                    break;
                default:
                    System.out.println(ConsoleUtility.RED + "Invalid choice. Please try again." + ConsoleUtility.RESET);
            }
            if (continueTransaction) {
                System.out.print("Do you want to do anything else? (" + ConsoleUtility.GREEN + "yes" + ConsoleUtility.RESET + "/ " + ConsoleUtility.RED + "no" + ConsoleUtility.RESET + "): ");
                String response = scanner.next().toLowerCase();
                if (!response.equals("yes")) {
                    exitATM();
                    continueTransaction = false;
                } else {
                    System.out.println("Enter pin to continue: ");
                    promptPIN();
                }
            }
        }
    }

    private void exitATM() {
        System.out.println("Thank you for your patronage. Goodbye.");
        System.exit(0);
    }

    private void changePIN() {
        System.out.println("What would you like your new PIN to be? ");
        int newPin = scanner.nextInt();
        if (newPin == customer.getPin()) {
            System.out.println("That is the same PIN.");
        } else if (newPin != customer.getPin()) {
            customer.setPin(newPin);
            transactionHistory.addSecurityTransaction("PIN changed", true);
            System.out.print(ConsoleUtility.GREEN + "PIN change successful!" + ConsoleUtility.RESET);
        }
    }

    private void getAccountBalances() {
        System.out.println(checkingsAccount.getBalanceSummary());
        System.out.println(savingsAaccount.getBalanceSummary());
    }

    private void displayTransactionReceipt(String action, boolean successful, double balance) {
        String transactionType = (balance == 0) ? "Security Transaction" : "Account Transaction";
        String status = (successful) ? "Successful" : "Unsuccessful";
        System.out.println("Receipt:\nTransaction Type: " + transactionType + "\nAction: " + action + "\nStatus: " + status);
        if (balance != 0) {
            System.out.println("Current Balance: $" + balance);
        }
    }

    private void performTransfer() {
        System.out.println("Choose the" + ConsoleUtility.PURPLE + " 'FROM' " + ConsoleUtility.RESET + "account:");
        Account fromChoice = askAccount();

        System.out.println("Choose the " + ConsoleUtility.CYAN + "'TO'"  + ConsoleUtility.RESET + "account:");
        Account toChoice = askAccount();

        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (fromChoice.getAccountType().equals(toChoice.getAccountType())) {
            System.out.println(ConsoleUtility.RED + "Cannot transfer between the same accounts. Please choose different accounts." + ConsoleUtility.RESET);
        } else {
            accountTransfer(fromChoice, toChoice, amount);
        }
    }

    private void accountTransfer(Account fromChoice, Account toChoice, double amount) {
        if (fromChoice.getBalance() >= amount) {
            fromChoice.withdraw(amount);
            toChoice.deposit(amount);
            System.out.println(ConsoleUtility.GREEN + "Transfer successful!" + ConsoleUtility.RESET);
            transactionHistory.addAccountTransaction("Transfer from " + fromChoice.getAccountType() + " to " + toChoice.getAccountType(), true, fromChoice.getBalance());
            displayTransactionReceipt("Transfer", true, fromChoice.getBalance());
        } else {
            transactionHistory.addAccountTransaction("Failed transfer from " + fromChoice.getAccountType() + " to " + toChoice.getAccountType(), false, fromChoice.getBalance());
            displayTransactionReceipt("Transfer", false, fromChoice.getBalance());
            System.out.println(ConsoleUtility.RED + "Insufficient funds for the transfer." + ConsoleUtility.RESET);
        }
    }

    private void performDeposit(Account account) {
        System.out.print("Enter deposit amount: ");
        double amt = scanner.nextInt();
        account.deposit(amt);
        scanner.nextLine();
        transactionHistory.addAccountTransaction("Deposit to " + account.getAccountType(), true, account.getBalance());
        displayTransactionReceipt("Deposit", true, account.getBalance());
        System.out.println(ConsoleUtility.GREEN + "Deposit successful!" + ConsoleUtility.RESET);
    }

    // withdraw a multiple of 5, can only receive $5 and $20 bills
    private void performWithdrawal(Account account) {
        System.out.print("Enter withdrawal amount (must be a multiple of 5): $");
        int withdrawalAmount = scanner.nextInt();

        if (withdrawalAmount % 5 != 0) {
            System.out.println(ConsoleUtility.RED + "Invalid withdrawal amount. Please enter a multiple of 5." + ConsoleUtility.RESET);
            return;
        }

        int numTwentyBills = 0;
        int numFiveBills = 0;

        System.out.print("Enter the number of $20 bills: ");
        numTwentyBills = scanner.nextInt();

        System.out.print("Enter the number of $5 bills: ");
        numFiveBills = scanner.nextInt();

        int totalWithdrawal = (numTwentyBills * 20) + (numFiveBills * 5);

        if (totalWithdrawal != withdrawalAmount) {
            System.out.println(ConsoleUtility.RED + "Invalid combination of bills. Please try again." + ConsoleUtility.RESET);
        } else {
            boolean withdrawalSuccess = account.withdraw(withdrawalAmount);

            if (withdrawalSuccess) {
                transactionHistory.addAccountTransaction("Withdrawal from " + account.getAccountType(), true, account.getBalance());
                displayTransactionReceipt("Withdrawal", true, account.getBalance());
                System.out.println(ConsoleUtility.GREEN + "Withdrawal successful!" + ConsoleUtility.RESET);
                System.out.println("Dispensing: " + numTwentyBills + " $20 bills and " + numFiveBills + " $5 bills");
            } else {
                transactionHistory.addAccountTransaction("Failed withdrawal from " + account.getAccountType(), false, account.getBalance());
                displayTransactionReceipt("Withdrawal", false, account.getBalance());
                System.out.println(ConsoleUtility.RED + "Insufficient funds for withdrawal." + ConsoleUtility.RESET);
            }
        }
        scanner.nextLine();
    }
}
