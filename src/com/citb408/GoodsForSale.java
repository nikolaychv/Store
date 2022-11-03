package com.citb408;

import java.text.DecimalFormat;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class GoodsForSale extends Goods{

    protected double finalPrice;
    protected LocalDate expirationDate;

    public GoodsForSale(String name, double price, GoodsCategory isEatable, int amount, LocalDate expirationDate) {
        super(name, price, isEatable, amount);
        this.expirationDate = expirationDate;
        setFinalPrice(isEatable, price);
    }

    public void setFinalPrice(GoodsCategory isEatable, double price) {
        double final_price = price;
        double markup;
        if (isEatable == GoodsCategory.FOODSTUFF) {
            markup = price * (GoodsCategory.FOODSTUFF.getPercentage() / 100);
            final_price += markup;
        }
        else {
            markup = price * (GoodsCategory.NONFOOD_STUFF.getPercentage() / 100);
            final_price += markup;
        }
        this.finalPrice = final_price;
    }

    public void setFinalPrice(double price) {
        this.finalPrice = price;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setDiscountUponExpiration(DiscountUponExpiration discountUponExpiration) {
        LocalDate dateNow = LocalDate.now();
        LocalDate dateExpiration = this.getExpirationDate();
        long daysBetween = DAYS.between(dateNow, dateExpiration);
        double final_price = this.getFinalPrice();
        if (daysBetween < discountUponExpiration.getDaysToExpiration()) {
            double markup;
            markup = this.getFinalPrice() * (discountUponExpiration.getDiscount() / 100);
            final_price -= markup;
        }
        this.setFinalPrice(final_price);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return  '\n' + "Name:'" + name + '\'' +
                ", ID:" + id  +
                ", Amount:" + amount +
                ", Final price:" + df.format(finalPrice) +
                ", Expiration Date: " + expirationDate + '\n';
    }
}