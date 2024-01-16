import java.io.Console;
import java.util.Scanner;
public class ATM {
    private Customer customer;
    private Account account;
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
        System.out.print("Would you like to make a " + ConsoleUtility.GREEN + "(s)avings" + ConsoleUtility.RESET + " or " + ConsoleUtility.BLUE + " (c)hecking account? " + ConsoleUtility.RESET);
        String answer = scanner.nextLine().toLowerCase();
        if (answer.equals("c")) {
            this.account= new Account("Checking Account", 0, customer);
        } else if (answer.equals("s")) {
            this.account = new Account("Savings Account", 0, customer);
        }
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
    private void mainMenu() {
        boolean continueTransaction = true;
        while (continueTransaction) {
            System.out.println("Main Menu:");
            System.out.println(ConsoleUtility.RED + "1." + ConsoleUtility.RESET + " Withdraw money");
            System.out.println(ConsoleUtility.LIGHT_RED + "2." + ConsoleUtility.RESET + " Deposit money");
            System.out.println(ConsoleUtility.YELLOW + "3." + ConsoleUtility.RESET + " Transfer money between accounts");
            System.out.println(ConsoleUtility.GREEN + "4."  + ConsoleUtility.RESET + " Get account balances");
            System.out.println(ConsoleUtility.BLUE + "5." + ConsoleUtility.RESET + " Get transaction history");
            System.out.println(ConsoleUtility.LIGHT_PURPLE + "6." + ConsoleUtility.RESET + " Change PIN");
            System.out.println(ConsoleUtility.PURPLE + "7." + ConsoleUtility.RESET + " Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    performWithdrawal();
                    break;
                case 2:
                    performDeposit();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    getAccountBalances();
                    break;
                case 5:
                    transactionHistory.getTransactionHistory();
                    break;
                case 6:
                    changePIN();
                    break;
                case 7:
                    exitATM();
                    continueTransaction = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            if (continueTransaction) {
                System.out.print("Do you want to do anything else? (yes/no): ");
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
    }

    private void changePIN() {
    }

    private void getAccountBalances() {
    }

    private void performTransfer() {
    }

    private void performDeposit() {
        System.out.print("Enter deposit amount: ");
        int amt = scanner.nextInt();
        account.deposit(amt);
        scanner.nextLine();
    }

    private void performWithdrawal() {
        System.out.print("Enter withdrawal amount: ");
        int amt = scanner.nextInt();
        account.withdraw(amt);
        scanner.nextLine();
    }
    }
