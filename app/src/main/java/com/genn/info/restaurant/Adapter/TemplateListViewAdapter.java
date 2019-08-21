package com.genn.info.restaurant.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.genn.info.restaurant.R;
import com.genn.info.restaurant.Util.Friend;

import java.util.ArrayList;

public class TemplateListViewAdapter extends ArrayAdapter<Friend> {

    private Activity activity;
    private ArrayList<Friend> Friends;
    private final String TAG = TemplateListViewAdapter.class.getSimpleName();

    public TemplateListViewAdapter(Activity activity, int resource, ArrayList<Friend> Friends) {
        super(activity, resource, Friends);
        this.activity = activity;
        this.Friends = Friends;
        Log.i(TAG, "init adapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        // inflate layout from xml
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        Friend friend = Friends.get(position);

        //set Friend data to views
        holder.name.setText(friend.getName());

       holder.check.setChecked(friend.isSelected());
        holder.check.setOnCheckedChangeListener(onCheckedChangeListener(friend));

        return convertView;
    }

    /**
     * handle check box event
     * @param f
     * @return
     */
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener(final Friend f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setSelected(true);
                } else {
                    f.setSelected(false);
                }
            }
        };
    }

    private class ViewHolder {
//        private ImageView image;
        private TextView name;
        private CheckBox check;

        public ViewHolder(View v) {

            name = (TextView) v.findViewById(R.id.name);
            check = (CheckBox) v.findViewById(R.id.check);
        }
    }

}