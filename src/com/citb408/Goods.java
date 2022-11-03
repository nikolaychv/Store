package com.citb408;

public abstract class Goods {
   protected int id;
   protected String name;
   protected double price;
   protected GoodsCategory isEatable;
   protected int amount;
   protected static int id_counter = 0;

    public Goods() {
    }

    public Goods(String name, double price, GoodsCategory isEatable, int amount) {
        Goods.id_counter += 1;
        this.id = id_counter;
        this.name = name;
        this.price = price;
        this.isEatable = isEatable;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public GoodsCategory getIsEatable() {
        return isEatable;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isEatable=" + isEatable +
                '}';
    }
}