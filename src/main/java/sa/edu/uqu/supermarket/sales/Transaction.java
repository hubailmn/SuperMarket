package sa.edu.uqu.supermarket.sales;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Transaction implements Comparable<Transaction> {

    private final String transactionId;
    private final LocalDateTime transactionDate;
    private final double totalAmount;
    private final Map<String, Integer> itemsSold;

    public Transaction(String transactionId, double totalAmount, Map<String, Integer> itemsSold) {
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.itemsSold = itemsSold;
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction(String transactionId, String dateString, double totalAmount, Map<String, Integer> itemsSold) {
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.itemsSold = itemsSold;
        this.transactionDate = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Map<String, Integer> getItemsSold() {
        return itemsSold;
    }

    public String toCSVString() {
        StringBuilder sb = new StringBuilder();
        sb.append(transactionId).append(",");
        sb.append(transactionDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(",");
        sb.append(String.format("%.2f", totalAmount)).append(",");

        String itemString = itemsSold.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(java.util.stream.Collectors.joining("|"));
        sb.append(itemString);

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Date: %s | Total: $%.2f",
                transactionId,
                transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                totalAmount);
    }

    @Override
    public int compareTo(Transaction other) {
        return Double.compare(other.totalAmount, this.totalAmount);
    }
}