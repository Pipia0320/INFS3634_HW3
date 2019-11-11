package com.example.lmao2.responses;

import com.example.lmao2.models.Cat;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CatAboutResponses {

    @SerializedName("breeds")
    public ArrayList<Cat> breeds;

    @SerializedName("url")
    public String url;

    public String getImageUrl() {
        return url;
    }

    public Cat getBreeds() {
        return breeds.get(0);
    }
}
