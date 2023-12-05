package edu.uga.cs.roomateshopping;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class ShoppingActivity extends AppCompatActivity {

    public static final String TAG = "ShoppingActivity";

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView imageView;
    private ActionBarDrawerToggle drawerToggle;

    private Button viewList;
    private Button myList;
    private Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        //viewList = findViewById(R.id.roomShoppingList);
        //myList = findViewById(R.id.myShoppingList);
        //logOut = findViewById(R.id.logOut);

        //logOut.setOnClickListener(new LogOutButtonClickListener());

        toolbar = findViewById(R.id.toolbar);
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

        imageView = findViewById(R.id.imageView);


    }

    private class LogOutButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start user registration
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
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
            finish();
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