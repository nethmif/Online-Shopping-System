//import org.junit.Before;
//import org.junit.Test;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import static org.junit.Assert.*;
//public class TestUseCases {
//    private WestminsterShoppingManager shoppingManager;
//    @Before
//    public void setUp() {
//        shoppingManager = new WestminsterShoppingManager();
//    }
//    // Test to add a product
//    @Test
//    public void testAddProduct() {
//        // Add a product
//        Electronics electronicProduct = new Electronics("E001", "Laptop", 18, 300, "Dell, 7 years warranty", "Electronics", "Dell", 7);
//        shoppingManager.addProduct(electronicProduct);
//        Clothing clothingProduct = new Clothing("C001", "T-Shirt", 10, 70, "L,Black", "Clothing", "L", "Black");
//        shoppingManager.addProduct(clothingProduct);
//        // Check if the product is added to the list
//        assertTrue(WestminsterShoppingManager.getListOfProducts().contains(electronicProduct));
//    }
//    // Test to remove a product
//    @Test
//    public void testRemoveProduct() {
//        // Add a product
//        Electronics electronicProduct = new Electronics("E002", "Phone", 20, 75, "Samsung, 5", "Electronics", "Samsung", 5);
//        shoppingManager.addProduct(electronicProduct);
//        // Remove the product
//        shoppingManager.removeProduct("E002");
//        // Check if the product is removed from the list
//        assertFalse(WestminsterShoppingManager.getListOfProducts().contains(electronicProduct));
//    }
//    // Test to print the product list in alphabetical order
//    @Test
//    public void testPrintProductList() {
//        // Add products
//        Electronics electronicProduct = new Electronics("E003", "Headset", 20, 60, "Sony,6", "Electronics", "Sony", 6);
//        Clothing clothingProduct = new Clothing("C002", "Shirt", 25, 14, "L,Blue", "Clothing", "L", "Blue");
//        shoppingManager.addProduct(electronicProduct);
//        shoppingManager.addProduct(clothingProduct);
//        ByteArrayOutputStream cont = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(cont));
//        // Print the product list
//        shoppingManager.printProductList();
//        System.setOut(System.out);
////        "E001", "Laptop", 18, 300, "Dell, 7 years warranty", "Dell", 7
////        "C001", "T-Shirt", 10, 70, "L,Black", "L", "Black"
////        "E003", "Headset", 20, 60, "Sony,6", "Sony", 6
////        "C002", "Shirt", 25, 14, "L,Blue", "L", "Blue"
//        // Check if the printed output contains the product information
//        String expectedResult = "Product Id: C002\nProduct name: Shirt\nNumber Of Available Items: 25\nPrice: £14.0\nProduct type: Clothing\nSize: L\nColor: Blue\n\nProduct Id: C003\nProduct name: Short\nNumber Of Available Items: 35\nPrice: £20.0\nProduct type: Clothing\nSize: L\nColor: Brown\n\nProduct Id: C003\nProduct name: Short\nNumber Of Available Items: 35\nPrice: £20.0\n\nProduct Id: E003\nProduct name: Headset\nNumber Of Available Items: 20\nPrice: £60.0\nProduct Type: Electronics\nBrand: Sony\nWarranty Period: 6 years\n\nProduct Id: E004\nProduct name: Keyboard\nNumber Of Available Items: 25\nPrice: £32.0\nProduct Type: Electronics\nBrand: Logitech\nWarranty Period: 2 years\n\nProduct Id: E004\nProduct name: Keyboard\nNumber Of Available Items: 25\nPrice: £32.0\n\n";
//        assertEquals(expectedResult, cont.toString());
//    }
//    // Test save and load file
//    @Test
//    public void testSaveAndLoadFromFile() {
//        // Clear existing products
//        WestminsterShoppingManager.getListOfProducts().clear();
//        // Add products
//        Electronics electronicProduct = new Electronics("E004", "Keyboard", 25, 32, "Logitech, 2", "Electronics", "Sony", 2);
//        Clothing clothingProduct = new Clothing("C003", "Short", 35, 20, "L,Brown", "Clothing", "L", "Brown");
//        shoppingManager.addProduct(electronicProduct);
//        shoppingManager.addProduct(clothingProduct);
//        // Save to file
//        shoppingManager.saveToFile();
//        // Load from file
//        shoppingManager.loadFromFile();
//        // Check if the products are loaded correctly
//        assertTrue(WestminsterShoppingManager.getListOfProducts().contains(electronicProduct));
//        assertTrue(WestminsterShoppingManager.getListOfProducts().contains(clothingProduct));
//    }
//}
