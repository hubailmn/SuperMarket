package sa.edu.uqu.supermarket.sales;

import sa.edu.uqu.supermarket.inventory.InventoryManager;
import sa.edu.uqu.supermarket.inventory.Item;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class SalesManager {

    public static final SalesManager instance = new SalesManager();
    private static final String CSV_FILE = "sales.csv";
    private final List<Transaction> transactions = new ArrayList<>();
    private final AtomicLong nextTransactionId = new AtomicLong(1);

    private SalesManager() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println(" ❌ Failed to create sales.csv file");
                }
            } catch (IOException e) {
                System.out.println("Error creating sales file: " + e.getMessage());
            }
        }
        loadTransactions();
    }

    public static SalesManager getInstance() {
        return instance;
    }

    private static Transaction getTransaction(String[] data, String id) {
        String date = data[1];
        double total = Double.parseDouble(data[2]);

        Map<String, Integer> itemsSold = new HashMap<>();
        String[] itemData = data[3].split("\\|");
        for (String itemEntry : itemData) {
            String[] parts = itemEntry.split(":");
            if (parts.length == 2) {
                itemsSold.put(parts[0], Integer.parseInt(parts[1]));
            }
        }

        return new Transaction(id, date, total, itemsSold);
    }

    public void loadTransactions() {
        transactions.clear();
        long maxId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", 4);
                if (data.length == 4) {
                    String id = data[0];
                    Transaction transaction = getTransaction(data, id);
                    transactions.add(transaction);

                    try {
                        long currentId = Long.parseLong(id);
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            nextTransactionId.set(maxId + 1);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Failed to load transactions from file: " + e.getMessage());
        }
    }

    private void saveTransactions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Transaction transaction : transactions) {
                bw.write(transaction.toCSVString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save transactions to file: " + e.getMessage());
        }
    }

    public void recordTransaction(double totalAmount, Map<String, Integer> itemsSold) {
        String id = String.valueOf(nextTransactionId.getAndIncrement());
        Transaction transaction = new Transaction(id, totalAmount, itemsSold);
        transactions.add(transaction);
        saveTransactions();
    }

    public void generateSalesReport() {

        System.out.println("\n--- SALES REPORT ---");

        if (transactions.isEmpty()) {
            System.out.println("No sales transactions recorded.");
            return;
        }

        Map<String, double[]> salesSummary = new HashMap<>();
        double overallRevenue = 0.0;
        int totalUnitsSold = 0;

        for (Transaction t : transactions) {
            overallRevenue += t.getTotalAmount();
            for (Map.Entry<String, Integer> itemEntry : t.getItemsSold().entrySet()) {
                String code = itemEntry.getKey();
                int quantity = itemEntry.getValue();

                double[] summary = salesSummary.getOrDefault(code, new double[]{0.0, 0.0});

                Item item = InventoryManager.getInstance().getItemByCode(code);
                double price = (item != null) ? item.getPrice() : 0.0;

                summary[0] += quantity;
                summary[1] += quantity * price;
                salesSummary.put(code, summary);

                totalUnitsSold += quantity;
            }
        }

        String[][] reportData = new String[salesSummary.size()][4];
        int i = 0;
        for (Map.Entry<String, double[]> entry : salesSummary.entrySet()) {
            String code = entry.getKey();
            double[] summary = entry.getValue();
            Item item = InventoryManager.getInstance().getItemByCode(code);
            String name = (item != null) ? item.getName() : "Unknown Item";

            reportData[i][0] = code;
            reportData[i][1] = name;
            reportData[i][2] = String.format("%.0f", summary[0]);
            reportData[i][3] = String.format("%.2f", summary[1]);
            i++;
        }

        Arrays.sort(reportData, (a, b) -> Double.compare(Double.parseDouble(b[3]), Double.parseDouble(a[3])));

        System.out.printf("Overall Total Revenue: $%.2f%n", overallRevenue);
        System.out.printf("Overall Total Units Sold: %d%n%n", totalUnitsSold);

        System.out.println("--- Product Sales Leaderboard (Sorted by Revenue) ---");
        System.out.printf("%-10s %-20s %-12s %-15s%n", "Code", "Name", "Units Sold", "Revenue");
        System.out.println("-----------------------------------------------------------------");

        for (String[] row : reportData) {
            System.out.printf("%-10s %-20s %-12s $%-15s%n", row[0], row[1], row[2], row[3]);
        }

        System.out.println("\n--- Detailed Transaction History (Newest First) ---");
        List<Transaction> sortedTransactions = new ArrayList<>(transactions);
        sortedTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

        for (Transaction t : sortedTransactions) {
            System.out.println(t.toString());
        }
    }
}