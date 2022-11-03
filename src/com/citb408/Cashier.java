package com.citb408;

public class Cashier extends shopEmployees{
    private double monthlySalary;
    private Shop shop;

    public Cashier() {
    }

    public Cashier(String name, Position position, Shop shop) {
        super(name, position);
        salary();
        this.shop = shop;
    }

    public void salary() {
        this.monthlySalary = 700;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "Shop=" + shop.getName() +
                ", " + super.toString();
    }
}