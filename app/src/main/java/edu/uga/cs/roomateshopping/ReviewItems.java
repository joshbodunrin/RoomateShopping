package edu.uga.cs.roomateshopping;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewItems extends Fragment {


    public static final String TAG = "ReviewItemsFragment";

    private RecyclerView recyclerView;
    private ItemListRecyclerAdapter recyclerAdapter;

    private List<Item> itemsList;
    private FloatingActionButton floatingButton;
    private FirebaseDatabase database;


    public ReviewItems() {

    }

    public static ReviewItems newInstance(String param1, String param2) {
        ReviewItems fragment = new ReviewItems();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_review_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        Log.d(TAG, "onViewCreated()" );

        recyclerView = view.findViewById(R.id.recyclerView);

        itemsList = new ArrayList<Item>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new ItemListRecyclerAdapter(itemsList, getContext());
        recyclerView.setAdapter(recyclerAdapter);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");


        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
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


        // get a Firebase DB instance reference
        //database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("items");
    }

}
