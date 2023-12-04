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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ReviewItems extends Fragment {

    private RecyclerView recyclerView;
    private ItemListRecyclerAdapter recyclerAdapter;

    private List<Item> itemsList;
    private FloatingActionButton floatingButton;
    private FirebaseDatabase database;

    public ReviewItems()
    {

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
        return inflater.inflate(R.layout.fragment_add_shopping_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance){
        recyclerView = view.findViewById( R.id.recyclerView );

        FloatingActionButton floatingButton = view.findViewById(R.id.floatingActionButton);
        /*floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddJobLeadDialogFragment();
                newFragment.show( getSupportFragmentManager(), null);
            }
        });
*/
        // initialize the Job Lead list
        itemsList = new ArrayList<Item>();

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // the recycler adapter with job leads is empty at first; it will be updated later
        recyclerAdapter = new ItemListRecyclerAdapter( itemsList, view.getContext() );
        recyclerView.setAdapter( recyclerAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jobleads");
    }

}
