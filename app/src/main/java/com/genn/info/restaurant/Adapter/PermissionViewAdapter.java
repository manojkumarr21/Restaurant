package com.genn.info.restaurant.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.genn.info.restaurant.R;
import com.genn.info.restaurant.Util.Friend;
import com.genn.info.restaurant.Util.Permission;

import java.util.ArrayList;

public class PermissionViewAdapter extends ArrayAdapter<Permission> {

    private Activity activity;
    private ArrayList<Permission> Permissions;
    private final String TAG = PermissionViewAdapter.class.getSimpleName();

    public PermissionViewAdapter(Activity activity, int resource, ArrayList<Permission> Permissions) {
        super(activity, resource, Permissions);
        this.activity = activity;
        this.Permissions = Permissions;
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
            convertView = inflater.inflate(R.layout.permission_item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        Permission permission = Permissions.get(position);

        //set Friend data to views
        holder.name.setText(permission.getName());

       holder.menu.setChecked(permission.isMenuright());
       holder.FA.setChecked(permission.isFA());
       holder.FS.setChecked(permission.isFS());
       holder.FD.setChecked(permission.isFD());
       holder.FV.setChecked(permission.isFV());
       holder.FX.setChecked(permission.isFX());



        holder.menu.setOnCheckedChangeListener(onCheckedChangeListener(permission));
        holder.FA.setOnCheckedChangeListener(onCheckedChangeListener1(permission));
        holder.FS.setOnCheckedChangeListener(onCheckedChangeListener2(permission));
        holder.FD.setOnCheckedChangeListener(onCheckedChangeListener3(permission));
        holder.FV.setOnCheckedChangeListener(onCheckedChangeListener4(permission));
        holder.FX.setOnCheckedChangeListener(onCheckedChangeListener5(permission));

        return convertView;
    }

    /**
     * handle check box event
     * @param f
     * @return
     */
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener(final Permission f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setMenuright(true);
                } else {
                    f.setMenuright(false);
                }
            }
        };
    }


    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener1(final Permission f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setFA(true);
                } else {
                    f.setFA(false);
                }
            }
        };
    }


  private CompoundButton.OnCheckedChangeListener onCheckedChangeListener2(final Permission f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setFS(true);
                } else {
                    f.setFS(false);
                }
            }
        };
    }


    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener3(final Permission f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setFD(true);
                } else {
                    f.setFD(false);
                }
            }
        };
    }


    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener4(final Permission f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setFV(true);
                } else {
                    f.setFV(false);
                }
            }
        };
    }


    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener5(final Permission f) {
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    f.setFX(true);
                } else {
                    f.setFX(false);
                }
            }
        };
    }

    private class ViewHolder {
//        private ImageView image;
        private TextView name;
        private CheckBox menu,FA,FS,FD,FV,FX;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.name);
            menu = (CheckBox) v.findViewById(R.id.menu);
            FA = (CheckBox) v.findViewById(R.id.FA);
            FS = (CheckBox) v.findViewById(R.id.FS);
            FD = (CheckBox) v.findViewById(R.id.FD);
            FV = (CheckBox) v.findViewById(R.id.FV);
            FX = (CheckBox) v.findViewById(R.id.FX);
        }
    }

}