package restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;

public class RestaurantOrderSystem extends JFrame implements ActionListener {

    private JPanel itemsPanel;
    private JTextArea receiptArea;
    private JTextField customerNameField, tableField, contactField;
    private JButton calcBtn, saveBtn, resetBtn, exitBtn;

    private Map<String, Object> categoryMap = new LinkedHashMap<>();
    private Map<FoodItem, JCheckBox> itemCheckMap = new HashMap<>();
    private Map<FoodItem, JLabel> itemPriceMap = new HashMap<>();
    private Map<FoodItem, JSpinner> itemQtyMap = new HashMap<>();

    private final double TAX_RATE = 0.18; // 18% tax
    private DecimalFormat moneyFmt = new DecimalFormat("#0.00");

    public RestaurantOrderSystem() {
        setTitle("Restaurant Ordering System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        initializeMenu(); // Setup categories & subcategories

        // Top title
        JLabel title = new JLabel("Restaurant Ordering System", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Left panel
        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(700, getHeight()));
        left.setBorder(BorderFactory.createTitledBorder("Menu"));
        left.setBackground(Color.LIGHT_GRAY);

        // Categories panel (buttons)
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (String category : categoryMap.keySet()) {
            JButton btn = new JButton(category);
            btn.addActionListener(e -> showItemsForCategory(category));
            categoryPanel.add(btn);
        }
        left.add(categoryPanel, BorderLayout.NORTH);

        // Items panel
        itemsPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        left.add(scrollPane, BorderLayout.CENTER);

        // Customer info
        JPanel custPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        custPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField(10);
        custPanel.add(customerNameField);
        
        custPanel.add(new JLabel("Contact Number:"));
        contactField = new JTextField(10);
        custPanel.add(contactField);

        custPanel.add(new JLabel("Table Number:"));
        tableField = new JTextField(4);
        custPanel.add(tableField);

        left.add(custPanel, BorderLayout.SOUTH);

        add(left, BorderLayout.WEST);

        // Right panel: receipt + buttons
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Receipt"));
        right.setBackground(Color.LIGHT_GRAY);

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane receiptScroll = new JScrollPane(receiptArea);
        right.add(receiptScroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        calcBtn = new JButton("Generate Bill");
        saveBtn = new JButton("Save Receipt");
        resetBtn = new JButton("Reset");
        exitBtn = new JButton("Exit");

        calcBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        buttons.add(calcBtn);
        buttons.add(saveBtn);
        buttons.add(resetBtn);
        buttons.add(exitBtn);

        right.add(buttons, BorderLayout.SOUTH);

        add(right, BorderLayout.CENTER);
    }

// Initialize categories, subcategories, and items /
    private void initializeMenu() {
        // Pizza category with subcategories
        Map<String, List<FoodItem>> pizzaSub = new LinkedHashMap<>();
        pizzaSub.put("Veg", Arrays.asList(
                new FoodItem("Margherita Pizza", 399.0, "Pizza"),
                new FoodItem("Paneer Pizza", 699.0, "Pizza"),
                new FoodItem("Classic Veg Pizza", 299.0, "Pizza"),
                new FoodItem("Pepi Paneer", 499.0, "Pizza"),
                new FoodItem("Farmhouse Pizza", 559.0, "Pizza"),
                new FoodItem("BBQ Pizza", 599.0, "Pizza"),
                new FoodItem("Veggie Supreme Pizza", 599.0, "Pizza"),
                new FoodItem("Cheese Burst Pizza", 599.0, "Pizza"),
                new FoodItem("Deluxe Veggie Pizza", 699.0, "Pizza"),
                new FoodItem("Corn & Cheese Pizza", 35.0, "Pizza"),
                new FoodItem("Mushroom Pizza", 38.0, "Pizza"),
                new FoodItem("Spinach & Cheese Pizza", 42.0, "Pizza"),
                new FoodItem("Mexican Green Wave", 48.0, "Pizza")

        ));
        pizzaSub.put("Non-Veg", Arrays.asList(
                new FoodItem("Chicken Tikka Pizza", 50.0, "Pizza"),
                new FoodItem("Chicken Sausage Pizza", 60.0, "Pizza"),
                new FoodItem("Chicken Pepperoni Pizza", 60.0, "Pizza"),
                new FoodItem("Spicy Chicken Pizza", 60.0, "Pizza"),
                new FoodItem("Chicken Tikka Pizza", 55.0, "Pizza"),
                new FoodItem("Pepperoni Pizza", 60.0, "Pizza"),
                new FoodItem("BBQ Chicken Pizza", 65.0, "Pizza"),
                new FoodItem("Chicken Sausage Pizza", 58.0, "Pizza"),
                new FoodItem("Smoky Chicken Pizza", 70.0, "Pizza"),
                new FoodItem("Chicken & Bacon Pizza", 75.0, "Pizza"),
                new FoodItem("Spicy Keema Pizza", 68.0, "Pizza"),
                new FoodItem("Prawn Pizza", 80.0, "Pizza"),
                new FoodItem("Chicken Supreme Pizza", 72.0, "Pizza"),
                new FoodItem("Meat Lovers Pizza", 85.0, "Pizza")
                
        ));
        categoryMap.put("Pizza", pizzaSub);

        Map<String, List<FoodItem>> drinksSub = new LinkedHashMap<>();
        
        // Burger category
Map<String, Object> burgerSub = new LinkedHashMap<>();

// Veg Burger subcategory (10 items)
burgerSub.put("Veg", Arrays.asList(
    new FoodItem("Classic Veg Burger", 120.00, "Burger"),
    new FoodItem("Cheese Veg Burger", 140.00, "Burger"),
    new FoodItem("Paneer Tikka Burger", 150.00, "Burger"),
    new FoodItem("Aloo Patty Burger", 110.00, "Burger"),
    new FoodItem("Veggie Delight Burger", 130.00, "Burger"),
    new FoodItem("Mushroom Veg Burger", 145.00, "Burger"),
    new FoodItem("Spicy Corn Burger", 125.00, "Burger"),
    new FoodItem("Grilled Veg Burger", 135.00, "Burger"),
    new FoodItem("Double Cheese Veg Burger", 160.00, "Burger"),
    new FoodItem("Farmhouse Veg Burger", 155.00, "Burger")
));

// Non-Veg Burger subcategory (10 items)
burgerSub.put("Non-Veg", Arrays.asList(
    new FoodItem("Classic Chicken Burger", 150.00, "Burger"),
    new FoodItem("Cheese Chicken Burger", 170.00, "Burger"),
    new FoodItem("Spicy Chicken Burger", 160.00, "Burger"),
    new FoodItem("Grilled Chicken Burger", 180.00, "Burger"),
    new FoodItem("Double Patty Chicken Burger", 200.00, "Burger"),
    new FoodItem("Bacon Chicken Burger", 190.00, "Burger"),
    new FoodItem("Chicken Tikka Burger", 175.00, "Burger"),
    new FoodItem("Peri-Peri Chicken Burger", 185.00, "Burger"),
    new FoodItem("Egg & Chicken Burger", 165.00, "Burger"),
    new FoodItem("Cheesy BBQ Chicken Burger", 195.00, "Burger")
));

// Add Burger category to categoryMap
categoryMap.put("Burger", burgerSub);


// Sizzler category
Map<String, Object> sizzlerSub = new LinkedHashMap<>();

// Veg Sizzler subcategory 
sizzlerSub.put("Veg", Arrays.asList(
    new FoodItem("Paneer Sizzler", 220.00, "Sizzler"),
    new FoodItem("Veggie Sizzler", 200.00, "Sizzler"),
    new FoodItem("Tofu Sizzler", 210.00, "Sizzler"),
    new FoodItem("Mushroom Sizzler", 215.00, "Sizzler"),
    new FoodItem("Mixed Veg Sizzler", 205.00, "Sizzler"),
    new FoodItem("Corn & Capsicum Sizzler", 195.00, "Sizzler"),
    new FoodItem("Paneer Tikka Sizzler", 225.00, "Sizzler"),
    new FoodItem("Cheese Veg Sizzler", 230.00, "Sizzler"),
    new FoodItem("Spicy Veg Sizzler", 210.00, "Sizzler"),
    new FoodItem("Veg Supreme Sizzler", 240.00, "Sizzler"),
    new FoodItem("Farmhouse Sizzler", 220.00, "Sizzler"),
    new FoodItem("Golden Corn Sizzler", 200.00, "Sizzler"),
    new FoodItem("Mix Paneer Sizzler", 235.00, "Sizzler")
));

// Non-Veg Sizzler subcategory 
sizzlerSub.put("Non-Veg", Arrays.asList(
    new FoodItem("Chicken Sizzler", 280.00, "Sizzler"),
    new FoodItem("Grilled Chicken Sizzler", 290.00, "Sizzler"),
    new FoodItem("BBQ Chicken Sizzler", 300.00, "Sizzler"),
    new FoodItem("Peri-Peri Chicken Sizzler", 295.00, "Sizzler"),
    new FoodItem("Chicken Tikka Sizzler", 310.00, "Sizzler"),
    new FoodItem("Butter Chicken Sizzler", 320.00, "Sizzler"),
    new FoodItem("Mutton Sizzler", 350.00, "Sizzler"),
    new FoodItem("Lamb Sizzler", 345.00, "Sizzler"),
    new FoodItem("Fish Sizzler", 300.00, "Sizzler"),
    new FoodItem("Prawn Sizzler", 360.00, "Sizzler"),
    new FoodItem("Egg & Chicken Sizzler", 275.00, "Sizzler"),
    new FoodItem("Spicy Chicken Sizzler", 305.00, "Sizzler"),
    new FoodItem("Mix Non-Veg Sizzler", 370.00, "Sizzler")
));

// Add Sizzler category to categoryMap
categoryMap.put("Sizzler", sizzlerSub);
// Exotic Fish category
Map<String, Object> exoticFishSub = new LinkedHashMap<>();

exoticFishSub.put("Fish", Arrays.asList(
    new FoodItem("Grilled Salmon", 450.00, "Exotic "),
    new FoodItem("Black Cod", 480.00, "Exotic "),
    new FoodItem("Seared Tuna", 470.00, "Exotic "),
    new FoodItem("Mahi Mahi", 430.00, "Exotic "),
    new FoodItem("Swordfish Steak", 490.00, "Exotic "),
    new FoodItem("Barramundi", 420.00, "Exotic "),
    new FoodItem("Red Snapper", 440.00, "Exotic "),
    new FoodItem("Yellowfin Tuna", 460.00, "Exotic "),
    new FoodItem("Kingfish", 450.00, "Exotic "),
    new FoodItem("Sea Bass", 470.00, "Exotic "),
    new FoodItem("Atlantic Cod", 430.00, "Exotic "),
    new FoodItem("Halibut", 480.00, "Exotic "),
    new FoodItem("Grouper", 460.00, "Exotic "),
    new FoodItem("Monkfish", 500.00, "Exotic "),
    new FoodItem("Snapper Supreme", 470.00, "Exotic "),
    new FoodItem("Bluefin Tuna", 550.00, "Exotic "),
    new FoodItem("Chilean Sea Bass", 600.00, "Exotic"),
        new FoodItem("Wild Arctic Char", 520.00, "Exotic Fish"),
    new FoodItem("Golden Pomfret", 490.00, "Exotic Fish"),
    new FoodItem("Japanese Yellowtail", 540.00, "Exotic Fish"),
    new FoodItem("Red Mullet", 460.00, "Exotic Fish"),
    new FoodItem("Hokkaido Scallop Fish", 580.00, "Exotic Fish"),
    new FoodItem("Black Grouper", 500.00, "Exotic Fish"),
    new FoodItem("Atlantic Halibut Fillet", 530.00, "Exotic Fish"),
    new FoodItem("Tasmanian Salmon", 560.00, "Exotic Fish"),
    new FoodItem("King Salmon Fillet", 600.00, "Exotic Fish"),
    new FoodItem("Rainbow Trout", 450.00, "Exotic Fish"),
    new FoodItem("Sablefish", 570.00, "Exotic Fish"),
    new FoodItem("Red Drum", 480.00, "Exotic Fish"),
    new FoodItem("Pomfret Royale", 520.00, "Exotic Fish")
));

// Add Exotic Fish category to categoryMap
categoryMap.put("Exotic", exoticFishSub);

// Soft Drinks
        drinksSub.put("Soft Drinks", Arrays.asList(
    new FoodItem("Coke", 79.0, "Drink"),
    new FoodItem("Pepsi", 79.0, "Drink"),
    new FoodItem("Sprite", 79.0, "Drink"),
    new FoodItem("Fanta", 79.0, "Drink"),
    new FoodItem("Mountain Dew", 79.0, "Drink"),
    new FoodItem("7 Up", 79.0, "Drink"),
    new FoodItem("Root Beer", 99.0, "Drink"),
    new FoodItem("Ginger Ale", 99.0, "Drink"),
    new FoodItem("Club Soda", 99.0, "Drink"),
    new FoodItem("Tonic Water", 99.0, "Drink"),
    new FoodItem("Diet Coke", 109.0, "Drink"),
    new FoodItem("Diet Pepsi", 109.0, "Drink")
));

// Cocktails (alcoholic)
drinksSub.put("Cocktails", Arrays.asList(
    new FoodItem("Mojito", 119, "Drink"),
    new FoodItem("Martini", 119, "Drink"),
    new FoodItem("Margarita",119, "Drink"),
    new FoodItem("Long Island Iced Tea",149, "Drink"),
    new FoodItem("Cosmopolitan", 149, "Drink"),
    new FoodItem("Bloody Mary",149, "Drink"),
    new FoodItem("Pina Colada",149, "Drink"),
    new FoodItem("Tequila Sunrise",199, "Drink"),
    new FoodItem("Whiskey Sour",199, "Drink"),
    new FoodItem("Mai Tai", 199, "Drink"),
    new FoodItem("Caipirinha",199, "Drink"),
    new FoodItem("Negroni",199, "Drink"),
    new FoodItem("Old Fashioned",199, "Drink"),
    new FoodItem("Daiquiri",249, "Drink"),
    new FoodItem("Screwdriver",249, "Drink")
));

// Mocktails (non-alcoholic cocktails)
drinksSub.put("Mocktails", Arrays.asList(
    new FoodItem("Virgin Mojito",209, "Drink"),
    new FoodItem("Fruit Punch",209, "Drink"),
    new FoodItem("Blue Lagoon",209, "Drink"),
    new FoodItem("Shirley Temple",249, "Drink"),
    new FoodItem("Lemon Iced Tea",249, "Drink"),
    new FoodItem("Virgin Pina Colada",249, "Drink"),
    new FoodItem("Strawberry Mojito",249, "Drink"),
    new FoodItem("Apple Fizz",249, "Drink"),
    new FoodItem("Watermelon Cooler",299, "Drink"),
    new FoodItem("Mango Mule",299, "Drink"),
    new FoodItem("Peach Iced Tea",299, "Drink"),
    new FoodItem("Kiwi Smash",299, "Drink"),
    new FoodItem("Cucumber Cooler",299, "Drink"),
    new FoodItem("Orange Blossom",299, "Drink"),
    new FoodItem("Cranberry Spritzer",299, "Drink")
));

// Add Drinks to main category map
categoryMap.put("Drinks", drinksSub);

//staters

              Map<String, List<FoodItem>> startersSub = new HashMap<>();
//              chinese staters
    startersSub.put("Chinese", Arrays.asList(
            new FoodItem("Spring Rolls", 49, "Starter"),
            new FoodItem("Chili Paneer", 99, "Starter"),
            new FoodItem("Manchurian", 99, "Starter"),
    new FoodItem("Honey Chilli Potato", 99, "Starter"),
    new FoodItem("Veg Hakka Noodles", 109, "Starter"),
    new FoodItem("Schezwan Noodles",109, "Starter"),
    new FoodItem("Veg Fried Rice", 109, "Starter"),
    new FoodItem("Paneer Chilli",149, "Starter"),
        new FoodItem("Chicken Lollipop", 199, "Starter"),
    new FoodItem("Dragon Chicken", 199, "Starter")
    ));
//    korean staters
    startersSub.put("Korean", Arrays.asList(
            new FoodItem("Kimchi Pancake", 149, "Starter"),
            new FoodItem("Tteokbokki", 149, "Starter"),
                new FoodItem("Japchae (Glass Noodles)",199, "Starter"),
    new FoodItem("Bulgogi Skewers", 199, "Starter"),
    new FoodItem("Kimchi Dumplings", 199, "Starter"),
    new FoodItem("Spicy Pork Belly", 199, "Starter"),
    new FoodItem("Seaweed Salad", 199, "Starter"),
    new FoodItem("Korean BBQ Wings", 299, "Starter"),
    new FoodItem("Bibimbap Mini Bowl", 299, "Starter"),
    new FoodItem("Korean Fried Chicken", 299, "Starter")
    ));
//    Indian staters
    startersSub.put("Indian", Arrays.asList(
            new FoodItem("Samosa", 49, "Starter"),
           
            new FoodItem("Aloo Tikki",49, "Starter"),
            new FoodItem("Hara Bhara Kebab", 149, "Starter"),
          new FoodItem("Veg Seekh Kebab", 149, "Starter"),
             new FoodItem("Paneer Pakora", 149, "Starter"),
                new FoodItem("Onion Bhaji", 199, "Starter"),
    new FoodItem("Cheese Corn Balls", 199, "Starter"),
     new FoodItem("Paneer Tikka", 199, "Starter"),
    new FoodItem("Chicken Tikka", 199, "Starter"),
    new FoodItem("Mutton Seekh Kebab",299, "Starter"),
    new FoodItem("Prawn Masala Fry", 299, "Starter"),
    new FoodItem("Chili Chicken", 299, "Starter")
    ));
    categoryMap.put("Starters", startersSub);

        // Main Course category
Map<String, Object> mainCourseSub = new LinkedHashMap<>();

// Sabji (all together, veg & non-veg mixed if needed)
mainCourseSub.put("Vegetables", Arrays.asList(
    new FoodItem("Paneer Butter Masala", 180.00, "Main Course"),
    new FoodItem("Mix Veg Curry", 150.00, "Main Course"),
    new FoodItem("Dal Makhani", 160.00, "Main Course"),
    new FoodItem("Chole Masala", 140.00, "Main Course"),
    new FoodItem("Aloo Gobi", 130.00, "Main Course"),
    new FoodItem("Bhindi Masala", 140.00, "Main Course"),
    new FoodItem("Palak Paneer", 170.00, "Main Course")
    ));
mainCourseSub.put("Nonveg Vegetables", Arrays.asList(
    new FoodItem("Butter Chicken", 250.00, "Main Course"),
    new FoodItem("Chicken Curry", 230.00, "Main Course"),
    new FoodItem("Mutton Curry", 300.00, "Main Course"),
    new FoodItem("Fish Curry", 270.00, "Main Course"),
    new FoodItem("Prawn Masala", 320.00, "Main Course"),
    new FoodItem("Chicken Tikka Masala", 260.00, "Main Course")
));

// Chapati / Roti (7 types)
mainCourseSub.put("Chapati", Arrays.asList(
    new FoodItem("Plain Roti", 15.00, "Main Course"),
    new FoodItem("Butter Roti", 20.00, "Main Course"),
    new FoodItem("Naan", 30.00, "Main Course"),
    new FoodItem("Garlic Naan", 40.00, "Main Course"),
    new FoodItem("Lachha Paratha", 50.00, "Main Course"),
    new FoodItem("Roomali Roti", 35.00, "Main Course"),
    new FoodItem("Cheese Naan", 60.00, "Main Course")
));
// Add Dessert category to categoryMap
categoryMap.put("Main Course", mainCourseSub);

// Dessert category
Map<String, Object> dessertSub = new LinkedHashMap<>();

// Ice Cream subcategory (10 items)
dessertSub.put("Ice Cream", Arrays.asList(
    new FoodItem("Vanilla Ice Cream", 80.00, "Dessert"),
    new FoodItem("Chocolate Ice Cream", 90.00, "Dessert"),
    new FoodItem("Strawberry Ice Cream", 85.00, "Dessert"),
    new FoodItem("Mango Ice Cream", 95.00, "Dessert"),
    new FoodItem("Butterscotch Ice Cream", 100.00, "Dessert"),
    new FoodItem("Pista Ice Cream", 110.00, "Dessert"),
    new FoodItem("Chocolate Chip Ice Cream", 120.00, "Dessert"),
    new FoodItem("Coffee Ice Cream", 105.00, "Dessert"),
    new FoodItem("Cookies & Cream Ice Cream", 115.00, "Dessert"),
    new FoodItem("Caramel Ice Cream", 125.00, "Dessert")
));

// Hot Dessert subcategory (10 items)
dessertSub.put("Hot", Arrays.asList(
    new FoodItem("Gulab Jamun", 70.00, "Dessert"),
    new FoodItem("Rasmalai", 100.00, "Dessert"),
    new FoodItem("Warm Chocolate Brownie", 120.00, "Dessert"),
    new FoodItem("Jalebi with Rabri", 90.00, "Dessert"),
    new FoodItem("Kheer", 80.00, "Dessert"),
    new FoodItem("Moong Dal Halwa", 110.00, "Dessert"),
    new FoodItem("Caramel Custard", 95.00, "Dessert"),
    new FoodItem("Bread Pudding", 85.00, "Dessert"),
    new FoodItem("Hot Chocolate Fudge", 130.00, "Dessert"),
    new FoodItem("Apple Pie (Warm)", 120.00, "Dessert")
));

// Add Dessert category to categoryMap
categoryMap.put("Dessert", dessertSub);



        // Initialize checkboxes and spinners
        for (Object value : categoryMap.values()) {
            if (value instanceof List) {
                for (FoodItem item : (List<FoodItem>) value) {
                    itemCheckMap.put(item, new JCheckBox(item.getName() + " - ₹" + moneyFmt.format(item.getPrice())));
                    itemQtyMap.put(item, new JSpinner(new SpinnerNumberModel(0, 0, 50, 1)));
                }
            } else if (value instanceof Map) {
                Map<String, List<FoodItem>> subMap = (Map<String, List<FoodItem>>) value;
                for (List<FoodItem> subItems : subMap.values()) {
                    for (FoodItem item : subItems) {
                        itemCheckMap.put(item, new JCheckBox(item.getName() + " - ₹" + moneyFmt.format(item.getPrice())));
                        itemQtyMap.put(item, new JSpinner(new SpinnerNumberModel(0, 0, 50, 1)));
                    }
                }
            }
        }
    }

//    /** Show items in itemsPanel for a given category (handles subcategories) */
    private void showItemsForCategory(String category) {
        itemsPanel.removeAll();
        Object value = categoryMap.get(category);
//        if (value == null) return;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        if (value instanceof List) {
            for (FoodItem item : (List<FoodItem>) value) {
                addItemToPanel(item, gbc, row++);
            }
        } else if (value instanceof Map) {
            Map<String, List<FoodItem>> subMap = (Map<String, List<FoodItem>>) value;
            for (Map.Entry<String, List<FoodItem>> subEntry : subMap.entrySet()) {
                JLabel subLabel = new JLabel("   ➤ " + subEntry.getKey());
                subLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
                gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 3;
                itemsPanel.add(subLabel, gbc);

                for (FoodItem item : subEntry.getValue()) {
                    addItemToPanel(item, gbc, row++);
                }
            }
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }


    
/** Add single FoodItem checkbox and spinner to itemsPanel */
private void addItemToPanel(FoodItem item, GridBagConstraints gbc, int row) {
    JCheckBox cb = itemCheckMap.get(item);
    JSpinner sp = itemQtyMap.get(item);

    gbc.gridwidth = 1;

    // First column → food checkbox
    gbc.gridx = 0; 
    gbc.gridy = row;
    gbc.anchor = GridBagConstraints.WEST;
    itemsPanel.add(cb, gbc);

    // Create a small panel for Qty + Spinner
    JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0)); 
    qtyPanel.add(new JLabel("Qty:"));
    qtyPanel.add(sp);

    // Last column → force it to right side
    gbc.gridx = 2;
    gbc.anchor = GridBagConstraints.EAST; // align right
    gbc.weightx = 1.0; // take extra space
    itemsPanel.add(qtyPanel, gbc);

    // Reset weightx so checkboxes don’t stretch
    gbc.weightx = 0.0;
}





