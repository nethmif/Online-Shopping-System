import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class ShoppingGUI extends JFrame {
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton viewCartButton;
    private ArrayList<Product> productList;
    private ArrayList<Product> shoppingCart;
    private Map<String, Integer> purchaseHistory;
    public ShoppingGUI() {
        setTitle("Westminster Shopping Centre");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setupLayout();
        productList = new ArrayList<>();
        shoppingCart = new ArrayList<>();
        purchaseHistory = new HashMap<>();
        productList.add(new Electronics("E001", "TV", 5, 325, "Panasonic, 6 years warranty","Electronics", "Panasonic", 6));
        productList.add(new Electronics("E002", "Phone", 10, 250, "Samsung, 7 years warranty", "Electronics", "Samsung", 7));
        productList.add(new Clothing("C001", "Shirt", 35, 25, "S,White", "Clothing", "S", "White"));
        productList.add(new Clothing("C002", "Trouser", 15, 20, "M,Black", "Clothing", "M", "Black"));
        productList.add(new Clothing("C003", "T-Shirt", 7, 22, "L,Brown", "Clothing", "L", "Brown"));
        // Populate the product table with data
        populateProductTable();
    }
    private void initializeComponents() {
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productTable = new JTable();
        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton("Add to Shopping Cart");
        viewCartButton = new JButton("Shopping Cart");
        // Add action listeners to buttons
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCartAction();
            }
        });
        // View cart button
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCartAction();
            }
        });
        // Add a selection listener to the product table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                displayProductDetails();
            }
        });
// Add an action listener to the product type combo box
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateProductTable();
            }
        });
    }
    // Setting up the layout
    private void setupLayout() {
        setLayout(new BorderLayout());
        // Create a panel for product type selection
        JPanel typePanel = new JPanel();
        typePanel.add(new JLabel("Select Product Category:"));
        typePanel.add(productTypeComboBox);
        // Create a scroll pane for the product table
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        // Create a panel for product details
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JLabel("Selected Product - Details:"), BorderLayout.NORTH);
        detailsPanel.add(new JScrollPane(productDetailsTextArea), BorderLayout.CENTER);
        // Create a buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);
        buttonPanel.add(viewCartButton);
        // Add components to the main frame
        add(typePanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void populateProductTable() {
        String productType = (String) productTypeComboBox.getSelectedItem();
        ArrayList<Product> filteredProducts = filterProductsByType(productType);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Available Items");
        model.addColumn("Price");
        for (Product product : filteredProducts) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    product.getNumberOfAvailableItems(),
                    product.getPrice()
            });
        }
        // Set the model to the product table
        productTable.setModel(model);
    }
    private ArrayList<Product> filterProductsByType(String productType) {
        if ("All".equals(productType)) {
            return productList;
        } else if ("Electronics".equals(productType)) {
            return filterProductsByCategory(Electronics.class);
        } else if ("Clothing".equals(productType)) {
            return filterProductsByCategory(Clothing.class);
        } else {
            return productList;
        }
    }
    private ArrayList<Product> filterProductsByCategory(Class<? extends Product> category) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : productList) {
            if (category.isInstance(product)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
    private void productTypeComboBoxActionPerformed(ActionEvent evt) {
        // Update the product table when the product type selection changes
        populateProductTable();
    }
    private void displayProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = findProductById(productId);

            if (selectedProduct != null) {
                productDetailsTextArea.setText(selectedProduct.toString());
            }
        }
    }
    private void addToCartAction() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = findProductById(productId);

            if (selectedProduct != null) {
                shoppingCart.add(selectedProduct);
                // Additional logic, e.g., update the shopping cart display
                JOptionPane.showMessageDialog(this, "Product added to the cart!");
            }
        }
    }
    private void viewCartAction() {
        // Implement logic to display the shopping cart, e.g., in a new window or dialog
        // You can use a JTable to display the shopping cart contents
        if (!shoppingCart.isEmpty()) {
            double totalCost = calculateTotalCost();
            String message = buildShoppingCartMessage(totalCost);
            JOptionPane.showMessageDialog(this, message, "Shopping Cart", JOptionPane.INFORMATION_MESSAGE);
        } else {
//            JOptionPane.showMessageDialog(this, "Shopping cart is empty.", "Shopping Cart", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private double calculateTotalCost() {
        double totalCost = 0;
        for (Product product : shoppingCart) {
            totalCost += product.getPrice();
        }

        // Apply discounts
        int totalItems = shoppingCart.size();
        if (totalItems >= 3) {
            totalCost *= 0.8; // 20% discount for buying at least three products
        }

        // Check if it's the first purchase for the client
        String clientId = "Client1"; // Replace with actual client identifier
        if (!purchaseHistory.containsKey(clientId)) {
            totalCost *= 0.9; // 10% discount for the very first purchase
            purchaseHistory.put(clientId, 1); // Record the first purchase
        }

        return totalCost;
    }

    private String buildShoppingCartMessage(double totalCost) {
        StringBuilder message = new StringBuilder("Shopping Cart Contents:\n");
        for (Product product : shoppingCart) {
            message.append(product).append("\n");
        }
        message.append("\nFinal Price: $").append(String.format("%.2f", totalCost));

        return message.toString();
    }
    private Product findProductById(String productId) {
        for (Product product : productList) {
            if (productId.equals(product.getProductId())) {
                return product;
            }
        }
        return null;
    }
}


