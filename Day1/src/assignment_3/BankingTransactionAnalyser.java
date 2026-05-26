package assignment_3;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Banking Transaction Analyser
 *
 */
public class BankingTransactionAnalyser {
    public static void main(String[] args) {
        List<Transaction> transactions = List.of(
                new Transaction("T001", "ACC123", 50000, "Credit", "2024-01-15"),
                new Transaction("T002", "ACC123", 75000, "Debit", "2024-01-16"),
                new Transaction("T003", "ACC456", 30000, "Credit", "2024-01-17"),
                new Transaction("T004", "ACC456", 15000, "Debit", "2024-01-18"),
                new Transaction("T005", "ACC789", 80000, "Credit", "2024-01-19")
        );
        findDebitTransactions(transactions);
        findHighValueTransactions(transactions);
        calculateTotalCreditedAmount(transactions);
        groupTransactionsByType(transactions);
        findLatestTransaction(transactions);
        sortTransactionsByAmountDescending(transactions);
        processTransactionsInParallel(transactions);
    }

    //1. Find all debit Transactions
    public static void findDebitTransactions(List<Transaction> transactions) {
        System.out.println("-------------------------------------------");
        System.out.println("All Debit Transactions: ");
        System.out.println("--------------------------------------------");
        transactions.stream()
                .filter(txn -> "Debit".equals(txn.getTransactionType()))
                .forEach(System.out::println);
    }

    //2. Find all transaction above 50000
    public static void findHighValueTransactions(List<Transaction> transactions) {
        System.out.println("-------------------------------------------");
        System.out.println("All Transactions above 50000: ");
        System.out.println("--------------------------------------------");
        transactions.stream()
                .filter(txn -> txn.getAmount() > 50000)
                .forEach(System.out::println);
    }

    //3. Calculated total credited amount
    public static void calculateTotalCreditedAmount(List<Transaction> transactions) {
        double totalCredited = transactions.stream()
                .filter(txn -> "Credit".equals(txn.getTransactionType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
        System.out.println("-------------------------------------------");
        System.out.println("Total Credited Amount: " + totalCredited);
        System.out.println("--------------------------------------------");
    }

    //4. Group Transactions by type
    public static void groupTransactionsByType(List<Transaction> transactions) {
        System.out.println("-------------------------------------------");
        System.out.println("Transactions grouped by type: ");
        System.out.println("--------------------------------------------");
        var grouped = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionType));
        grouped.forEach((type, txnList) -> {
            System.out.println(type + ":");
            txnList.forEach(System.out::println);
        });
    }

    //5. Find latest transaction
    public static void findLatestTransaction(List<Transaction> transactions) {
        System.out.println("-------------------------------------------");
        System.out.println("Latest Transaction: ");
        System.out.println("--------------------------------------------");
        transactions.stream()
                .max((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()))
                .ifPresent(System.out::println);
    }

    //6. Sort transactions by amount in descending
    public static void sortTransactionsByAmountDescending(List<Transaction> transactions) {
        System.out.println("-------------------------------------------");
        System.out.println("Transactions sorted by amount in descending order: ");
        System.out.println("--------------------------------------------");
        transactions.stream()
                .sorted((t1, t2) -> Double.compare(t2.getAmount(), t1.getAmount()))
                .forEach(System.out::println);
    }

    //7. Use parallel stream for processing
    public static void processTransactionsInParallel(List<Transaction> transactions) {
        System.out.println("-------------------------------------------");
        System.out.println("Processing transactions in parallel: ");
        System.out.println("--------------------------------------------");
        transactions.parallelStream()
                .filter(txn -> "Credit".equals(txn.getTransactionType()))
                .forEach(System.out::println);
    }
}