   // Updated calculateAndShowReceipt() — place at class level and call from your button handler
private void calculateAndShowReceipt() {
    StringBuilder missingFields = new StringBuilder();
    String customerName = customerNameField.getText().trim();
    String tableStr = tableField.getText().trim();
    String contact = contactField.getText().trim();

    // Check customer name
    if (customerName.isEmpty()) {
        missingFields.append("- Customer Name\n");
    }else if (!customerName.matches("[a-zA-Z ]+")) {
        missingFields.append("- Customer Name must contain only letters.\n");
    }

    // Check table number
    int tableNumber = 0;
    if (tableStr.isEmpty()) {
        missingFields.append("- Table Number\n");
    } else {
        try {
            tableNumber = Integer.parseInt(tableStr);
            if (tableNumber <= 0) {
                missingFields.append("- Enter valid Table Number.\n");
            }
        } catch (NumberFormatException e) {
            missingFields.append("- Table Number must be a valid number\n");
        }
    }

    // Check contact number
    if (contact.isEmpty()) {
        missingFields.append("- Contact Number\n");
    } else if (contact.length() != 10 || !contact.matches("\\d+")) {
        missingFields.append("- Enter valid Contact number.\n");
    }

    // If any validation failed, show message and stop
    if (missingFields.length() > 0) {
        JOptionPane.showMessageDialog(this,
            "Please correct the following before generating the bill:\n" + missingFields.toString(),
            "Missing or Invalid Information",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

  
    // Begin receipt
    StringBuilder sb = new StringBuilder();
    String billNo = "BILL" + System.currentTimeMillis() % 100000; // simple unique bill number
    String dateTime = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    sb.append("\t***** CONTINENTAL CRAVINGS *****\n");
    sb.append("\t   TAX INVOICE / BILL\n");
    sb.append("-----------------------------------------------\n");
    sb.append("GSTIN: 27ABCDE1234F1Z5\n");
    sb.append(String.format("Bill No : %s\n", billNo));
    sb.append(String.format("Date    : %s\n", dateTime));
    sb.append(String.format("Customer: %s\n", customerName));
    sb.append(String.format("Table   : %s\n", tableNumber));
    sb.append(String.format("Contact : %s\n", contact));
    sb.append("-----------------------------------------------\n");
    sb.append(String.format("%-25s %5s %10s\n", "Item", "Qty", "Amount (₹)"));
    sb.append("-----------------------------------------------\n");

    double subtotal = 0;
    boolean anySelected = false;

    for (Object value : categoryMap.values()) {
        subtotal += processCategoryForReceipt(value, sb);
    }

    if (subtotal == 0) {
        JOptionPane.showMessageDialog(this,
                "Please select at least one item before generating the bill!",
                "No Items Selected", JOptionPane.WARNING_MESSAGE);
        return;
    }

    double tax = subtotal * TAX_RATE;
    double total = subtotal + tax;

    sb.append("-----------------------------------------------\n");
    sb.append(String.format("%-25s %15s\n", "Subtotal:", moneyFmt.format(subtotal)));
    sb.append(String.format("%-25s %15s\n", "GST (18%):", moneyFmt.format(tax)));
    sb.append(String.format("%-25s %15s\n", "TOTAL:", moneyFmt.format(total)));
    sb.append("-----------------------------------------------\n");
    sb.append("\t  THANK YOU FOR VISITING!\n");
    sb.append("\t     PLEASE COME AGAIN\n");

    receiptArea.setText(sb.toString());
}

@SuppressWarnings("unchecked")
private double processCategoryForReceipt(Object value, StringBuilder sb) {
    double subtotal = 0;

    if (value instanceof List) {
        List<FoodItem> items = (List<FoodItem>) value;
        for (FoodItem item : items) {
            JCheckBox cb = itemCheckMap.get(item);
            JSpinner sp = itemQtyMap.get(item);
            if (cb != null && sp != null && cb.isSelected()) {
                int qty = (Integer) sp.getValue();
                if (qty > 0) {
                    double itemTotal = item.getPrice() * qty;
                    subtotal += itemTotal;
                    sb.append(String.format("%-25s %5d %10s\n", item.getName(), qty, moneyFmt.format(itemTotal)));
                }
            }
        }
    } else if (value instanceof Map) {
        Map<String, Object> subMap = (Map<String, Object>) value;
        for (Object subValue : subMap.values()) {
            subtotal += processCategoryForReceipt(subValue, sb); // recursive call
        }
    }

    return subtotal;
}




    /** Helper to process a single item for receipt */
    private boolean processReceiptItem(StringBuilder sb, FoodItem item) {
        JCheckBox cb = itemCheckMap.get(item);
        JSpinner sp = itemQtyMap.get(item);
        int qty = (Integer) sp.getValue();
        if (cb.isSelected() && qty > 0) {
            double totalItem = item.getPrice() * qty;
            sb.append(String.format("%-20s x%2d  ₹%7s\n", item.getName(), qty, moneyFmt.format(totalItem)));
            return true;
        }
        return false;
    }

    /** Helper to calculate total for a single item */
    private double getItemTotal(FoodItem item) {
        JSpinner sp = itemQtyMap.get(item);
        int qty = (Integer) sp.getValue();
        return item.getPrice() * qty;
    }

    /** Save receipt to file */
    private void saveReceiptToFile() {
        String content = receiptArea.getText();
        if (content == null || content.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No receipt to save. Calculate first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Receipt");
        int choice = chooser.showSaveDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write(content);
                JOptionPane.showMessageDialog(this, "Receipt saved to: " + f.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** Reset form */
    private void resetForm() {
        for (JCheckBox cb : itemCheckMap.values()) cb.setSelected(false);
        for (JSpinner sp : itemQtyMap.values()) sp.setValue(0);
        customerNameField.setText("");
        tableField.setText("");
        contactField.setText("");
        receiptArea.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == calcBtn) calculateAndShowReceipt();
        else if (src == saveBtn) saveReceiptToFile();
        else if (src == resetBtn) resetForm();
        else if (src == exitBtn) System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RestaurantOrderSystem app = new RestaurantOrderSystem();
            app.setVisible(true);
        });
    }
}
