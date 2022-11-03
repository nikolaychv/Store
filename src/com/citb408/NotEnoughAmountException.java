package com.citb408;

public class NotEnoughAmountException extends Exception {
    private int amount;

    public NotEnoughAmountException(String message, int amount) {
        super(message);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "NotEnoughAmountException{" +
                "amount=" + amount +
                '}';
    }
}
