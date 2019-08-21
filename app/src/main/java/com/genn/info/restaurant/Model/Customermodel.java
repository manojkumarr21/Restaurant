package com.genn.info.restaurant.Model;

public class Customermodel {
    private String rank;
    private String country;
    private String population;
    private String quantity;


    public Customermodel(String rank, String country, String population,String quantity) {
        this.rank = rank;
        this.country = country;
        this.population = population;
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


    public String getPopulation() {
        return this.population;
    }
}