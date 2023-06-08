package com.cst2335.Kaur0918;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.cst2335.Kaur0918.databinding.ActivitySecondBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;
    private Bitmap mBitmap;
    private File photoFile;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "MyData";
    private static final String PHONE_NUMBER_KEY = "PhoneNumber";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent fromPrevious = getIntent();


        //Intent fromPrevious=getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        //int age = fromPrevious.getIntExtra("Age");
        String name = fromPrevious.getStringExtra("Name");
        String pCode = fromPrevious.getStringExtra("PostalCode");
        binding.textView3.setText("Welcome back " + emailAddress);


        // Load the phone number if available
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String phoneNumber = prefs.getString(PHONE_NUMBER_KEY, "");
        binding.editTextPhone.setText(phoneNumber);


        ////String phoneNumber = getIntent().getStringExtra("PhoneNumber");
        // Declare and initialize the profileImage ImageView
        //ImageView profileImage = binding.imageView;


        binding.secondPageButton.setOnClickListener(clk -> {
            // Creating the call intent
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + "6134453414"));
            startActivity(callIntent);


        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override

                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            binding.imageView.setImageBitmap(thumbnail);

                        }

                    }


                    private void saveBitmap(Bitmap bitmap) {
                        FileOutputStream fOut = null;

                        try {
                            fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fOut != null) {
                                try {
                                    fOut.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }


                });
        binding.button3.setOnClickListener(clk -> {
            //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });
        cameraResult.launch(cameraIntent);
        File file = new File(getFilesDir(), "Picture.png");
        if (file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            binding.imageView.setImageBitmap(theImage);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        String phoneNumber = binding.editTextPhone.getText().toString();
        savePhoneNumber(phoneNumber);
    }

    private void savePhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PHONE_NUMBER_KEY, "6134063414");
        editor.apply();

    }
}