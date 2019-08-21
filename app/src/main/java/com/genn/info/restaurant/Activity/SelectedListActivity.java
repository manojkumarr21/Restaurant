package com.genn.info.restaurant.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.genn.info.restaurant.R;
import com.genn.info.restaurant.Util.Friend;

import java.util.ArrayList;

public class SelectedListActivity extends AppCompatActivity {

    private LinearLayout container;
    private ArrayList<Friend> checkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_list);

        container = (LinearLayout) findViewById(R.id.layout);
        checkedList = new ArrayList<Friend>(); // initializing list
        getDataFromIntent(); // receive data from intent (put by MainActivity)
        generateDataToContainerLayout();
    }

    private void getDataFromIntent() {
        checkedList = getIntent().getParcelableArrayListExtra("Checked List");
        Log.i("ListActivity", "size" + checkedList.size());
    }

    @SuppressLint("InflateParams")
    private void generateDataToContainerLayout() {

        int i = 0;
        if (checkedList.size() == i) { //do nothing
        }
        while (checkedList.size() > i) {
            final Friend friend = checkedList.get(i);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_listview, null,
                    false);

            TextView friendName = (TextView) view.findViewById(R.id.name);
            CheckBox checked = (CheckBox)view.findViewById(R.id.check);
            checked.setVisibility(View.GONE);
            if (friend.isSelected()) {
                Log.i("ListActivity", "here" + friend.getName());
                friendName.setText(friend.getName());

                container.addView(view);
            }

            i++; // rise i
        }
    }
}
