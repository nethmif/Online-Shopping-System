import javax.swing.*;
import java.io.*;
import java.util.*;

interface shoppingManager {
    public void addProduct(Product product);
    public void removeProduct(String productId);
    public void printProductList();
    public void saveToFile();
    public void loadFromFile();
}
class WestminsterShoppingManager implements shoppingManager {
    public static List<Product> listOfProducts;
    private static final int maximumNumberOfProducts = 50;
    private ShoppingCart shoppingCart;
    private Scanner scan;
    public static int totalAvailableItems = 0;  // to check the maximum number of products 50 does not exceed

    public static List<Product> getListOfProducts() {
        return listOfProducts;
    }

    public WestminsterShoppingManager() {
//        this.listOfProducts = new ArrayList<>();
        if (listOfProducts == null) {  // Initialize the list only if it's null
            listOfProducts = new ArrayList<>();
        }
        this.scan = new Scanner(System.in);
        this.shoppingCart = new ShoppingCart();
//        parseProduct();
    }

    public static void menuSelection() {
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        Scanner scan = new Scanner(System.in);
        wsm.loadFromFile();
        while (true) {
            try {
                System.out.print("-------------------------\nOnline Shopping System\n-------------------------\nPlease enter:\n1: Add a new product\n2: Delete a product\n3: Print the list of products\n4: Save in a file\n5: Shopping Cart GUI\n6: Exit the system\n\nPlease enter your choice: ");
                int option = scan.nextInt();
                scan.nextLine();
                if (option > 0 && option <= 6) {
                    switch (option) {
                        case 1:
                            System.out.print("Please enter:\n1: Electronics\n2: Clothing\nChoice: ");   // enter the choice 1 for electronics and 2 for clothing
                            int addTypeOfProduct = scan.nextInt();
                            try {
                                if (addTypeOfProduct == 1 | addTypeOfProduct == 2) {
                                    switch (addTypeOfProduct) {
                                        case 1:
                                            System.out.print("Please enter the productId: ");
                                            String addEProductId = scan.next();
                                            System.out.print("please enter the product name: ");
                                            String addEProductName = scan.next();
                                            if (addEProductName.matches("[a-zA-Z]+")) { // checks whether the entered name is valid without any numbers or symbols
                                                System.out.print("Please enter the number of available items: ");
                                                int addENoOfAvailableItems = scan.nextInt();
                                                System.out.print("Please enter the price: ");
                                                double addEPrice = scan.nextDouble();
                                                System.out.print("Please enter the brand: ");
                                                String brand = scan.next();
                                                System.out.print("Please enter the warranty period: ");
                                                int warrantyPeriod = scan.nextInt();
                                                String info = brand + "," + warrantyPeriod + " years warranty";
                                                String productType = "Electronics";
                                                Electronics electronicProduct = new Electronics(addEProductId, addEProductName, addENoOfAvailableItems, addEPrice, info, productType, brand, warrantyPeriod);
                                                wsm.addProduct(electronicProduct);  // adds the product as a electronic product
                                            } else {
                                                System.out.println("Please re-enter a valid Electronic product name.");
                                            }
                                            break;
                                        case 2:
                                            System.out.print("Please enter the productId: ");
                                            String addCProductId = scan.next();
                                            System.out.print("Please enter the product name: ");
                                            String addCProductName = scan.next();
                                            if (addCProductName.matches("[a-zA-Z]+")) {     // checks whether a valid name is entered without any symbols or numbers
                                                System.out.print("Please enter the number of available items: ");
                                                int addCNoOfAvailableItems = scan.nextInt();
                                                System.out.print("Please enter the price: ");
                                                double addCPrice = scan.nextDouble();
                                                String category = "Clothing";
                                                System.out.print("Please enter the size: ");
                                                String size = scan.next();
                                                System.out.print("Please enter the colour: ");
                                                String colour = scan.next();
                                                String productType = "Clothing";
                                                if (colour.matches("[a-zA-Z]+")) {      // checks whether a valid colour is entered without any symbols or letters added
                                                    String info = size + "," + colour;
                                                    Clothing clothingProduct = new Clothing(addCProductId, addCProductName, addCNoOfAvailableItems, addCPrice, info, productType, size, colour);
                                                    wsm.addProduct(clothingProduct);        // adds the product as a clothing product
                                                } else {
                                                    System.out.println("Please re-enter a valid Clothing colour.");     // if the user has entered symbols or numbers then this message is displayed
                                                }
                                            } else {
                                                System.out.println("Please re-enter a valid Clothing product name.");       // if the user has entered symbols or numbers then this message is displayed
                                            }
                                            break;
                                    }
                                } else {
                                    System.out.println("Incorrect option entered please enter 1 (Electronics) or 2 (Clothing).");        // instead of 1 or 2 for electronics and clothing a different option value is entered
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter an integer");
                                scan.nextLine();
                            }
                            break;
                        case 2:
                            System.out.print("Please enter the productId: ");
                            String productId = scan.next();
                            wsm.removeProduct(productId);
                            break;
                        case 3:
                            wsm.printProductList();
                            break;
                        case 4:
                            wsm.saveToFile();
                            break;
                        case 5:
                            ShoppingGUI frame = new ShoppingGUI();
                            frame.setTitle("Westminster Shopping Centre");
                            frame.setSize(800, 400);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.setVisible(true);

                            break;
                        case 6:
                            System.out.println("Exiting from the system.");
                            return;
                    }
                } else {
                    System.out.println("Incorrect option entered. Please enter an integer in the range of 1 to 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer ");
                scan.next();
            }
        }
    }

    public static Product getProductById(String productId) {
        for (Product product : listOfProducts) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null; // If no product with the specified ID is found
    }

    @Override
    public void addProduct(Product product) {
        if (totalNumberOfAvailableItems() < 50) {
            listOfProducts.add(product);
        } else {
            System.out.println("Maximum limit 50 is reached. Thus, no more products can be added.");
        }
    }
    // removing a product from the system
    public void removeProduct(String productId) {
        // Iterate through the list to find the product with the specified ID
        Product removingProduct = null;
        for (Product product : listOfProducts) {
            if (product.getProductId().equals(productId)) {
                removingProduct = product;
                break;
            }
        }
        // Check if the product was found
        if (removingProduct != null) {
            // Remove the product from the list by checking whether the product id is present in the system
            listOfProducts.remove(removingProduct);
            System.out.println("\nProduct ID: " + removingProduct.getProductId());
            System.out.println("Product Name: " + removingProduct.getProductName());
            System.out.println("Number Of items that was present in : " + removingProduct.getNumberOfAvailableItems());
            System.out.println("Price: £" + removingProduct.getPrice());
            if (removingProduct instanceof Electronics) {
                Electronics electronics = (Electronics) removingProduct;
                System.out.println("Product Type: Electronics");
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period: " + electronics.getWarrantyPeriod() + " years");
            } else if (removingProduct instanceof Clothing) {
                Clothing clothing = (Clothing) removingProduct;
                System.out.println("Product type: Clothing");
                System.out.println("Size: " + clothing.getSize());
                System.out.println("Color: " + clothing.getColour());
            }
            System.out.println("Product is removed successfully. ");
            System.out.println("The total number of items remaining in the system:  " + totalNumberOfAvailableItems());
            System.out.println();
        } else {
            System.out.println("Product with ID (" + productId + ") is not found.");
        }
    }

    // count for the total number of items in the system
    public int totalNumberOfAvailableItems() {
        for (Product product : listOfProducts) {
            totalAvailableItems += product.getNumberOfAvailableItems();
        }
        return totalAvailableItems;
    }
    // printing the product list in the system
    public void printProductList() {
        List<Product> sortedProducts = new ArrayList<>(listOfProducts);
        //ordered alphabetically  according to the product id
        Collections.sort(sortedProducts, Comparator.comparing(Product::getProductId));
        for (Product product : sortedProducts) {
            System.out.println("Product Id: " + product.getProductId());
            System.out.println("Product name: " + product.getProductName());
            System.out.println("Number Of Available Items: " + product.getNumberOfAvailableItems());
            System.out.println("Price: £" + product.getPrice());
            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                System.out.println("Product Type: Electronics");
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period: " + electronics.getWarrantyPeriod() + " years");
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                System.out.println("Product type: Clothing");
                System.out.println("Size: " + clothing.getSize());
                System.out.println("Color: " + clothing.getColour());
            }
            System.out.println();
        }
    }
    // after adding the product it is being saved into a file through the below method if the number 4 is entered in the console
    @Override
    public void saveToFile() {
        //Method to save the information in a file.
        int productsLength = listOfProducts.size();
        try {
            FileWriter writeToFile = new FileWriter("Products.txt");
            System.out.println(productsLength);
            writeToFile.write(String.valueOf(productsLength));
            writeToFile.write("\n");
            for (int i = 0; i < listOfProducts.size(); i++) {
                writeToFile.write(listOfProducts.get(i).getProductId());
                writeToFile.write("\n");
                writeToFile.write(listOfProducts.get(i).getProductName());
                writeToFile.write("\n");
                writeToFile.write(String.valueOf(listOfProducts.get(i).getNumberOfAvailableItems()));
                writeToFile.write("\n");
                writeToFile.write(String.valueOf(listOfProducts.get(i).getPrice()));
                writeToFile.write("\n");
                writeToFile.write(String.valueOf(listOfProducts.get(i).getInfo()));
                writeToFile.write("\n");
            }
            writeToFile.close();
            System.out.println();
            System.out.println("Saved successfully");
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    // after the system starts again all the content in the file should be loaded to the array list
    @Override
    public void loadFromFile() {
        // File reading information
        File file = new File("Products.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int productsLength = Integer.parseInt(reader.readLine());
            for (int i = 0; productsLength > i; i++) {
                String productId = (reader.readLine());
                String productName = (reader.readLine());
                Integer numberOfAvailableItems = Integer.valueOf((reader.readLine()));
                double price = Double.parseDouble((reader.readLine()));
                String info = (reader.readLine());
                String productType = (reader.readLine());
                Product product = new Product(productId, productName, numberOfAvailableItems, price, info, productType) {
                    @Override
                    public String toString() {
                        return null;
                    }
                };
                listOfProducts.add(product);
            }
        } catch (IOException e) {
            System.out.println("Error while reading information to file: " + e.getMessage());
        }
    }
}











//    @Override
//    public void saveToFile() {
//        int productsCount = listOfProducts.size();
//        try {
//            FileWriter obj = new FileWriter("Products.txt", true);
//            for (Product product : listOfProducts) {
//                obj.write(product.toString()+"\n"+"\n");
//            }
//            obj.close();
//            System.out.println("Product data successfully stored.");
//        } catch (IOException e) {
//            System.out.println("An error occurred while storing product data.");
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void loadFromFile() {
//        try {
//            File myObj = new File("Products.txt");
//            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//        try {
//            File readFile = new File("Products.txt");
//            Scanner input = new Scanner(readFile);
//
//            while (input.hasNextLine()) {
//                String line = input.nextLine();
//                // Assuming the format of each line is consistent with the toString() method of Product
//                String[] dataArray = line.split("\n");
//
//                // Check the product type to determine which concrete class to instantiate
//                String productType = dataArray[0].trim();
//                Product product = null;
//                if ("Electronics".equals(productType)) {
//                    product = new Electronics(dataArray[1], dataArray[2], Integer.parseInt(dataArray[3]), Double.parseDouble(dataArray[4]), dataArray[5], Integer.parseInt(dataArray[6]));
//                } else if ("Clothing".equals(productType)) {
//                    product = new Clothing(dataArray[1], dataArray[2], Integer.parseInt(dataArray[3]), Double.parseDouble(dataArray[4]), Integer.parseInt(dataArray[5]), dataArray[6]);
//                }
//
//                if (product != null) {
//                    listOfProducts.add(product);
//                }
//            }
//
//            input.close();
//            System.out.println("Successfully loaded program data from the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred while reading the file.");
//            e.printStackTrace();
//        }
//    }







//    public void loadFromFile() {
//        try {
//            File ReadFile = new File("Products.txt");
//            Scanner input = new Scanner(ReadFile);
//            while (input.hasNextLine()) {
//                String display = input.nextLine();
//                System.out.println(display);    // the arrays information for each row stored in the text file is being displayed.
//            }
//            input.close();
//        } catch (IOException e) {
//            System.out.println("Error occurred while reading the file.");
//            e.printStackTrace();
//        }
//    }

//        try {
//            File myObj = new File("Products.txt");
//            if (myObj.exists()) {
//                Scanner myReader = new Scanner(myObj);
//                while (myReader.hasNextLine()) {
//                    String[] dataArray = myReader.nextLine().split(": ");
//                    // Check the product type to determine which concrete class to instantiate
//                    String productType = dataArray[0].trim();
//                    Product product = null;
//                    if ("Electronics".equals(productType)) {
//                        product = new Electronics(dataArray[1], dataArray[2], Integer.parseInt(dataArray[3]), Double.parseDouble(dataArray[4]), dataArray[5], Integer.parseInt(dataArray[6]));
//                    } else if ("Clothing".equals(productType)) {
//                        product = new Clothing(dataArray[1], dataArray[2], Integer.parseInt(dataArray[3]), Double.parseDouble(dataArray[4]), Integer.parseInt(dataArray[5]), dataArray[6]);
//                    }
//                    if (product != null) {
//                        listOfProducts.add(product);
//                    }
//                }
//                myReader.close();
//                System.out.println("Successfully loaded program data from the file.");
//            } else {
//                System.out.println("No saved data can be found. Program will start without any loading of data.");
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
    //    @Override
//    public void saveToFile() {
//        try (PrintWriter writer = new PrintWriter(new FileWriter("Products.txt"))) {
//            for (Product product : listOfProducts) {
//                writer.println(product.toString());
//            }
//            System.out.println("Product list saved to file: " + "Products.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void loadFromFile() {
//        try (BufferedReader reader = new BufferedReader(new FileReader("Products.txt"))) {
//            listOfProducts.clear();  // Clear the list before loading
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Product product = parseProduct(line);
//                if (product != null) {
//                    listOfProducts.add(product);
//                }
//            }
//            System.out.println("Product list loaded from file: " + "Products.txt");
//            // Print loaded products for debugging
//            System.out.println("Loaded products:");
//            for (Product product : listOfProducts) {
//                System.out.println(product.toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Product parseProduct(String line) {
//        String[] parts = line.split(";");
//
//        if (parts.length > 0) {
//            String type = parts[0].trim();
//            if ("Electronics".equals(type)) {
//                return Electronics.fromString(parts);
//            } else if ("Clothing".equals(type)) {
//                return Clothing.fromString(parts);
//            }
//        }
//        // Return null if parsing fails
//        return null;
//    }
    // New method to load product data
//    public void loadFromFile() {
//        try {
//            File obj = new File("Products.txt");
//            if (obj.exists()) {
//                Scanner myReader = new Scanner(obj);
//                while (myReader.hasNextLine()) {
//                    String productData = myReader.nextLine();
//                    Product product = parseProduct(productData);
//                    if (product != null) {
//                        listOfProducts.add(product);
//                    }
//                }
//                myReader.close();
//                System.out.println("Product data is successfully loaded.\n");
//            } else {
//                System.out.println("No product data found.\n");
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Error occurred while loading product data.");
//            e.printStackTrace();
//        }
//    }
//    private Product parseProduct(String line) {
//        String[] parts = line.split("/n");
//
//        if (parts.length > 0) {
//            String type = parts[0].trim();
//            if ("Electronics".equals(type)) {
//                return Electronics.fromString(parts);
//            } else if ("Clothing".equals(type)) {
//                return Clothing.fromString(parts);
//            }
//        }
//
//        // Return null if parsing fails
//        return null;
//    }
    //    @Override
//    public void removeProduct(String productId){
//        Product removingProduct = null;
//        for (Product product : listOfProducts) {
//            if (product.getProductId().equals(productId)) {
//                removingProduct = product;
//                break;
//            }
//        }
//        if (removingProduct != null) {
//            listOfProducts.remove(removingProduct);
//            System.out.println(removingProduct.getProductName()+"("+removingProduct.getProductId()+") removed successfully");
//        } else {
//            System.out.println("Product: " + productId + " is not found.");
//        }
//    }
//    public void loadFromFile() {
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Products.txt"))) {
//            List<Product> loadedProductList = (List<Product>) inputStream.readObject();
//            productsList.clear();
//            productsList.addAll(loadedProductList);
//            System.out.println("Product list loaded from file: ProductList.txt");
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }



//                            System.out.println("Please enter,\n1: Electronics\n2: Clothing");
//                                    int removeTypeOfProduct = scan.nextInt();
//                                    try {
//                                    if (removeTypeOfProduct == 1 | removeTypeOfProduct == 2) {
//                                    switch (removeTypeOfProduct) {
//                                    case 1:
//                                    System.out.print("Please enter the productId: ");
//                                    String removeEProductId = scan.next();
//                                    System.out.print("please enter the product name: ");
//                                    String removeEProductName = scan.next();
//                                    System.out.print("Please enter the number of available items: ");
//                                    int removeENoOfAvailableItems = scan.nextInt();
//                                    System.out.print("Please enter the price: ");
//                                    double removeEPrice = scan.nextDouble();
//                                    System.out.print("Please enter the brand: ");
//                                    String brand = scan.next();
//                                    System.out.print("Please enter the warranty period: ");
//                                    int warrantyPeriod = scan.nextInt();
//                                    Electronics electronicProduct = new Electronics(removeEProductId, removeEProductName, removeENoOfAvailableItems, removeEPrice, brand, warrantyPeriod);
//                                    wsm.removeProduct(String.valueOf(electronicProduct));
//                                    break;
//                                    case 2:
//                                    System.out.print("Please enter the productId: ");
//                                    String removeCProductId = scan.next();
//                                    System.out.print("please enter the product name: ");
//                                    String removeCProductName = scan.next();
//                                    System.out.print("Please enter the number of available items: ");
//                                    int removeCNoOfAvailableItems = scan.nextInt();
//                                    System.out.print("Please enter the price: ");
//                                    double removeCPrice = scan.nextDouble();
//                                    System.out.print("Please enter the size: ");
//                                    int size = scan.nextInt();
//                                    System.out.print("Please enter the colour: ");
//                                    String colour = scan.next();
//                                    Clothing clothingProduct = new Clothing(removeCProductId, removeCProductName, removeCNoOfAvailableItems, removeCPrice, size, colour);
//                                    wsm.removeProduct(String.valueOf(clothingProduct));
//                                    break;
//                                    }
//                                    } else {
//                                    System.out.println("Incorrect option entered please enter 1 (Electronics) or 2 (Clothing).");
//                                    }
//                                    } catch (InputMismatchException e){
//                                    System.out.println("Please enter an integer option");
//                                    }