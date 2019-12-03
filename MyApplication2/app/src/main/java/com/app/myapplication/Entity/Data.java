package com.app.myapplication.Entity;

public class Data {
    public String imageUrl;
    public String film_name;
    public String film_description;
    public String film_time;


    public Data(String imageUrl, String film_description, String film_name, String film_time) {
        this.imageUrl = imageUrl;
        this.film_description = film_description;
        this.film_name = film_name;
        this.film_time = film_time;
    }


}