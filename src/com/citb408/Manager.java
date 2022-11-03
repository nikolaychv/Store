package com.citb408;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Manager extends shopEmployees {
    private double monthlySalary;
    private Shop shop;

    public Manager() {
    }

    public Manager(String name, Position position, Shop shop) {
        super(name, position);
        salary();
        this.shop = shop;
    }

    public void salary() {
        this.monthlySalary = 1000;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "monthlySalary=" + monthlySalary +
                ", shop=" + shop +
                ", " + super.toString();
    }
}