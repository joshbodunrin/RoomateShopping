package edu.uga.cs.roomateshopping;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PurchasedItems extends AppCompatActivity
        implements EditItemDialogFragment.EditItemDialogListener{

    public static final String TAG = "ReviewItemsFragment";

    private RecyclerView recyclerView;
    private ItemListRecyclerAdapter recyclerAdapter;

    private List<Item> itemsList;

    private FirebaseDatabase database;





    @Override
    public void onCreate( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_items);

        Log.d(TAG, "onViewCreated()" );

        recyclerView = findViewById(R.id.recyclerView);

        itemsList = new ArrayList<Item>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new ItemListRecyclerAdapter(itemsList, getApplicationContext(), PurchasedItems.this);
        recyclerView.setAdapter(recyclerAdapter);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("purchasedItems");


        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    //Log.d(TAG, "KEY VALUE ADDED HERE: " + postSnapshot.getKey());
                    item.setKey(postSnapshot.getKey());
                    itemsList.add(item);
                    Log.d(TAG, "ValueEventListener: adder: " + item);
                    Log.d(TAG, "ValueEventListener: key: " + postSnapshot.getKey());

                }

                Log.d(TAG, "ValueEventListener: notifiying recylerAdapter");
                recyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("ValueEventListener: reading failed: " + databaseError.getMessage());

            }

        } );





    }





    public void updateItem(int position, Item item, int action) {
        if (action == EditItemDialogFragment.DELETE) {
            Log.d(TAG, "Deleting item at: " + position + "(" + item.getName() + ")");

            // remove deleted item from list
            itemsList.remove(position);

            //update recytler to show changes
            recyclerAdapter.notifyItemRemoved(position);



            //update item in Firebase
            DatabaseReference ref = database.getReference().child("purchasedItems").child(item.getKey());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "deleted item at: " + position + "(" + item.getName() + ")");
                            Toast.makeText(getApplicationContext(), "Item deleted: " + item.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d(TAG, "failed to deleted item at: " + position + "(" + item.getName() + ")");
                    Toast.makeText(getApplicationContext(), "Failed to dlete " + item.getName(),
                            Toast.LENGTH_SHORT).show();

                }
            });


        } else if (action == EditItemDialogFragment.SAVE) {
            Log.d(TAG, "Updating item at: " + position + "(" + item.getName() + ")");

            //update recytler to show changes
            recyclerAdapter.notifyItemChanged(position);

            //update item in Firebase
            DatabaseReference ref = database.getReference().child("purchasedItems").child(item.getKey());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "updated item at: " + position + "(" + item.getName() + ")");
                            Toast.makeText(getApplicationContext(), "Item updated: " + item.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d(TAG, "failed to update item at: " + position + "(" + item.getName() + ")");
                    Toast.makeText(getApplicationContext(), "Failed to update " + item.getName(),
                            Toast.LENGTH_SHORT).show();

                }
            });



        } /*else if (action == -1) {

            Item oldItem = itemsList.get(position);
            Item purchasedItem = new Item(oldItem.getName(),oldItem.getPrice());
            DatabaseReference ref = database.getReference("purchasedItems");
            ref.push().setValue(purchasedItem)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // confirmation
                            Toast.makeText(getApplicationContext(), "New item added to list:  " + item.getName(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to add item",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            ref = database.getReference().child("shoppingBucket").child(item.getKey());

            // remove deleted item from list
            itemsList.remove(position);

            //update recytler to show changes
            recyclerAdapter.notifyItemRemoved(position);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    snapshot.getRef().setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "updated item at: " + position + "(" + item.getName() + ")");
                            Toast.makeText(getApplicationContext(), "Item updated: " + item.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d(TAG, "failed to update item at: " + position + "(" + item.getName() + ")");
                    Toast.makeText(getApplicationContext(), "Failed to update " + item.getName(),
                            Toast.LENGTH_SHORT).show();


                }
            });

        }
        */
    }

}
