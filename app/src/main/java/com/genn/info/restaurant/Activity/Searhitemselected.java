package com.genn.info.restaurant.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.genn.info.restaurant.R;

public class Searhitemselected extends AppCompatActivity {
    // Declare Variables
    TextView txtrank;
    TextView txtcountry;
    TextView txtpopulation;
    String rank;
    String country;
    String population;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);

        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();
        // Get the results of rank
        rank = i.getStringExtra("rank");
        // Get the results of country
        country = i.getStringExtra("country");
        // Get the results of population
        population = i.getStringExtra("population");

        // Locate the TextViews in singleitemview.xml
        txtrank = (TextView) findViewById(R.id.rank);
        txtcountry = (TextView) findViewById(R.id.country);
        txtpopulation = (TextView) findViewById(R.id.population);

        // Load the results into the TextViews
        txtrank.setText(rank);
        txtcountry.setText(country);
        txtpopulation.setText(population);
    }
}
