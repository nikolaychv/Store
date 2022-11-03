package com.citb408;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "NotEnoughMoneyException";
    }
}
