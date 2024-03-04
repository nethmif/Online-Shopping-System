import java.util.Arrays;

abstract class Product{
    private String productId;
    private String productName;
    private int numberOfAvailableItems;
    private double price;
    private String info;
    private String productType;
    private ShoppingCart shoppingCart;
    /// Product constructor
    public Product(String productId, String productName, int availableItems, double price, String info, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.numberOfAvailableItems = availableItems;
        this.price = price;
        this.info = info;
        this.productType = productType;
        this.shoppingCart = new ShoppingCart();
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getNumberOfAvailableItems() {
        return numberOfAvailableItems;
    }
    public void setNumberOfAvailableItems(int availableItems) {
        this.numberOfAvailableItems = availableItems;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    public abstract String toString();
    public static Product fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length > 0) {
            String type = parts[0].trim();
            if ("Electronics".equals(type)) {
                return Electronics.fromString(Arrays.toString(parts));
            } else if ("Clothing".equals(type)) {
                return Clothing.fromString(Arrays.toString(parts));
            }
        }
        return null;
    }
}
// Getters and Setters
class Electronics extends Product{
    public static String brand;
    public static int warrantyPeriod;
    public Electronics(String productId, String productName, int availableItems, double price, String info, String productType, String brand, int warrantyPeriod) {
        super(productId, productName, availableItems, price, info, productType);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }
    public static String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public static int getWarrantyPeriod() {
        return warrantyPeriod;
    }
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    public String toString(){
        return "Product Type: Electronics" + "\nProduct Id: " + getProductId() + "\nProduct name: " + getProductName() + "\nPrice: " + getPrice() + "\nNumber of available items : " + getNumberOfAvailableItems() + "\nBrand: " + getBrand() +
                "\nWarranty Period: " + getWarrantyPeriod() + " months";
    }

    public String productInfo() {
        return "Brand: " + getBrand() + ", Warranty Period: " + getWarrantyPeriod() + " months";
    }
}
class Clothing extends Product{
    public static String size;
    public static String colour;
    public Clothing(String productId, String productName, int availableItems, double price, String info, String productType, String size, String colour) {
        super(productId, productName, availableItems, price, info, productType);
        this.size = size;
        this.colour = colour;
    }
    public static String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public static String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public String toString() {
        return "Product Type: Clothing" + "\nProduct Id: " + getProductId() + "\nProduct name: " + getProductName() + "\nPrice: " + getPrice() + "\nNumber of available items : " + getNumberOfAvailableItems() + "\nSize: " + getSize() + "\nColor: " + getColour();
    }
}

