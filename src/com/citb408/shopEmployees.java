package com.citb408;

public abstract class shopEmployees implements Employees{
    private String name;
    private int id;
    private Position position;
    private static int counter = 0;

    public shopEmployees() {
    }

    public shopEmployees(String name, Position position) {
        this.name = name;
        shopEmployees.counter += 1;
        this.id = counter;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", id=" + id +
                ", position=" + position +
                '}';
    }
}
