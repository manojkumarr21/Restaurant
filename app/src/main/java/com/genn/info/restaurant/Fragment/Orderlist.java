package com.genn.info.restaurant.Fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.genn.info.restaurant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Orderlist extends Fragment {

View view;
    int minteger = 0;
    TextView displayInteger;
    Button decrease;
    Button increase;

    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    int[] listviewImage = new int[]{
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
    };

    String[] listviewShortDescription = new String[]{
            "\u20B9 200", "\u20B9 200", "\u20B9 200", "\u20B9 200",
            "\u20B9 200", " \u20B9 200", "\u20B9 200", "\u20B9 200",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_orderlist, container, false);




        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }
        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.orderlistview_activity, from, to){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                decrease=view.findViewById(R.id.decrease);
                increase=view.findViewById(R.id.increase);
                displayInteger=view.findViewById(R.id.integer_number);
                decrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        minteger = minteger - 1;
                        displayInteger.setText("" + minteger);
//                        display(minteger);
                    }
                });
                increase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        minteger = minteger + 1;
                        displayInteger.setText("" + minteger);
//                        display(minteger);
                    }
                });


                return view;
            }
        };
        ListView androidListView = view.findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);


        return view;
    }


/*
    public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger);

    }public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
    }


*/

    private void display(int number) {

        displayInteger.setText("" + number);
    }

}
