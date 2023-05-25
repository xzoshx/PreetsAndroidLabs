package com.cst2335.Kaur0918.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cst2335.Kaur0918.data.MainViewModel;
import com.cst2335.Kaur0918.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parents onCreate()

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //loads XML on screen

        //variableBinding.textview1.setText(model.editString);
        model.editString.observe(this, s-> {
            variableBinding.textview1.setText("Your edit text has"+ s);
        });
        variableBinding.button1.setOnClickListener(click ->
        {
            model.editString.postValue(variableBinding.edittext1.getText().toString());
        });
        model.isSelected.observe(this, selected -> {
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
        });
        variableBinding.checkBox.setOnCheckedChangeListener((btn, isChecked)->{
            model.isSelected.postValue(isChecked);
            Toast.makeText(MainActivity.this, "The value is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });
        variableBinding.radioButton.setOnCheckedChangeListener((btn, isChecked)->{
            model.isSelected.postValue(isChecked);
            Toast.makeText(MainActivity.this, "The value is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });
        variableBinding.switch1.setOnCheckedChangeListener((btn, isChecked)->{
            model.isSelected.postValue(isChecked);
            Toast.makeText(MainActivity.this, "The value is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });
        variableBinding.imageButton.setOnClickListener(click->{
            Toast.makeText(MainActivity.this, "Width:"+variableBinding.imageButton.getWidth()+"Length:"+variableBinding.imageButton.getHeight(), Toast.LENGTH_LONG).show();
        });
        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has: " + editString);
            }
        });*/

    }
}