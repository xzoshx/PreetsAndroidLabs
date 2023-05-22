package com.cst2335.Kaur0918;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cst2335.Kaur0918.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parents onCreate()

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //loads XML on screen

        TextView mytext = variableBinding.textview1;
        Button btn = variableBinding.button1;
        EditText myedit = variableBinding.edittext1;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has: " + editString);
            }
        });

    }
}