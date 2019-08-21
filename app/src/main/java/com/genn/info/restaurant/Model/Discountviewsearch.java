package com.genn.info.restaurant.Model;

public class Discountviewsearch {

    private String country;

    private String quantity;
    ;

    public Discountviewsearch( String country,String quantity) {

        this.country = country;

        this.quantity = quantity;
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


}