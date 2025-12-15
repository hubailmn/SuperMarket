package sa.edu.uqu.supermarket.inventory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryManager {

    public static final InventoryManager instance = new InventoryManager();
    private static final String CSV_FILE = "inventory.csv";
    private final List<Item> items = new ArrayList<>();

    private InventoryManager() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println(" ❌ Failed to create inventory.csv file");
                }
            } catch (IOException e) {
                System.out.println("Error creating inventory file: " + e.getMessage());
            }
        }
        loadItems();
    }

    public static InventoryManager getInstance() {
        return instance;
    }

    public void loadItems() {
        items.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String code = data[0];
                    String name = data[1];
                    String description = data[2];
                    double price = Double.parseDouble(data[3]);
                    int quantity = Integer.parseInt(data[4]);

                    Item item = new Item(name, description, price, quantity, code);
                    items.add(item);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Failed to load inventory from file: " + e.getMessage());
        }
    }

    public void saveItems() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Item item : items) {
                String line = String.join(",",
                        item.getCode(),
                        item.getName(),
                        item.getDescription(),
                        String.valueOf(item.getPrice()),
                        String.valueOf(item.getQuantity()));
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save inventory to file: " + e.getMessage());
        }
    }

    public void addItem(Item item) {
        if (getItemByCode(item.getCode()) != null) {
            System.out.println(" ❌ Item with code " + item.getCode() + " already exists. Use update instead.");
            return;
        }

        items.add(item);
        saveItems();
        System.out.println(" ✅ Item added successfully: " + item.getName());
    }

    public Item getItemByCode(String code) {
        for (Item item : items) {
            if (item.getCode().equalsIgnoreCase(code)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Item> searchItem(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchName = name.toLowerCase().trim();
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(searchName))
                .collect(Collectors.toList());
    }

    public boolean updateItemQuantity(String code, int quantityChange) {
        Item item = getItemByCode(code);
        if (item == null) {
            System.out.println(" ❌ Item with code " + code + " not found.");
            return false;
        }

        int newQuantity = item.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            System.out.printf(" ❌ Cannot reduce quantity by %d. Final quantity would be negative (%d).%n",
                    Math.abs(quantityChange), newQuantity);
            return false;
        }

        item.setQuantity(newQuantity);
        saveItems();
        System.out.printf(" ✅ Stock updated for %s. New quantity: %d%n", item.getName(), newQuantity);
        return true;
    }
}