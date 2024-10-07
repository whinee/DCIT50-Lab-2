package xyz.whinyaan.dcit50.lab2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    public static void exit() {
        System.out.println("Thank you for using the vending machine!");
        System.exit(0);
    }

    public static void wantToContinue(
            String prompt, String... messagesBeforeExit
    ) {
        Scanner scanner = new Scanner(System.in);
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

    public static void main(String[] args) {
        // Program Configurations:

        // When true, whenever the user inputs a balance lower than the price of
        // the cheapest item on the list, the program will prompt the user if
        // they still want to use the vending machine.
        boolean loopWhenNotEnoughBalance = false;
        
        // When false, whenever the price of an item's price is more than the
        // balance, the item will not be displayed to the user.
        boolean displayItemWhenPriceMoreThanBalance = true;
        
        // In combination with `displayItemWhenPriceMoreThanBalance`, when true,
        // whenever an item's price is more than the balance, it will display
        // "(price higher than balance)" besides the item.
        boolean printPriceMoreWhenItemsPriceMoreThanBalance = true;

        Scanner scanner = new Scanner(System.in);

        boolean loop = true;
        float balanceFloat = 0;
        String cannotBuyCheapest = "Your balance cannot buy the cheapest" +
                                " item on the vending machine.";
        String changeDispensed = "Ending purchase. Change will be dispensed" +
                " as coins";
        String invalidInputNumber = "Invalid input. Please enter a number.";
        String promptAnotherItem = "Do you want to buy another item? ";
        String promptContinueUsing = "Do you want to continue using the vending"
                        + " machine? ";
        
        HashMap<String, Integer> items = new HashMap<>();
        items.put("Soda", 25);
        items.put("Chips", 15);
        items.put("Chocolate", 20);

        int lowestPrice = Collections.min(items.values());

        System.out.println("Welcome to Arriola's Vending Machine!");

        while (loop) {
            try {
                System.out.print("Enter your initial balance: ");
                String balance = scanner.nextLine();
                balanceFloat = Float.parseFloat(balance);
                if (lowestPrice <= balanceFloat) {
                    loop = false;
                } else {
                    if (balanceFloat < 0) {
                        System.out.println("Invalid input. Please enter a" + 
                                " number greater than 0."
                        );
                    } else {
                        System.out.println(cannotBuyCheapest);
                        System.out.println(
                                "Balance will be dispensed as coins."
                        );
                    }
                    if (loopWhenNotEnoughBalance) {
                        wantToContinue(promptContinueUsing);
                    } else {
                        exit();
                    }
                    
                }
            } catch (NumberFormatException e) {
                System.out.println(invalidInputNumber);
                wantToContinue(promptContinueUsing);
            }
        }

        while (true) {
            System.out.println("Available items:");
            int itemNumber = 1;
            for (HashMap.Entry<String, Integer> item : items.entrySet()) {
                int price = item.getValue();
                String printStr = itemNumber + ". " + item.getKey() + " - "
                            + price;
                if (balanceFloat >= price) {
                    System.out.print(printStr);
                } else if (displayItemWhenPriceMoreThanBalance) {
                    System.out.print(printStr);
                    if (printPriceMoreWhenItemsPriceMoreThanBalance) {
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

                if (selectedItem > 0 && selectedItem <= items.size()) {
                    String selectedKey = (String) items.keySet().
                            toArray()[selectedItem - 1];
                    int price = items.get(selectedKey);
                    
                    if (balanceFloat > price) {
                        balanceFloat -= price;

                        System.out.println("Item purchased successfully. Your"
                                + " remaining balance is $"
                                + String.format("%.2f", balanceFloat)
                        );

                        if (balanceFloat == 0) {
                            System.out.println("Your remaining balancing is" +
                                    " $0."
                            );
                            System.out.println("You have no remaining" + 
                                    " balance. Ending purchase."
                            );
                            if (loopWhenNotEnoughBalance) {
                                wantToContinue(promptContinueUsing);
                            } else {
                                exit();
                            }
                        } else if (balanceFloat < lowestPrice) {
                            System.out.println(cannotBuyCheapest);
                            System.out.println(changeDispensed);
                            if (loopWhenNotEnoughBalance) {
                                wantToContinue(promptContinueUsing);
                            } else {
                                exit();
                            }
                        }
                    } else {
                        System.out.println("The item's (" + selectedKey +
                                ") price ($" + price + ") is more than your " +
                                "balance ($" +
                                String.format("%.2f", balanceFloat) + ")."
                        );
                    }
                } else {
                    System.out.println("Invalid choice. Please select a valid" +
                            " item number."
                    );
                }
            } catch (NumberFormatException e) {
                System.out.println(invalidInputNumber);
            }

            if (balanceFloat > 0) {
                wantToContinue(promptAnotherItem, changeDispensed);
            } else {
                wantToContinue(promptAnotherItem);
            }
        }
    }
}
