package com.genn.info.restaurant.Model;

public class Listviewsearch {
    private String rank;
    private String country;
    private String population;
    private String quantity;
    private int image;

    public Listviewsearch(String rank, String country, String population, int image,String quantity) {
        this.rank = rank;
        this.country = country;
        this.population = population;
        this.image = image;
        this.quantity = quantity;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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