package sa.edu.uqu.supermarket.inventory;

public class Item {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String code;

    public Item(String name, String description, double price, int quantity, String code) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("Code: %s, Name: %s, Price: %.2f, Quantity: %d", code, name, price, quantity);
    }
}