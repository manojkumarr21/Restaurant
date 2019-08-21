package com.genn.info.restaurant.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.genn.info.restaurant.Activity.Searhitemselected;
import com.genn.info.restaurant.Model.Customermodel;
import com.genn.info.restaurant.Model.Listviewsearch;
import com.genn.info.restaurant.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerlistAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Customermodel> worldpopulationlist = null;
    private ArrayList<Customermodel> arraylist;

    public CustomerlistAdapter(Context context, List<Customermodel> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Customermodel>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView rank;
        TextView country;
        TextView population;
        TextView quantity;
//        CircleImageView image;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Customermodel getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final CustomerlistAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.customerlistadd, null);
            // Locate the TextViews in listviewsearch_item.xmlem.xml
            holder.rank = (TextView) view.findViewById(R.id.rank);
            holder.country = (TextView) view.findViewById(R.id.country);
            holder.population = (TextView) view.findViewById(R.id.population);
            holder.quantity = (TextView) view.findViewById(R.id.quantity);
//            holder.image = (CircleImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (CustomerlistAdapter.ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.rank.setText(worldpopulationlist.get(position).getRank());
        holder.country.setText(worldpopulationlist.get(position).getCountry());
        holder.population.setText(worldpopulationlist.get(position).getPopulation());
        holder.quantity.setText(worldpopulationlist.get(position).getQuantity());
//        holder.image.setImageResource(worldpopulationlist.get(position).getImage());


        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to Searhitemselected Class
                Intent intent = new Intent(mContext, Searhitemselected.class);
                // Pass all data rank
                intent.putExtra("rank",(worldpopulationlist.get(position).getRank()));
                // Pass all data country
                intent.putExtra("country",(worldpopulationlist.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population",(worldpopulationlist.get(position).getPopulation()));
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
            for (Customermodel wp : arraylist)
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