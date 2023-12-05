package edu.uga.cs.roomateshopping;

/**
 * Represents shopping item to be added, edited, and deleted
 */
public class Item {

    private String key; // not exactly what key is used for

    private String name;

    private double price;

    private String buyer; // represent who purchased, may need to change to diff
                          // thinking of user email right now

    public Item() {
        this.key = null;
        this.name = null;
        this.price = 0;
        this.buyer = null;
    }
    public Item(String name) {
        this.key = null;
        this.name = name;
        this.price = 0;
        this.buyer = "Not Yet Claimed!";
    }
    public Item(String name, double price) {
        this.key = null;
        this.name = name;
        this.price = price;
        this.buyer = "Not Yet Claimed!";
    }

    //getters and setters for instance variables

    public double getPrice() {
        return price;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}


