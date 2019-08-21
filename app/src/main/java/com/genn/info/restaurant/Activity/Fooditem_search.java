package com.genn.info.restaurant.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.genn.info.restaurant.Adapter.ListsearchViewAdapter;

import com.genn.info.restaurant.Model.Listviewsearch;
import com.genn.info.restaurant.R;

import java.util.ArrayList;

public class Fooditem_search extends AppCompatActivity {

    // Declare Variables
    ListView list;
    ListsearchViewAdapter adapter;
    EditText editsearch;
    String[] rank;
    String[] country;
    String[] quantity;
    String[] population;
    ArrayList<Listviewsearch> arraylist = new ArrayList<Listviewsearch>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem_search);

        // Generate sample data
        rank = new String[] { "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category", "Category" };

        country = new String[] { "Item", "Item", "Item",
                "Item", "Item", "Item", "Item", "Item",
                "Item", "Item" };

        population = new String[] { "\u20B9 1,354", "\u20B9 354",
                "\u20B9 315", "\u20B9 237", "\u20B9 193", "\u20B9 182",
                "\u20B9 170", "\u20B9 152", "\u20B9 143", "\u20B9 127" };
        quantity = new String[] { "qty:1", "qty:4",
                "qty:3", "qty:2", "qty:3", "qty:2",
                "qty:1", "qty:1", "qty:3", "qty:7" };
        int[] listviewImage = new int[]{
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
        };


        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < rank.length; i++)
        {
            Listviewsearch wp = new Listviewsearch(rank[i], country[i],
                    population[i],listviewImage[i],quantity[i]);
            // Binds all strings into an array
            arraylist.add(wp);
        }

        // Pass results to ListsearchViewAdapter Class
        adapter = new ListsearchViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    list.clearTextFilter();
                }
                else {
                    adapter.filter(s);
                }
                return true;
            }
        });










        return true;
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_settings){
            //do your functionality here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

}