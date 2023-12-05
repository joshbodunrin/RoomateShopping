package edu.uga.cs.roomateshopping;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddShoppingItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddShoppingItem extends Fragment {


    private static final String TAG = "AddShoppingItemFragment";


    private EditText itemView;
    private EditText priceView;

    private Button button;



    public AddShoppingItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddShoppingItem.
     */
    // TODO: Rename and change types and number of parameters
    public static AddShoppingItem newInstance(String param1, String param2) {
        AddShoppingItem fragment = new AddShoppingItem();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

         */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_shopping_item, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        itemView = view.findViewById(R.id.editTextText3);
        priceView = view.findViewById(R.id.editTextText4);
        button = view.findViewById(R.id.button3);

        button.setOnClickListener(new ButtonClickListener());

    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String item = itemView.getText().toString();
            String p = priceView.getText().toString();
            Log.d(TAG, "price string  " + p);
            double price = Double.parseDouble(p);
            final Item shoppingItem;
            shoppingItem = new Item(item, price);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("items");

            myRef.push().setValue(shoppingItem)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // confirmation
                            Toast.makeText(getContext(), "New item added to list:  " + shoppingItem.getName(),
                                    Toast.LENGTH_SHORT).show();

                            itemView.setText("");
                            priceView.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to add item",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }
}