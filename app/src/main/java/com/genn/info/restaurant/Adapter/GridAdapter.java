package com.genn.info.restaurant.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.R;
import com.genn.info.restaurant.Util.SQLDBClass;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] web;
    private final String[] cash;
    private final int[] Imageid;

    SQLDBClass sqldbClass;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    boolean isFavourite;

    public GridAdapter(Context c,String[] web,int[] Imageid,String[] cash ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.cash = cash;

        sqldbClass = new SQLDBClass(c);
        sharedPreferences = c.getSharedPreferences("Test",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.productlist_content, null);
            TextView textView = (TextView) grid.findViewById(R.id.textName);
            TextView text = (TextView) grid.findViewById(R.id.cash);
            ImageView imageView = (ImageView)grid.findViewById(R.id.imageView);
            final ImageView imgButton=(ImageView) grid.findViewById(R.id.favouritebtn);
            textView.setText(web[position]);
            text.setText("\u20B9 "+cash[position]);
            imageView.setImageResource(Imageid[position]);

            boolean aa = sharedPreferences.getBoolean("isFav",isFavourite);

            Log.e("dsddddddddd12132323424",""+aa);

            if (aa) {
                imgButton.setBackgroundResource(R.drawable.like);
            } else {
                imgButton.setBackgroundResource(R.drawable.like_red);
            }

            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    isFavourite = readState();

                    if (sharedPreferences.getBoolean("isFav",isFavourite)) {
                        imgButton.setBackgroundResource(R.drawable.like);
                        isFavourite = false;
                        Log.e("dsddddddddd12132323424",""+web[position]);
                        editor.putBoolean("isFav",isFavourite);
                        editor.commit();
                        editor.apply();
//                        saveState(isFavourite);

                    } else {
                        imgButton.setBackgroundResource(R.drawable.like_red);
                        isFavourite = true;

                        editor.putBoolean("isFav",isFavourite);
                        editor.commit();
                        editor.apply();
//                        saveState(isFavourite);
                    }

                }
            });



        } else {
            grid = (View) convertView;
        }






        return grid;
    }

    private void saveState(boolean isFavourite) {
        SharedPreferences aSharedPreferences =mContext.getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = aSharedPreferences
                .edit();
        aSharedPreferencesEdit.putBoolean("State", isFavourite);
        aSharedPreferencesEdit.commit();
    }

    private boolean readState() {
        SharedPreferences aSharedPreferences = mContext.getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE);
        return aSharedPreferences.getBoolean("State", true);
    }


}
