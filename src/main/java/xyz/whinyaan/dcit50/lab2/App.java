package xyz.whinyaan.dcit50.lab2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    // Program Configurations:

    // When true, whenever the user inputs a balance lower than the price of
    // the cheapest item on the list, the program will prompt the user if
    // they still want to use the vending machine.
    public static final boolean LOOP_WHEN_NOT_ENOUGH_BALANCE = false;
    
    // When false, whenever the price of an item's price is more than the
    // balance, the item will not be displayed to the user.
    public static final boolean DISPLAY_ITEM_WHEN_ITEMS_PRICE_MORE_THAN_BALANCE = true;
    
    // In combination with `DISPLAY_ITEM_WHEN_ITEMS_PRICE_MORE_THAN_BALANCE`,
    // when true, whenever an item's price is more than the balance, it will
    // display "(price higher than balance)" besides the item.
    public static final boolean PRINT_PRICE_MORE_WHEN_ITEMS_PRICE_MORE_THAN_BALANCE = true;

    public static final Scanner scanner = new Scanner(System.in);

    public static final String CANNOT_BUY_CHEAPEST = "Your balance cannot buy"
        + " the cheapest item on the vending machine.";
    public static final String CHANGE_DISPENSED = "Ending purchase. Change will"
        + " be dispensed as coins.";
    public static final String INVALID_INPUT_NUMBER = "Invalid input. Please"
        + " enter a number.";
    public static final String PROMPT_ANOTHER_ITEM = "Do you want to buy"
        + " another item? ";
    public static final String PROMPT_CONTINUE_USING = "Do you want to continue"
        + " using the vending machine? ";
    
    public static final HashMap<String, Integer> ITEMS = new HashMap<>();

    // initialize variables

    public static float balanceFloat;
    public static int lowestPrice;

    public static void main(String[] args) {
        ITEMS.put("Soda", 25);
        ITEMS.put("Chips", 15);
        ITEMS.put("Chocolate", 20);

        lowestPrice = Collections.min(ITEMS.values());

        System.out.println("Welcome to Arriola's Vending Machine!");

        boolean loop = true;

        while (loop) {
            try {
                System.out.print("Enter your initial balance: ");
                String balance = scanner.nextLine();
                balanceFloat = Float.parseFloat(balance);
                if (lowestPrice <= balanceFloat) {
                    loop = false;
                } else {
                    if (balanceFloat < 0) {
                        System.out.println("Invalid input. Please enter a"
                            + " number greater than 0."
                        );
                    } else {
                        System.out.println(CANNOT_BUY_CHEAPEST);
                        System.out.println(
                            "Balance will be dispensed as coins."
                        );
                    }
                    if (LOOP_WHEN_NOT_ENOUGH_BALANCE) {
                        wantToContinue(PROMPT_CONTINUE_USING);
                    } else {
                        exit();
                    }
                    
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_NUMBER);
                wantToContinue(PROMPT_CONTINUE_USING);
            }
        }

        displayItems();
    }

    public static void wantToContinue(
        String prompt, String... messagesBeforeExit
    ) {
        boolean loop = true;

        while (loop) {
            System.out.print(prompt + "(yes/no): ");
            String answer = scanner.nextLine();
            switch (answer) {
                case "yes":
                    loop = false;
                    break;
                case "no":
                    for (String message : messagesBeforeExit) {
                        System.out.println(message);
                    }
                    exit();
                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }
        }
    }

    public static void exit() {
        System.out.println("Thank you for using the vending machine!");
        System.exit(0);
    }

    private static void displayItems() {
        while (true) {
            System.out.println("Available ITEMS:");
            int itemNumber = 1;
            for (HashMap.Entry<String, Integer> item : ITEMS.entrySet()) {
                int price = item.getValue();
                String printStr = itemNumber + ". " + item.getKey() + " - "
                        + price;
                if (balanceFloat >= price) {
                    System.out.print(printStr);
                } else if (DISPLAY_ITEM_WHEN_ITEMS_PRICE_MORE_THAN_BALANCE) {
                    System.out.print(printStr);
                    if (PRINT_PRICE_MORE_WHEN_ITEMS_PRICE_MORE_THAN_BALANCE) {
                        System.out.print(" (price higher than balance)");
                    }
                }
                System.out.println();
                itemNumber++;
            }

            System.out.print("Enter the item number you want to buy: ");
            String choice = scanner.nextLine();
            try {
                int selectedItem = Integer.parseInt(choice);

                priceChecker(selectedItem);
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_NUMBER);
            }

            if (balanceFloat > 0) {
                wantToContinue(PROMPT_ANOTHER_ITEM, CHANGE_DISPENSED);
            } else {
                wantToContinue(PROMPT_ANOTHER_ITEM);
            }
        }
    }

    private static void priceChecker(int selectedItem) {
        if (selectedItem > 0 && selectedItem <= ITEMS.size()) {
            String selectedKey = (String) ITEMS.keySet()
                    .toArray()[selectedItem - 1];
            int price = ITEMS.get(selectedKey);
            
            if (balanceFloat >= price) {
                balanceFloat -= price;
                
                System.out.println("Item purchased successfully. Your"
                        + " remaining balance is $"
                        + String.format("%.2f", balanceFloat));
                
                if (balanceFloat == 0) {
                    System.out.println("You have no remaining" +
                            " balance. Ending purchase.");
                    if (LOOP_WHEN_NOT_ENOUGH_BALANCE) {
                        wantToContinue(PROMPT_CONTINUE_USING);
                    } else {
                        exit();
                    }
                } else if (balanceFloat < lowestPrice) {
                    System.out.println(CANNOT_BUY_CHEAPEST);
                    System.out.println(CHANGE_DISPENSED);
                    if (LOOP_WHEN_NOT_ENOUGH_BALANCE) {
                        wantToContinue(PROMPT_CONTINUE_USING);
                    } else {
                        exit();
                    }
                }
            } else {
                System.out.println("The item's (" + selectedKey +
                        ") price ($" + price + ") is more than your " +
                                "balance ($" +
                        String.format("%.2f", balanceFloat) + ").");
            }
        } else {
            System.out.println("Invalid choice. Please select a valid" +
                    " item number.");
        }
    }
}
