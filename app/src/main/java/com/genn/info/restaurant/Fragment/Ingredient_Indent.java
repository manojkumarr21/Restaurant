package com.genn.info.restaurant.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genn.info.restaurant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ingredient_Indent extends Fragment {


    public Ingredient_Indent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient__indent, container, false);
    }

}
