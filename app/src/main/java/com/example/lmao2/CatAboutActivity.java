package com.example.lmao2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.lmao2.models.Cat;
import com.example.lmao2.responses.CatAboutResponses;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CatAboutActivity extends AppCompatActivity {
    private boolean isPressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_cat);

        final TextView title = findViewById(R.id.title);
        final ImageView image = findViewById(R.id.image);
        final TextView description = findViewById(R.id.description);
        final TextView weight = findViewById(R.id.weight);
        final TextView temperament = findViewById(R.id.temperament);
        final TextView origin = findViewById(R.id.origin);
        final TextView life_span = findViewById(R.id.life_span);
        final TextView dog_level = findViewById(R.id.dog_level);
        final TextView wiki_link = findViewById(R.id.wiki_link);

        // Get intent, retrieve CatID
        Intent intent = getIntent();
        final String cat_id = intent.getStringExtra("CatID");

        // Send request to API and setText
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.thecatapi.com/v1/images/search?breed_ids="+cat_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                Type arrayListType = new TypeToken<ArrayList<CatAboutResponses>>(){}.getType();
                ArrayList<CatAboutResponses> target = gson.fromJson(response, arrayListType);

                // Kill activity if no response
                // Polydactyl the cat return no response
                // id poly
                if (target.size() == 0) {
                    finish();
                } else {
                    Cat cat = target.get(0).getBreeds();

                    title.setText(cat.name);
                    description.setText(cat.description);
                    weight.setText(cat.weight.metric);
                    temperament.setText(cat.temperament);
                    origin.setText(cat.origin);
                    life_span.setText(cat.life_span);
                    wiki_link.setText(cat.wikipedia_url);
                    dog_level.setText(String.valueOf(cat.dog_friendly));
                    Glide.with(getBaseContext()).load(target.get(0).url).into(image);
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);

        // Favourite Button functionality
        Button favourite_button = findViewById(R.id.favourite_button);
        // Check if it's Favourite already
        if (MainActivity.favourites.contains(cat_id)) {
            favourite_button.setBackgroundResource(R.drawable.fav_star_filled);
            favourite_button.setTag("true");
        }

        favourite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPressed) {
                    v.setBackgroundResource(R.drawable.fav_star);
                    MainActivity.favourites.remove(cat_id);
                } else {
                    v.setBackgroundResource(R.drawable.fav_star_filled);
                    MainActivity.favourites.add(cat_id);
                }

                isPressed = !isPressed;

                System.out.println(MainActivity.favourites);
            }
        });

    }
}
