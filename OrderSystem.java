import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderSystem {

    // Define VAT rate and arrays for valid table numbers, menu items, menu prices,
    // and item numbers
    static final double VAT_RATE = 0.20;
    static final int[] VALID_TABLE_NUMBERS = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
    static final String[] MENU_ITEMS = { "Sweet corn", " w/ chicken", "Cream of Tomato/Mushroom", "Veg soup",
            "Baked Beans", "Cheese", "Tuna", "Coleslaw", "Cheese", " Ham & Cheese", "Bacon Egg Cheese",
            "Sausage Egg cheese", "Ham&Brie", "Grilled Chicken Mayo", "Prawn salad", "Egg Mayo", "Tuna sweetcorn",
            "Chicken", " Hamburger", "Veggie", "Steak & Ale Pie", "Fish & chips", "Breaded Scampi w/ fries",
            "Chicken pasta", "Lasagne", "Spaghetti Bolognese", "Hotdog w/ fries", "Chicken nuggets", "Macaroni Cheese",
            "Pigs in a blanket", "French fries", " Onion rings", "Garlic bread", "Nachos ",
            "Chocolate cookie", "Cheesecake ", "Pie ", " Pancakes (2)", "Tea cup",
            " Coffee", "Cappuccino", "Hot Chocolate", "Pop", "Sparkling Water" };
    static final double[] MENU_PRICES = { 1.99, 2.35, 2.10, 2.25, 3.20, 2.50, 3.75, 2.75, 2.25, 3.20, 3.75, 3.55, 3.75,
            3.25, 3.95, 2.95, 3.29, 5.65, 5.75, 4.95, 6.95, 7.95, 6.95, 6.79, 6.95, 5.99, 3.50, 2.55, 2.20, 2.95, 2.50,
            2.99, 2.99, 2.25, 2.10, 3.99, 2.25, 2.99, 2.10, 2.50, 2.75, 2.95, 2.35, 1.75 };
    static final int[] ITEM_NUMBER = { 11, 12, 13, 14, 21, 22, 23, 24, 31, 32, 33, 34, 41, 42, 43, 45, 46, 47, 48, 49,
            51, 52, 53, 54, 55, 56, 57, 60, 61, 62, 71, 72, 73, 74, 81, 82, 83, 84, 91, 92, 93, 94, 95, 96 };

    // Arrays to store order details: table number, item names, quantities, prices,
    // item numbers and comments
    static String[] orderComments = new String[MENU_ITEMS.length];
    static int[] orderTableNumbers = new int[MENU_ITEMS.length];
    static String[] orderItemNames = new String[MENU_ITEMS.length];
    static int[] orderQuantities = new int[MENU_ITEMS.length];
    static double[] orderPrices = new double[MENU_ITEMS.length];
    static int orderCount = 0;
    static int[] orderItemNumbers = new int[MENU_ITEMS.length];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Main loop for the menu
        while (!exit) {
            // Displaying the main menu options
            System.out.println("Menu:");
            System.out.println("1) Place Order");
            System.out.println("2) Display Invoice");
            System.out.println("3) Exit");
            System.out.print("Enter your choice: ");

            // Input validation for integer inputs

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        placeOrder(scanner);
                        break;
                    case 2:
                        displayInvoice();
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Exiting system...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // to avoid infinite loop
            }
        }
        scanner.close();
    }

    // Method to handle order placement
    private static void placeOrder(Scanner scanner) {
        if (orderCount >= MENU_ITEMS.length) {
            System.out.println("Order limit reached. Cannot place more orders.");
            return;
        }
        int orderNumber = 0;
        boolean validOrderNumber = false;
        while (!validOrderNumber) {
            System.out.print("Enter order number (101 to 105): ");
            try {
                orderNumber = scanner.nextInt();
                if (orderNumber < 101 || orderNumber > 105) {
                    System.out.println("Invalid order number. Please enter a number between 101 and 105.");
                } else {
                    validOrderNumber = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next(); // Consume the invalid input
            }
        }

        System.out.println("Placing an order...");
        System.out.print("Enter table number: ");
        int tableNumber = scanner.nextInt();

        // Validate the entered table number
        if (!validateTableNumber(tableNumber)) {
            System.out.println("Invalid table number.");
            return;
        }

        System.out.print("Enter item number: ");
        int itemNumber = scanner.nextInt();

        // Validate the entered item number against the ITEM_NUMBER array
        boolean validItem = false;
        int arrayIndex = -1;
        for (int i = 0; i < ITEM_NUMBER.length; i++) {
            if (ITEM_NUMBER[i] == itemNumber) {
                validItem = true;
                arrayIndex = i;
                break;
            }
        }
        if (!validItem) {
            System.out.println("Invalid item number.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter any comment for the order: ");
        String comment = scanner.nextLine();

        // Store the order details in respective arrays
        orderTableNumbers[orderCount] = tableNumber;
        orderItemNames[orderCount] = MENU_ITEMS[arrayIndex];
        orderPrices[orderCount] = MENU_PRICES[arrayIndex];
        orderQuantities[orderCount] = quantity;
        orderItemNumbers[orderCount] = ITEM_NUMBER[arrayIndex];
        orderComments[orderCount] = comment;

        orderCount++;
        System.out.println("Order placed successfully.");
    }

    // Method to validate if the entered table number is in the list of valid table
    // numbers
    private static boolean validateTableNumber(int tableNumber) {
        for (int validTable : VALID_TABLE_NUMBERS) {
            if (validTable == tableNumber) {
                return true;
            }
        }
        return false;
    }

    // Method to display the invoice
    private static void displayInvoice() {
        if (orderCount == 0) {
            System.out.println("No orders to display.");
            return;
        }
        // Generate and format the current date
        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy");
        String formattedDate = sdf.format(new Date());
        // Invoice display formatting
        System.out.println("");
        System.out.printf(" %-8s %-25s %-8s %-6s %-10s %n", "Order#", "101", "Table#", "2", " ");
        System.out.printf(" %-8s %-25s %-8s %-6s %-10s %n", "Date", formattedDate, "Staff:", "Jill", " ");
        System.out.println("");
        System.out.printf(" %-8s %-25s %-8s %-6s %-10s %n", "Item", "Name", "Qty", "Price", "Sub-total");
        double total = 0.0;

        // Loop through each order and calculate the subtotal
        for (int i = 0; i < orderCount; i++) {
            double subTotal = orderPrices[i] * orderQuantities[i];

            System.out.printf(" %-8s %-25s %-8s %-6s %-10s %n", orderItemNumbers[i],
                    orderItemNames[i] + " (" + orderComments[i] + ")",
                    orderQuantities[i], orderPrices[i], subTotal);
            total += subTotal;
        }

        // Calculate VAT and net amount
        double vat = total * (VAT_RATE / (1 + VAT_RATE));
        double netAmount = total - vat;
        // Display the total, VAT, and net amount
        System.out.println("");
        System.out.printf(" %-8s %-25s %-8s %-6s %-10s %n", " ", " ", " ", "Total:", total);
        System.out.printf(" %-8s %-25s %-8s %-6s %-10s %n", " ", " ", " ", "VAT (20%%):", vat);
        System.out.printf(" %-8s %-25s %-8s %-6s %-10s %n", " ", " ", " ", "Net Amount:", netAmount);
    }
}

