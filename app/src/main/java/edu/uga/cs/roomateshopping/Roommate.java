package edu.uga.cs.roomateshopping;

import java.util.ArrayList;

/**
 * Represents a Roommate who has a cart of items
 */
public class Roommate {

    private String email;

    private ArrayList<Item> purchasing;

    /**
     * default constructor
     */
    public Roommate(){
        email = "";

        purchasing = null;
    }

    /**
     * constructor.
     * @param email the user's email.
     * @param name the user's given name.
     */
    public Roommate(String email)
    {
        this.email = email;
        purchasing = new ArrayList<Item>();
    }

}
