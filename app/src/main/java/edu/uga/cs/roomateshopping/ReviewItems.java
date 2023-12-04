package edu.uga.cs.roomateshopping;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReviewItems extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemListRecyclerAdapter recyclerAdapter;

    private List<Item> itemsList;

    private FirebaseDatabase database;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_items);

    }

}
