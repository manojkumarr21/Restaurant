package com.genn.info.restaurant.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.genn.info.restaurant.R;

public class Payment extends AppCompatActivity {

  /*  private LinearLayout americanExpress, visa, mastercard;
    public ImageView rightmark1,rightmark2,rightmark3;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


/*
        ArrayList<ItemData> list = new ArrayList<>();



        list.add(new ItemData("Jan"));
        list.add(new ItemData("Feb"));
        list.add(new ItemData("Mar"));
        list.add(new ItemData("Apr"));
        list.add(new ItemData("May"));
        list.add(new ItemData("Jun"));
        list.add(new ItemData("Jul"));
        list.add(new ItemData("Aug"));
        list.add(new ItemData("Sep"));
        list.add(new ItemData("Oct"));
        list.add(new ItemData("Nov"));
        list.add(new ItemData("Dec"));
        Spinner sp = (Spinner) findViewById(R.id.spinner_month);
        SpinnerCousineAdapter adapter = new SpinnerCousineAdapter(this, R.layout.spinner_selecting_adults, R.id.data, list);
        sp.setAdapter(adapter);

        ArrayList<ItemDataClass> lists = new ArrayList<>();



        lists.add(new ItemDataClass("2017"));
        lists.add(new ItemDataClass("2018"));
        lists.add(new ItemDataClass("2019"));
        lists.add(new ItemDataClass("2020"));
        lists.add(new ItemDataClass("2021"));
        lists.add(new ItemDataClass("2022"));
        lists.add(new ItemDataClass("2023"));
        lists.add(new ItemDataClass("2024"));
        lists.add(new ItemDataClass("2025"));
        lists.add(new ItemDataClass("2026"));
        lists.add(new ItemDataClass("2027"));
        lists.add(new ItemDataClass("2028"));



        Spinner spinner = (Spinner) findViewById(R.id.spinner_year);
        SpinnerClassAdapter adapters = new SpinnerClassAdapter(this, R.layout.spinner_selecting_adults, R.id.data, lists);
        spinner.setAdapter(adapters);


        americanExpress = (LinearLayout) findViewById(R.id.americanExpress);
        visa = (LinearLayout) findViewById(R.id.visa);
        mastercard = (LinearLayout) findViewById(R.id.mastercard);
        rightmark1 = (ImageView) findViewById(R.id.rightmark1);
        rightmark2 = (ImageView) findViewById(R.id.rightmark2);
        rightmark3 = (ImageView) findViewById(R.id.rightmark3);

        americanExpress.setOnClickListener(this);
        visa.setOnClickListener(this);
        mastercard.setOnClickListener(this);*/
    }

   /* @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.americanExpress:

                rightmark1.setImageResource(R.drawable.ic_right);
                rightmark2.setImageResource(R.drawable.ic_round);
                rightmark3.setImageResource(R.drawable.ic_round);
                break;

            case R.id.visa:

                rightmark1.setImageResource(R.drawable.ic_round);
                rightmark2.setImageResource(R.drawable.ic_right);
                rightmark3.setImageResource(R.drawable.ic_round);
                break;

            case R.id.mastercard:

                rightmark1.setImageResource(R.drawable.ic_round);
                rightmark2.setImageResource(R.drawable.ic_round);
                rightmark3.setImageResource(R.drawable.ic_right);
                break;



        }

    }*/
}
