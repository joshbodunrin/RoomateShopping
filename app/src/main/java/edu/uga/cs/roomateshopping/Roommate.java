package edu.uga.cs.roomateshopping;

import java.util.ArrayList;

/**
 * Represents a Roommate who has a cart of items
 */
public class Roommate {

    private String email;
    private String name;
    private ArrayList<Item> purchasing;

    /**
     * default constructor
     */
    public Roommate(){
        email = "";
        name = "";
        purchasing = null;
    }

    /**
     * constructor.
     * @param email the user's email.
     * @param name the user's given name.
     */
    public Roommate(String email, String name)
    {
        this.email = email;
        this.name = name;
        purchasing = new ArrayList<Item>();
    }

}
