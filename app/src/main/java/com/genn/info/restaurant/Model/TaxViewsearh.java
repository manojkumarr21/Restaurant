package com.genn.info.restaurant.Model;

public class TaxViewsearh  {
    private String rank;
    private String country;
    private String quantity;
    private String id;


    public TaxViewsearh(String rank, String country, String quantity, String id) {
        this.rank = rank;
        this.country = country;;
        this.quantity = quantity;
        this.id = id;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCountry() {
        return this.country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}