package edu.uga.cs.roomateshopping;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddShoppingItem extends AppCompatActivity {


    private static final String TAG = "AddShoppingItem";

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private EditText itemView;
    private EditText priceView;
    private Button button;


    @Override
    public void onCreate( Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.add_shopping_item);
        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        navigationView = findViewById( R.id.nvView );
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem( menuItem );
                    return true;
                });
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
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close );
    }

    public void selectDrawerItem( MenuItem menuItem ) {

        // Create a new fragment based on the used selection in the nav drawer


        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_add) {
            Intent intent = new Intent(this, AddShoppingItem.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_list) {
            Intent intent = new Intent(this, ReviewItems.class);
            startActivity(intent);

        } else if (itemId == R.id.menu_help) {
            Intent intent = new Intent(this, PurchasedItems.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_bucket) {
            Intent intent = new Intent(this, ShoppingBucket.class);
            startActivity(intent);
        } else if (itemId == R.id.menu_close) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            return;
        }




        /*
        // this is included here as a possible future modification
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked( true );
        // Set action bar title
        setTitle( menuItem.getTitle());
         */

        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }
}