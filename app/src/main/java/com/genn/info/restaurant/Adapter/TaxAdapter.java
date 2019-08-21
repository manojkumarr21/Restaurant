package com.genn.info.restaurant.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.genn.info.restaurant.Activity.Searhitemselected;
import com.genn.info.restaurant.Activity.Varientmaster;
import com.genn.info.restaurant.Fragment.Tax;
import com.genn.info.restaurant.Model.Listviewsearch;
import com.genn.info.restaurant.Model.TaxViewsearh;
import com.genn.info.restaurant.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaxAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<TaxViewsearh> worldpopulationlist = null;
    private ArrayList<TaxViewsearh> arraylist;


    public TaxAdapter(Context context, List<TaxViewsearh> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<TaxViewsearh>();
        this.arraylist.addAll(worldpopulationlist);

    }

    public class ViewHolder {
        TextView rank;
        TextView country;
        CheckBox quantity;
        TextView id;
        ImageView editicon;

    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public TaxViewsearh getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final TaxAdapter.ViewHolder holder;
        if (view == null) {
            holder = new TaxAdapter.ViewHolder();
            view = inflater.inflate(R.layout.taxviewseaechlist, null);
            // Locate the TextViews in listviewsearch_item.xmlem.xml
            holder.rank = (TextView) view.findViewById(R.id.rank);
            holder.country = (TextView) view.findViewById(R.id.country);
            holder.id = (TextView) view.findViewById(R.id.idview);
            holder.quantity = (CheckBox) view.findViewById(R.id.quantity);
            holder.quantity.setEnabled(false);
            holder.editicon = (ImageView) view.findViewById(R.id.editicon);
            view.setTag(holder);
        } else {
            holder = (TaxAdapter.ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.rank.setText(worldpopulationlist.get(position).getRank());
        holder.country.setText(worldpopulationlist.get(position).getCountry());

        String check=""+worldpopulationlist.get(position).getQuantity();

        if (check.equals("1")) {
            holder.quantity.setChecked(true);
        } else {
            holder.quantity.setChecked(false);
        }


        holder.id.setText(worldpopulationlist.get(position).getId());
        holder.editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Tax fragment = new Tax();
                FragmentManager fragmentManager = mContext.get; // this is basically context of the class
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putString("name", "From Adapter"); //key and value
//set Fragmentclass Arguments
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.contain_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/






            }
        });

    /*    // Listen for ListView Item Click
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
                intent.putExtra("population",(worldpopulationlist.get(position).getQuantity()));
                // Pass all data flag
                // Start Searhitemselected Class
                mContext.startActivity(intent);
            }
        });
*/
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
            for (TaxViewsearh wp : arraylist)
            {
                if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }





  /*  private void editCustomDialog() {

        String edit_uom = null, edit_plant = null, edit_sort = null, edit_creatby = null, edit_creatdate = null, isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup =mContext.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.varientaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Variant where VariantID='" + ID + "'";
        try {

            connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid = String.valueOf(rs.getString("VariantID"));
                edit_plant = String.valueOf(rs.getString("Variantname"));
                edit_sort = String.valueOf(rs.getString("Sortorder"));
                edit_creatby = String.valueOf(rs.getString("Createdby"));
                edit_creatdate = String.valueOf(rs.getString("Createddate"));

                isactive = rs.getString("Isactive");

            }

            Log.e("ffryutfffffff", "" + isactive);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        varientname = dialogView.findViewById(R.id.et_varientname);
        sortid = dialogView.findViewById(R.id.et_varientorder);

        checkBoxAgree = dialogView.findViewById(R.id.checkBoxAgree);


        checkBoxAgree.setVisibility(View.VISIBLE);


        varientname.setText("" + edit_plant);
        sortid.setText("" + edit_sort);

        creatby = "" + edit_creatby;
        creatdate = "" + edit_creatdate;
        if (isactive.equals("1")) {
            checkBoxAgree.setChecked(true);
        } else {
            checkBoxAgree.setChecked(false);
        }


        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "DELETE FROM Mas_Variant WHERE VariantID='" + ID + "'";
                try {
                    connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                Intent i = new Intent(mContext, Varientmaster.class);
//                startActivity(i);
//                finish();
                buildDialog("Deleted Sucessfully");
            }
        });


        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();



        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                varinamestr = varientname.getText().toString();
                varisort = sortid.getText().toString();


                if (varientname.getText().toString().equals("")) {
                    varientname.requestFocus();
                    varientname.setError("FIELD CANNOT BE EMPTY");
                }else if (sortid.getText().toString().equals("")){
                    sortid.requestFocus();
                    sortid.setError("FIELD CANNOT BE EMPTY");
                }
                else {
                    String query = "select count(*) as row,TaxID from Taxname where Taxname='" + varinamestr + "'group by  TaxID";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


//                    String size=""+rs.getFetchSize();
//
//                    Toast.makeText(Bussinessmaster.this, ""+size, Toast.LENGTH_SHORT).show();


                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            String BussID = String.valueOf(rs.getString("VariantID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                               Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(mContext, "User already exit", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Update update = new Update();
                            update.execute("");
                            alertDialog.dismiss();
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

    }
*/

}