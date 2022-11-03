package com.citb408;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalesReceipt {
    private int id;
    private Cashier cashier;
    private String timeIssued;
    private List<GoodsForSale> soldGoodsList;
    private static int counter = 0;
    private PayDesk payDesk;

    public SalesReceipt() {
    }

    public SalesReceipt(Cashier cashier, LocalDateTime tempTime, List<GoodsForSale> soldGoods, PayDesk thePayDesk) {
        SalesReceipt.counter += 1;
        this.id = counter;
        this.cashier = cashier;
        LocalDateTime temp = tempTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        this.timeIssued = temp.format(formatter);
        this.payDesk = thePayDesk;
        this.soldGoodsList = new ArrayList<GoodsForSale>();
        for (GoodsForSale goods : soldGoods) {
            soldGoodsList.add(goods);
            payDesk.addToSoldGoods(goods);
        }
        String name = "salesreceipts/SalesReceipt#" + id + ".txt";
        fileCreator(name);
    }

    public void fileCreator(String name) {
        try (FileWriter file = new FileWriter(name)) {
            file.write(this.toString());
            file.write('\n' + "Number of sales receipts: " + counter);
        } catch (IOException fileNot) {
            fileNot.printStackTrace();
        }
    }

    public double salesReceiptPrice() {
        double sum = 0.0;
        for (GoodsForSale soldProduct : soldGoodsList) {
            sum += soldProduct.getFinalPrice() * soldProduct.getAmount();
        }
        return sum;
    }

    @Override
    public String toString() {
        String strPrice = String.format("%.2f", this.salesReceiptPrice());
        return "~~~~ SalesReceipt ~~~~" + '\n' +
                "ID:" + id + '\n' +
                "Cashier:" + cashier.getName() + '\n' +
                "Date and time:'" + timeIssued + '\'' + '\n' +
                "List of products: " + soldGoodsList + '\n' +
                "Price: " + strPrice + '\n';
    }
}