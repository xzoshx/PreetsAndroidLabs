package com.cst2335.Kaur0918;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {
    RequestQueue queue = null;

    protected String cityName;
    /**
     * This hold the text at the centre of the screen
     */
    private TextView tv = null;

    /**
     * This holds the text as the password
     */
    EditText et = null;
    /**
     * tThis has login button which shows the error if anything is missing
     */
    Button btn = null;
    TextView temp=null;
    TextView maxtemp=null;
    TextView mintemp=null;
    TextView humidity=null;
    ImageView icon=null;
    TextView description=null;
    private Bitmap image = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.edittext);
        btn = findViewById(R.id.button8);
        temp=findViewById(R.id.temp);
        maxtemp=findViewById(R.id.maxTemp);
        mintemp=findViewById(R.id.minTemp);
        humidity=findViewById(R.id.humidity);

        icon=findViewById(R.id.icon);
        description=findViewById(R.id.description);


        btn.setOnClickListener(clk -> {
            cityName = et.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="+ URLEncoder.encode(cityName,"UTF-8")+"&appid=f4165a9a06ccf01377462c4df2dea94e&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try{
                            JSONObject coord=response.getJSONObject("coord");
                            // Extract "weather" JSON array and get the first element (position 0)
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject position0 = weatherArray.getJSONObject(0);
                            // Extract "description" and "icon" from the "weather" JSON object at position 0
                            String descriptionValue = position0.getString("description");
                            String iconName = position0.getString("icon");

                            // Extract "main" JSON object
                            JSONObject mainObject = response.getJSONObject("main");

                            // Extract required values from "main" object
                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidityValue = mainObject.getInt("humidity");


                            // Construct the URL for the weather icon
                            String imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";
                            ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {
                                    // Do something with loaded bitmap...
                                    //FileOutputStream fOut=null;
                                    try {
                                        image = bitmap;
                                        image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName + ".png", Context.MODE_PRIVATE));
                                        runOnUiThread(() -> {
                                            temp.setText("The current temperature is " + current);
                                            temp.setVisibility(View.VISIBLE);
                                            maxtemp.setText("The min temperature is  " + min);
                                            maxtemp.setVisibility(View.VISIBLE);
                                            mintemp.setText("The maxtemp temperature is " + max);
                                            mintemp.setVisibility(View.VISIBLE);
                                            humidity.setText("The Humidity temperature is " + humidityValue + "%");
                                            humidity.setVisibility(View.VISIBLE);
                                            icon.setImageBitmap(image);
                                            icon.setVisibility(View.VISIBLE);
                                            description.setText(descriptionValue);
                                            description.setVisibility(View.VISIBLE);
                                        });
                                    } catch (Exception e) {

                                    }
                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                    (error ) -> {

                                    });runOnUiThread(() -> {
                                temp.setText("The current temperature is " + current);
                                temp.setVisibility(View.VISIBLE);
                                maxtemp.setText("The min temperature is  " + min);
                                maxtemp.setVisibility(View.VISIBLE);
                                mintemp.setText("The maxtemp temperature is " + max);
                                mintemp.setVisibility(View.VISIBLE);
                                humidity.setText("The Humidity temperature is " + humidityValue + "%");
                                humidity.setVisibility(View.VISIBLE);
                                icon.setImageBitmap(image);
                                icon.setVisibility(View.VISIBLE);
                                description.setText(descriptionValue);
                                description.setVisibility(View.VISIBLE);
                            });
                            queue.add(imgReq);

                        } catch(JSONException e){
                            throw new RuntimeException();
                        }
                    },

                    (error) -> {
                    });
            queue.add(request);

        });
    }
}