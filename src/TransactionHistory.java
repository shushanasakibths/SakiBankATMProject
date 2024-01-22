//https://www.w3schools.com/java/java_arraylist.asp
//https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html

import java.util.ArrayList;

public class TransactionHistory {
    private ArrayList<String> history;
    private int accountTransactionIDCounter;
    private int securityTransactionIDCounter;

    public TransactionHistory() {
        this.history = new ArrayList<>();
        this.accountTransactionIDCounter = 1;
        this.securityTransactionIDCounter = 1;
    }

    // monetary transactions
    public void addAccountTransaction(String transactionSummary, boolean successful, double balance) {
        String transactionID = generateAccountTransactionID();
        String transactionInfo = formatTransactionInfo(transactionID, transactionSummary, successful, balance);
        history.add(0, transactionInfo);
    }

    // updates in security
    public void addSecurityTransaction(String transactionSummary, boolean successful) {
        String transactionID = generateSecurityTransactionID();
        String transactionInfo = formatTransactionInfo(transactionID, transactionSummary, successful, 0);
        history.add(0, transactionInfo);
    }

    // retrieves transaction history
    public String getFormattedHistory() {
        StringBuilder formattedHistory = new StringBuilder(ConsoleUtility.PURPLE + "Transaction History:\n" + ConsoleUtility.RESET);

        for (String transaction : history) {
            formattedHistory.append(transaction).append("\n");
        }

        return formattedHistory.toString();
    }

    private String generateAccountTransactionID() {
        return "A" + String.format("%04d", accountTransactionIDCounter++);
    }

    private String generateSecurityTransactionID() {
        return "S" + String.format("%04d", securityTransactionIDCounter++);
    }

    // formats history, latest updates on top, print like a receipt
    private String formatTransactionInfo(String transactionID, String transactionSummary, boolean successful, double balance) {
        StringBuilder formattedInfo = new StringBuilder("Receipt:\n");
        formattedInfo.append("Transaction ID: ").append(transactionID).append("\n");
        formattedInfo.append("Action: ").append(transactionSummary).append("\n");

        if (successful) {
            formattedInfo.append("Status: Successful\n");
        } else {
            formattedInfo.append("Status: Unsuccessful\n");
        }

        if (balance != 0) {
            formattedInfo.append("Current Balance: $").append(balance).append("\n");
        }

        return formattedInfo.toString();
    }
}