package com.genn.info.restaurant.Model;

public class Listsearch {
    private String rank;
    private String country;
    private String population;
    private int image;

    public Listsearch(String rank, String country, String population, int image) {
        this.rank = rank;
        this.country = country;
        this.population = population;
        this.image = image;
    }

    public String getRank() {
        return this.rank;
    }

    public String getCountry() {
        return this.country;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPopulation() {
        return this.population;
    }
}