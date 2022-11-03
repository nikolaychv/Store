package com.citb408;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class DiscountUponExpiration {
    private double discount; // in %
    private int daysToExpiration;
    private Shop shop;

    public DiscountUponExpiration() {
    }

    public DiscountUponExpiration(Shop shop, double discount, int daysToExpiration) {
        this.discount = discount;
        this.shop = shop;
        this.daysToExpiration = daysToExpiration;
    }

    public int getDaysToExpiration() {
        return daysToExpiration;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isExpirationDateExpired(GoodsForSale goodsForSale) {
        LocalDate dateNow = LocalDate.now();
        LocalDate dateExpiration = goodsForSale.getExpirationDate();

        return (dateNow.compareTo(dateExpiration) > 0);
    }

}
