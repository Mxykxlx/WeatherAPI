package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText city;
    TextView temp, mintemp, maxtemp, humidity, weather, description;
    Button search, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        function();
    }

    private void initialize() {
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        mintemp = findViewById(R.id.mintemp);
        maxtemp = findViewById(R.id.maxtemp);
        humidity = findViewById(R.id.humidity);
        weather = findViewById(R.id.weather);
        description = findViewById(R.id.description);
        search = findViewById(R.id.search);
        clear = findViewById(R.id.clear);
    }

    private void function() {


        search.setOnClickListener(v -> {
            String ct = city.getText().toString();
            String key = "b5b3a5b2cd1493ce23ec5bc17dde1aba";
            String url ="https://api.openweathermap.org/data/2.5/weather?q="+ ct + "&appid=" + key;

            RequestQueue q = Volley.newRequestQueue(this);
            q.start();

            JsonObjectRequest data = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONObject obj = response.getJSONObject("main");
                            JSONArray a = response.getJSONArray("weather");
                            JSONObject we = a.getJSONObject(0);

                            String t = obj.getString("temp");
                            String min = obj.getString("temp_min");
                            String max = obj.getString("temp_max");
                            String h = obj.getString("humidity");
                            String w = we.getString("main");
                            String d = we.getString("description");

                            city.setText(null);
                            temp.setText(t);
                            mintemp.setText(min);
                            maxtemp.setText(max);
                            humidity.setText(h);
                            weather.setText(w);
                            description.setText(d);

                            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    },

                        error -> {
                            Toast.makeText(this, "ERROR!!!", Toast.LENGTH_SHORT).show();
                        });

            q.add(data);
        });


        clear.setOnClickListener(v -> {
            city.setText(null);
            temp.setText(null);
            mintemp.setText(null);
            maxtemp.setText(null);
            humidity.setText(null);
            weather.setText(null);
            description.setText(null);
        });
    }
}