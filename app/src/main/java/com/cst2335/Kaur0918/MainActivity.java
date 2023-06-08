package com.cst2335.Kaur0918;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cst2335.Kaur0918.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private SharedPreferences prefs;

    private static String TAG = "MainActivity";
    private View loginButton;
    private static final String PREFS_NAME = "MyData";
    private static final String EMAIL_KEY = "LoginName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads an xml file
        setContentView(binding.getRoot());


        //loginButton = findViewById(R.id.button);
        // Initialize SharedPreferences
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        //setContentView(R.layout.activity_main);
        // Load the email address if available
        // String emailAddress = prefs.getString("LoginName", "");
        String emailAddress = prefs.getString(EMAIL_KEY, "");
        binding.editText.setText(emailAddress);


        binding.button.setOnClickListener(clk -> {
// Save the email address
            String email = binding.editText.getText().toString();
            saveEmailAddress(email);

            //from page one to page second
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            nextPage.putExtra("EmailAddress", binding.editText.getText().toString());
            //startActivity(nextPage);
            Intent next = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("Age", 18);
            nextPage.putExtra("Name", "Prabh");
            nextPage.putExtra("PostalCode", "K2C0C1");
            startActivity(nextPage);

        });

        Log.w("MainActivity", "In onCreate() - Loading Widgets");
        Log.d(TAG, "Message");


    }

    private void saveEmailAddress(String email) {
        // Get SharedPreferences editor
        SharedPreferences.Editor editor = prefs.edit();
        // Save the email address
        editor.putString(EMAIL_KEY, email);
        editor.putFloat("Hi",4.5f);
        editor.putInt("Age",35);
        // Apply the changes to SharedPreferences
        editor.apply();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity", "In onStart() - Loading Widgets" );

    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.w( "MainActivity", "In onResume() - Loading Widgets" );

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( "MainActivity", "In onPause() - Loading Widgets" );

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( "MainActivity", "In onStop() - Loading Widgets" );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( "MainActivity", "In onDestroy() - Loading Widgets" );

    }


}