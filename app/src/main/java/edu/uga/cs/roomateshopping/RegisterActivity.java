package edu.uga.cs.roomateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText emailEditText;

    private EditText passwordEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.editTextText);
        passwordEditText = findViewById(R.id.editTextText2);

        Button registerButton = findViewById(R.id.button2);
        registerButton.setOnClickListener(new RegisterButtonClickListener() );




    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // need to check if values are null/valid first
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();


            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            // creating user, automatically signs user in as well
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                "Registered user: " + email,
                                        Toast.LENGTH_SHORT).show();

                                Log.d(TAG, "createUserWithEmail: success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();

                                Intent intent = new Intent(RegisterActivity.this, ShoppingActivity.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "createUserWithEmail: failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}