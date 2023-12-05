package edu.uga.cs.roomateshopping;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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


public class AddShoppingItem extends AppCompatActivity {


    private static final String TAG = "AddShoppingItem";


    private EditText itemView;
    private EditText priceView;

    private Button button;


    @Override
    public void onCreate( Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.add_shopping_item);
        itemView = findViewById(R.id.editTextText3);
        priceView = findViewById(R.id.editTextText4);
        button = findViewById(R.id.button3);

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
                            Toast.makeText(v.getContext(), "New item added to list:  " + shoppingItem.getName(),
                                    Toast.LENGTH_SHORT).show();

                            itemView.setText("");
                            priceView.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Failed to add item",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }
}