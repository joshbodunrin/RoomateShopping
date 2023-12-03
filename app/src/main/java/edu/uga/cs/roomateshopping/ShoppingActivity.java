package edu.uga.cs.roomateshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingActivity extends AppCompatActivity {

    private Button viewList;
    private Button myList;
    private Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        viewList = findViewById(R.id.roomShoppingList);
        myList = findViewById(R.id.myShoppingList);
        logOut = findViewById(R.id.logOut);

        logOut.setOnClickListener(new LogOutButtonClickListener());

    }

    private class LogOutButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start user registration
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}