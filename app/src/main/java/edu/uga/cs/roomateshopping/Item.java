package edu.uga.cs.roomateshopping;

/**
 * Represents shopping item to be added, edited, and deleted
 */
public class Item {

    private String key; // not exactly what key is used for

    private String name;

    private double price;

    private String description; // may not be needed

    private String buyer; // represent who purchased, may need to change to diff
                          // thinking of user email right now

    public Item() {
        this.key = null;
        this.name = null;
        this.price = 0;
        this.description = null;
        this.buyer = null;
    }


    public Item(String name) {
        this.key = null;
        this.name = name;
        this.price = 0;
        this.description = null;
        this.buyer = null;
    }
    public Item(String name, double price, String description) {
        this.key = null;
        this.name = name;
        this.price = price;
        this.description = description;
        this.buyer = null;
    }
    // going to need to add all getters and setters
}


