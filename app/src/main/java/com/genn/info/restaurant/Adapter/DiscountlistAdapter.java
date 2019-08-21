package com.genn.info.restaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.genn.info.restaurant.Activity.Searhitemselected;
import com.genn.info.restaurant.Model.Categorylistmodel;
import com.genn.info.restaurant.Model.Discountviewsearch;
import com.genn.info.restaurant.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiscountlistAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Discountviewsearch> worldpopulationlist = null;
    private ArrayList<Discountviewsearch> arraylist;

    public DiscountlistAdapter(Context context, List<Discountviewsearch> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Discountviewsearch>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {

        TextView country;

        TextView quantity;

    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Discountviewsearch getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final DiscountlistAdapter.ViewHolder holder;
        if (view == null) {
            holder = new DiscountlistAdapter.ViewHolder();
            view = inflater.inflate(R.layout.category_listitem, null);
            // Locate the TextViews in listviewsearch_item.xmlem.xml

            holder.country = (TextView) view.findViewById(R.id.country);

            holder.quantity = (TextView) view.findViewById(R.id.quantity);

            view.setTag(holder);
        } else {
            holder = (DiscountlistAdapter.ViewHolder) view.getTag();
        }
        // Set the results into TextViews

        holder.country.setText(worldpopulationlist.get(position).getCountry());

        holder.quantity.setText(worldpopulationlist.get(position).getQuantity());


        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to Searhitemselected Class
                Intent intent = new Intent(mContext, Searhitemselected.class);
                // Pass all data rank

                // Pass all data country
                intent.putExtra("country",(worldpopulationlist.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population",(worldpopulationlist.get(position).getQuantity()));
                // Pass all data flag
                // Start Searhitemselected Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (Discountviewsearch wp : arraylist)
            {
                if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}