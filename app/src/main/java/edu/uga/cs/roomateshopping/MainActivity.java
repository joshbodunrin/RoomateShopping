package edu.uga.cs.roomateshopping;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "RoommateShopping";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "RoomateShopping: MainActivity.onCreate()");

        Button loginButton = findViewById(R.id.button4);

        Button registerButton = findViewById(R.id.button5);

        loginButton.setOnClickListener(new LoginButtonClickListener() );
        registerButton.setOnClickListener(new RegisterButtonClickListener() );

}
    private class LoginButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());

            Log.d(TAG, "MainActivity.LoginButtonClickListener: Logging in started");

            // intent for firebaase signin
            Intent loginIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            loginLauncher.launch(loginIntent);
        }

        //Log.d(TAG, "MainActivity.LoginButtonClickListener: Logging in started");

    }

    private ActivityResultLauncher<Intent> loginLauncher =
            registerForActivityResult(
                    new FirebaseAuthUIActivityResultContract(),
                    new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                        @Override
                        public void onActivityResult(FirebaseAuthUIAuthenticationResult o) {
                            onLoginResult(o);
                        }
                    }
            );

    private void onLoginResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // SUCCESSFUL login
            if (response != null) {
                Log.d(TAG, "MainActivity.onLoginResult: response.getEmail(): " + response.getEmail());
            }

            Intent intent = new Intent(this, ShoppingActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "MainActivity.onLoginResult: Failed to login");
            Toast.makeText(getApplicationContext(),
                    "Login Failed, ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start user registration
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}

