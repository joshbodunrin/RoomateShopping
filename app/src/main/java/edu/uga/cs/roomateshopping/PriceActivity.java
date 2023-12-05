package edu.uga.cs.roomateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class PriceActivity extends AppCompatActivity {
    private List<Item> itemsList;

    private FirebaseDatabase database;

    private FirebaseAuth auth;

    private TextView priceOwed;

    private String email;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        itemsList = new ArrayList<Item>();
        priceOwed = findViewById(R.id.textView6);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("purchasedItems");
        int users = 5;

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //itemsList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    //Log.d(TAG, "KEY VALUE ADDED HERE: " + postSnapshot.getKey());
                    item.setKey(postSnapshot.getKey());
                    itemsList.add(item);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("ValueEventListener: reading failed: " + databaseError.getMessage());

            }

        } );

        double totalPrice = 0;
        double myPrice = 0;


        for (Item item: itemsList) {
            totalPrice += item.getPrice();
            if (item.getBuyer() == email)
                myPrice += item.getPrice();
        }
        double finalPrice = (totalPrice/users) - myPrice;
        priceOwed.setText(Double.toString(finalPrice));
    }
}