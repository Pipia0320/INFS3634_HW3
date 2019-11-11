package com.example.lmao2.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Cat {

    public String id;
    public String name;
    public String description;
    public String temperament;
    public String life_span;
    public String alt_names;
    public String wikipedia_url;
    public String origin;
    public CatWeight weight;
    public int experimental;
    public int hairless;
    public int natural;
    public int rare;
    public int rex;
    public int suppress_tail;
    public int short_legs;
    public int hypoallergenic;
    public int adaptability;
    public int affection_level;
    public String country_code;
    public int child_friendly;
    public int dog_friendly;
    public int energy_level;
    public int grooming;
    public int health_issues;
    public int intelligence;
    public int shedding_level;
    public int social_needs;
    public int stranger_friendly;
    public int vocalisation;


    public String getCatName() {
        return this.name;
    }
    public String getCatId() {
        return this.id;
    }
}
