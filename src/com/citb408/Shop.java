package com.citb408;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {
    private String name;
    private List<PayDesk> payDesks;
    private List<SalesReceipt> salesReceipts;
    private List<GoodsForSale> goodsForSales;
    private List<GoodsForSale> soldGoods;
    private List<Cashier> cashiers;
    private List<Manager> managers;
    private DiscountUponExpiration pricesOfGoods;

    public Shop(String name) {
        this.name = name;
        payDesks = new ArrayList<PayDesk>();
        salesReceipts = new ArrayList<SalesReceipt>();
        goodsForSales = new ArrayList<GoodsForSale>();
        soldGoods = new ArrayList<GoodsForSale>();
        cashiers = new ArrayList<Cashier>();
        managers = new ArrayList<Manager>();
    }

    public String getName() {
        return name;
    }

    public void setPricesOfGoods(DiscountUponExpiration pricesOfGoods) {
        this.pricesOfGoods = pricesOfGoods;
    }

    public void addPayDesk(PayDesk payDesk) {
        this.payDesks.add(payDesk);
    }

    public void removePayDesk(PayDesk payDesk) {
        this.payDesks.remove(payDesk);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        this.salesReceipts.add(salesReceipt);
    }

    public void removeSalesReceipt(SalesReceipt salesReceipt) {
        this.salesReceipts.remove(salesReceipt);
    }

    public void addGoodsForSale(GoodsForSale product) {
        this.goodsForSales.add(product);
    }

    public void removeGoodsForSale(GoodsForSale product) {
        this.goodsForSales.remove(product);
    }

    public void addManager(Manager manager) {
        this.managers.add(manager);
    }

    public void removeManager(Manager manager) {
        this.managers.remove(manager);
    }

    public void addCashier(Cashier cashier) {
        this.cashiers.add(cashier);
    }

    public void removeCashier(Cashier cashier) {
        this.cashiers.remove(cashier);
    }

    public void addSoldGood(GoodsForSale product) {
        this.soldGoods.add(product);
    }

    public void removeSoldGood(GoodsForSale soldGoods) {
        this.soldGoods.remove(soldGoods);
    }

    public List<PayDesk> getPayDesks() {
        return payDesks;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public List<GoodsForSale> getGoodsForSales() {
        return goodsForSales;
    }

    public DiscountUponExpiration getPricesOfGoods() {
        return pricesOfGoods;
    }

    public List<SalesReceipt> getSalesReceipts() {
        return salesReceipts;
    }

    public List<GoodsForSale> getSoldGoods() {
        return soldGoods;
    }

    public void printGoodsForSale() {
        System.out.println("The goods for sale: ");
        for (GoodsForSale goods : goodsForSales) {
            System.out.println(goods);
        }
    }

    public void printSoldGoods() {
        System.out.println("Sold goods: ");
        for (GoodsForSale goods : soldGoods) {
            System.out.println(goods);
        }
    }

    /*
    public void printPayDesks() {
        System.out.println("Cash registers: ");
        int i = 1;
        for (PayDesk payDesk : payDesks) {
            System.out.println(i + ". " + payDesk);
            System.out.println("");
            i++;
        }
    }
    */

    public void printCashiers() {
        System.out.println("Cashiers: ");
        int i = 1;
        for (Cashier cashier : cashiers) {
            System.out.println(i + ". " + cashier);
            System.out.println("");
            i++;
        }
    }

    public void printSalesReceipts() {
        System.out.println("Sales receipts: ");
        for (SalesReceipt salesReceipt : salesReceipts) {
            System.out.println(salesReceipt);
        }
    }

    public double cashiersSalaries() {
        double counter_salaries = 0.0;
        for (Cashier cashier : cashiers) {
            counter_salaries += cashier.getMonthlySalary();
        }
        return counter_salaries;
    }

    public double managersSalaries() {
        double counter_salaries = 0.0;
        for (Manager manager : managers) {
            counter_salaries += manager.getMonthlySalary();
        }
        return counter_salaries;
    }

    public double incomeFromSoldGoods() {
        double income = 0.0;
        for (GoodsForSale goods : soldGoods) {
            income += goods.getFinalPrice() * goods.getAmount();
        }
        return income;
    }

    public double costsGoodsForSale() {
        double costs_counter = 0.0;
        for (GoodsForSale goods : goodsForSales) {
            costs_counter += goods.getPrice() * goods.getAmount();
        }
        return costs_counter;
    }

    /*
    public boolean isSameID(int id, List<GoodsForSale> goodsForSales) {
        for (GoodsForSale goods: goodsForSales) {
            if (goods.getId() == id) {
                return true;
            }
        }
        return false;
    }
    */

    public void enterGoodsForSale() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter product information:");

        String goods_name;
        System.out.println("Name:");
        goods_name = reader.nextLine();

        double goods_price;
        System.out.println("Price:");
        goods_price = reader.nextDouble();

        GoodsCategory goods_category;
        int category_choice;
        do {
            System.out.println("Good category (foodstuff or not): ");
            System.out.println("Enter 1 for a foodstuff or 2 for non foodstuff: ");
            category_choice = reader.nextInt();
        } while (category_choice > 2 || category_choice < 1);

        if (category_choice == 1) {
            goods_category = GoodsCategory.FOODSTUFF;
        } else {
            goods_category = GoodsCategory.NONFOOD_STUFF;
        }

        int goods_amount;
        System.out.println("Amount:");
        goods_amount = reader.nextInt();

        System.out.println("Expiration date :");
        int day;
        System.out.println("Day: ");
        day = reader.nextInt();
        int month;
        System.out.println("Month: ");
        month = reader.nextInt();
        int year;
        System.out.println("Year: ");
        year = reader.nextInt();

        LocalDate goods_date = LocalDate.of(year, month, day);

        GoodsForSale goods = new GoodsForSale(goods_name, goods_price, goods_category, goods_amount, goods_date);
        addGoodsForSale(goods);

        System.out.println("All goods: ");
        for(GoodsForSale g : getGoodsForSales()) {
            System.out.println(g);
        }

        // reader.close();
    }

}