package com.citb408;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void cashier_menu(Shop shop, PayDesk payDesk) throws NotEnoughAmountException, NotEnoughMoneyException {
        Scanner choice = new Scanner(System.in);
        shop.printCashiers();
        int cashier_choice;
        do {
            System.out.println("Which cashier do you choose?:");
            cashier_choice = choice.nextInt();
        } while(cashier_choice > shop.getCashiers().size() || cashier_choice < 1);

        payDesk.setCashier(shop.getCashiers().get(cashier_choice - 1));

        System.out.println(payDesk);
        System.out.println(payDesk.getCashier());
        System.out.println();

        shop.printGoodsForSale();

        System.out.println("Choose id and amount / or '0' to finish /:");

        int id_choice, amount_choice;

        do {
                System.out.println("Id:");
                id_choice = choice.nextInt();

                if (id_choice == 0 && payDesk.getMarkedProducts().isEmpty()) {
                    break;
                }
                else if (id_choice == 0 && payDesk.getMarkedProducts().size() != 0) {
                    System.out.println(payDesk.getMarkedProducts());
                    System.out.println("Price: ");
                    DecimalFormat df = new DecimalFormat("#.##");
                    System.out.println(df.format(payDesk.sumOfProducts()));
                    double clients_money;
                    System.out.println("Enter client's money: ");
                    clients_money = choice.nextDouble();

                    payDesk.moneyCheck(clients_money, shop.getGoodsForSales());
                    break;
                }

                System.out.println("Amount:");
                amount_choice = choice.nextInt();

                GoodsForSale product = shop.getGoodsForSales().get(id_choice - 1);
                payDesk.productMarking(product, amount_choice);
                System.out.println("Marked products: ");
                System.out.println(payDesk.getMarkedProducts());
        } while(id_choice != 0);

        choice.close();
    }


    public static void manager_options(Shop shop) {
        Scanner managerChoice = new Scanner(System.in);
        int manager_choice;
        do {
            do {
                System.out.println();
                System.out.println("~~~~~~~~~ M A N A G E R - M E N U ~~~~~~~~~~~");
                System.out.println("1. Adding goods");
                System.out.println("2. Information for cashiers");
                System.out.println("3. List of sold goods");
                System.out.println("4. List of delivered goods");
                System.out.println("5. Sales receipts issued");
                System.out.println("6. Expenses for salaries of cashiers");
                System.out.println("7. Expenses for salaries of managers");
                System.out.println("8. Costs from delivery of goods");
                System.out.println("9. Income from sold goods");
                System.out.println("10. The profit of the store");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.printf("Enter an operation (or '0' to exit):");
                manager_choice = managerChoice.nextInt();

                switch (manager_choice) {
                    case 1: {
                        shop.enterGoodsForSale();
                        break;
                    }
                    case 2: {
                        shop.printCashiers();
                        break;
                    }
                    case 3: {
                        shop.printSoldGoods();
                        break;
                    }
                    case 4: {
                        shop.printGoodsForSale();
                        break;
                    }
                    case 5: {
                        shop.printSalesReceipts();
                        break;
                    }
                    case 6: {
                        System.out.println("Result: ");
                        System.out.println(shop.cashiersSalaries());
                        break;
                    }
                    case 7: {
                        System.out.println("Result: ");
                        System.out.println(shop.managersSalaries());
                        break;
                    }
                    case 8: {
                        System.out.println("Result: ");
                        System.out.println(shop.costsGoodsForSale());
                        break;
                    }
                    case 9: {
                        System.out.println("Result: ");
                        System.out.println(shop.incomeFromSoldGoods());
                        break;
                    }
                    case 10: {
                        System.out.println("Result: ");
                        double profit = shop.incomeFromSoldGoods() - shop.costsGoodsForSale() - shop.cashiersSalaries() - shop.managersSalaries();
                        System.out.println(profit);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } while (manager_choice > 10 || manager_choice < 0);

        } while (manager_choice != 0);
        managerChoice.close();
    }

    public static int manager_menu(Shop shop, List<PayDesk> payDesks, List<Cashier> cashiers) {
        Scanner readerPassword = new Scanner(System.in);
        int p = 2021;
        int p_choice;
        do {
            System.out.printf("Enter a password: ");
            p_choice = readerPassword.nextInt();
            if (p_choice != p) {
                System.out.println("If you want to end the program press 0.");
            }
            if (p_choice == 0) {
                return 0;
            }
        } while (p_choice != p);

        manager_options(shop);

        readerPassword.close();
        return 1;
    }

    public static void main(String[] args) throws NotEnoughAmountException, NotEnoughMoneyException {
        // Shop
        Shop my_shop = new Shop("MY GROCERY STORE");

        // Cashiers
        Cashier first_cashier = new Cashier("Ivan Ivanov", Position.CASHIER, my_shop);
        my_shop.addCashier(first_cashier);

        Cashier second_cashier = new Cashier("Petur Petrov", Position.CASHIER, my_shop);
        my_shop.addCashier(second_cashier);

        Cashier third_cashier = new Cashier("Krum Petkov", Position.CASHIER, my_shop);
        my_shop.addCashier(third_cashier);

        // Managers
        Manager first_manager = new Manager("Angel Angelov", Position.MANAGER, my_shop);
        my_shop.addManager(first_manager);

        // If you want to have a discount before the expiration date of the goods
        DiscountUponExpiration discountUponExpiration = new DiscountUponExpiration(my_shop, 35, 50);
        my_shop.setPricesOfGoods(discountUponExpiration);

        // If you want to have a discount before the expiration date of the goods
            for (GoodsForSale goods : my_shop.getGoodsForSales()) {
                if (discountUponExpiration.isExpirationDateExpired(goods)) {
                    goods.setDiscountUponExpiration(discountUponExpiration);
                }
            }

        // Markup percentage in the store
        GoodsCategory.FOODSTUFF.setPercentage(20);
        GoodsCategory.NONFOOD_STUFF.setPercentage(20);

        // Goods for sale
        GoodsForSale goods1 = new GoodsForSale("Bananas", 1.04, GoodsCategory.FOODSTUFF, 20, LocalDate.of(2022, 1, 1));
        my_shop.addGoodsForSale(goods1);
        GoodsForSale goods2 = new GoodsForSale("Apples", 0.92, GoodsCategory.FOODSTUFF, 15, LocalDate.of(2022, 1, 1));
        my_shop.addGoodsForSale(goods2);
        GoodsForSale goods3 = new GoodsForSale("Pears", 1.00, GoodsCategory.FOODSTUFF, 40, LocalDate.of(2021, 12, 14));
        my_shop.addGoodsForSale(goods3);
        GoodsForSale goods4 = new GoodsForSale("Milk", 1.50, GoodsCategory.FOODSTUFF, 10, LocalDate.of(2021, 11, 1));
        my_shop.addGoodsForSale(goods4);
        GoodsForSale goods5 = new GoodsForSale("Cheese", 1.50, GoodsCategory.FOODSTUFF, 10, LocalDate.of(2021, 11, 3));
        my_shop.addGoodsForSale(goods5);
        GoodsForSale goods6 = new GoodsForSale("Napkins", 0.90, GoodsCategory.NONFOOD_STUFF, 10, LocalDate.of(2025, 11, 4));
        my_shop.addGoodsForSale(goods6);
        GoodsForSale goods7 = new GoodsForSale("Toothpaste", 1.20, GoodsCategory.NONFOOD_STUFF, 10, LocalDate.of(2023, 11, 5));
        my_shop.addGoodsForSale(goods7);
        GoodsForSale goods8 = new GoodsForSale("Тoothpicks", 0.90, GoodsCategory.NONFOOD_STUFF, 10, LocalDate.of(2025, 11, 13));
        my_shop.addGoodsForSale(goods8);
        GoodsForSale goods9 = new GoodsForSale("Bread", 1.00, GoodsCategory.FOODSTUFF, 10, LocalDate.of(2021, 7, 7));
        my_shop.addGoodsForSale(goods9);
        GoodsForSale goods10 = new GoodsForSale("Ham", 2.00, GoodsCategory.FOODSTUFF, 10, LocalDate.of(2020, 11, 11));
        my_shop.addGoodsForSale(goods10);

        // PayDesks
        PayDesk first_paydesk = new PayDesk(my_shop);
        my_shop.addPayDesk(first_paydesk);

        PayDesk second_paydesk = new PayDesk(my_shop);
        my_shop.addPayDesk(second_paydesk);

            int choice;
            // int continue_program;
            Scanner reader = new Scanner(System.in);
            do {
                System.out.println("Select a position in the store: ");
                System.out.println("1. Cashier");
                System.out.println("2. Manager");
                System.out.printf("Enter: ");
                choice = reader.nextInt();
                if (choice == 1) {
                    System.out.println();
                    System.out.println("Selected: Cashier");
                    if (first_paydesk.getCashier() == null) {
                        cashier_menu(my_shop, first_paydesk);
                    } else if (first_paydesk.getCashier() != null && second_paydesk.getCashier() == null) {
                        cashier_menu(my_shop, second_paydesk);
                    } else {
                        System.err.println("Тhere are no free cash registers!");
                        break;
                    }
                } else if (choice == 2) {
                    System.out.println();
                    System.out.println("Selected: Manager");
                    manager_menu(my_shop, my_shop.getPayDesks(), my_shop.getCashiers());
                }

                /*
                System.out.println("Do you want to continue the program?");
                System.out.println("If you want, press 3: ");
                continue_program = reader.nextInt();
                */
            } while ((choice > 2 || choice < 1)); // || (continue_program == 3));
        reader.close();
    }
}