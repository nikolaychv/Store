package com.citb408;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PayDesk {
    private List<GoodsForSale> markedProducts;
    private Cashier cashier;
    private Shop shop;

    public PayDesk(Shop shop) {
        markedProducts = new ArrayList<GoodsForSale>();
        this.shop = shop;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void addProduct(GoodsForSale product) {
        this.markedProducts.add(product);
    }

    public void removeProduct(GoodsForSale product) {
        this.markedProducts.remove(product);
    }

    public Cashier getCashier() {
        return cashier;
    }


    public List<GoodsForSale> getMarkedProducts() {
        return markedProducts;
    }

    public void productMarking(GoodsForSale goodsForSale, int amount) throws NotEnoughAmountException {
        if(shop.getPricesOfGoods().isExpirationDateExpired(goodsForSale)) {
            System.err.println("This product has expired! The product cannot be marked.");
            decreaseAmountOfGood(goodsForSale, amount);
        }
        else {
            if (this.getMarkedProducts().isEmpty()) {
                decreaseAmountOfGood(goodsForSale, amount);
                addProduct(new GoodsForSale(goodsForSale.getName(), goodsForSale.price, goodsForSale.getIsEatable(), amount, goodsForSale.getExpirationDate()));
            }
            else {
                boolean isThereSuchElement = false;
                for (GoodsForSale goods : markedProducts) {
                    if(goods.getName().equals(goodsForSale.getName())) {
                        decreaseAmountOfGood(goodsForSale, amount);
                        increaseAmountOfGood(goods, amount);
                        isThereSuchElement = true;
                        break;
                    }
                }
                if(!isThereSuchElement) {
                    decreaseAmountOfGood(goodsForSale, amount);
                    addProduct(new GoodsForSale(goodsForSale.getName(), goodsForSale.price, goodsForSale.getIsEatable(), amount, goodsForSale.getExpirationDate()));
                }
            }
        }
    }

    public void decreaseAmountOfGood(GoodsForSale goodsForSale, int amount) throws NotEnoughAmountException {
        if (goodsForSale.getAmount() < amount) {
            throw new NotEnoughAmountException("Not enough amount of this goods.", (goodsForSale.getAmount()) - amount);
        }
        else {
            goodsForSale.setAmount(goodsForSale.getAmount() - amount);
        }
    }

    public void increaseAmountOfGood(GoodsForSale goodsForSale, int amount) {
        goodsForSale.setAmount(goodsForSale.getAmount() + amount);
    }

    public void addToSoldGoods(GoodsForSale markedProducts) {
        if (shop.getSoldGoods().isEmpty()) {
            shop.getSoldGoods().add(markedProducts);
        }
        else {
            boolean isThereSuchElement = false;
            for (GoodsForSale goods : shop.getSoldGoods()) {
                if(goods.getName().equals(markedProducts.getName())) {
                    increaseAmountOfGood(goods, markedProducts.getAmount());
                    isThereSuchElement = true;
                    break;
                }
            }
            if (!isThereSuchElement) {
                shop.getSoldGoods().add(markedProducts);
            }
        }
    }

    public double sumOfProducts() {
        double sum = 0.0;
        // we can also use for-each
        for (int i = 0; i < this.getMarkedProducts().size(); i++) {
            sum += (this.getMarkedProducts().get(i).getFinalPrice() * this.getMarkedProducts().get(i).getAmount());
        }
        return sum;
    }

    public void createSalesReceipt() {
        shop.getSalesReceipts().add(new SalesReceipt(this.getCashier(), LocalDateTime.now(), this.getMarkedProducts(), this));
        System.out.println();
        System.out.println(shop.getSalesReceipts().get(shop.getSalesReceipts().size() - 1));
    }

    public void returnTheGoodsBackForSale(List<GoodsForSale> goodsForSale) {
        for (GoodsForSale marked : getMarkedProducts()) {
            for (GoodsForSale goods : goodsForSale) {
                if (marked.getName().equals(goods.getName())) {
                    goods.amount += marked.getAmount();
                    marked.amount = 0;
                }
            }
        }
    }

    public void clearMarkedProducts() {
        this.markedProducts.clear();
    }

    public void removalFinishedGoods(List<GoodsForSale> goodsForSale) {
        for (GoodsForSale goods : goodsForSale) {
            if(goods.getAmount() == 0) {
                goodsForSale.remove(goods);
            }
        }
    }

    public void moneyCheck(double money, List<GoodsForSale> goodsForSale) throws NotEnoughMoneyException {
        if (money < this.sumOfProducts()) {
            returnTheGoodsBackForSale(goodsForSale);
            clearMarkedProducts();
            throw new NotEnoughMoneyException("Not enough money!");
        }
        else {
            createSalesReceipt();
            for (GoodsForSale goods : getMarkedProducts()) {
                addToSoldGoods(goods);
            }
            removalFinishedGoods(goodsForSale);
            clearMarkedProducts();
        }
    }

    @Override
    public String toString() {
        return "PayDesk{" +
                "markedProducts=" + markedProducts +
                ", cashier=" + cashier.getName() +
                ", shop=" + shop.getName() +
                '}';
    }
}