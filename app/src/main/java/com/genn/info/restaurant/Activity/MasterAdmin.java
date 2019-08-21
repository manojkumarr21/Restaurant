package com.genn.info.restaurant.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.genn.info.restaurant.R;

public class MasterAdmin extends AppCompatActivity {

    Toolbar toolbar;
    EditText email,password;
    CheckBox checkBox;
    ImageButton signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_admin);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        checkBox = (CheckBox)findViewById(R.id.checkbox);

        signin =(ImageButton)findViewById(R.id.signin);


        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MasterAdmin.this,"Sign In Button Clicked",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MasterAdmin.this,Masterview.class);
                startActivity(i);
            }
        });

    }
    }

