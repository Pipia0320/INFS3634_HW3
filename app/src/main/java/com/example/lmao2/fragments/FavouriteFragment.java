package com.example.lmao2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lmao2.MainActivity;
import com.example.lmao2.R;
import com.example.lmao2.adapters.CatAdapter;
import com.example.lmao2.models.Cat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private final ArrayList<String> favourites = MainActivity.favourites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_favourite, container, false);

        if (favourites.size() != 0) {

            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            final CatAdapter catAdapter = new CatAdapter();

            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.thecatapi.com/v1/breeds",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();

                            Type arrayListType = new TypeToken<ArrayList<Cat>>(){}.getType();
                            ArrayList<Cat> target = gson.fromJson(response, arrayListType);

                            ArrayList<Cat> favouriteCats = new ArrayList<Cat>();

                            for (Cat cat:target) {
                                if (favourites.contains(cat.getCatId())) {
                                    favouriteCats.add(cat);
                                }
                            }

                            catAdapter.setData(favouriteCats);

                            recyclerView.setAdapter(catAdapter);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(),"The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            requestQueue.stop();
                        }
                    });

            requestQueue.add(stringRequest);
        }

        return view;
    }
}
