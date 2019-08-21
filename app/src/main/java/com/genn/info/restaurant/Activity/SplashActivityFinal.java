package com.genn.info.restaurant.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.MainActivity;
import com.genn.info.restaurant.Model.ModelClass;
import com.genn.info.restaurant.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SplashActivityFinal extends AppCompatActivity {


    ImageView imageView;
    ProgressBar progressBar;

    ConnectionClass connectionClass;
    DBHelper dbHelper;
    ModelClass modelClass;

    String id,i,d,u,p;;
    String logid,name,pass,userid;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_final);

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        dbHelper=new DBHelper(this);
        modelClass=new ModelClass(this);
        connectionClass=new ConnectionClass();


        Cursor res1 = dbHelper.getAllLoginData();


        while (res1.moveToNext()) {
            logid = res1.getString(0);
            name = res1.getString(1);
            pass = res1.getString(2);
//            userid = res1.getString(3);

        }

        Log.e("ffffffffffffff",""+name);

        modelClass.setUser(u);
        modelClass.setPass(p);

        imageView=findViewById(R.id.splash_imageView);
        progressBar=findViewById(R.id.splash_progressBar);

        Animation up= AnimationUtils.loadAnimation(this,R.anim.up_to_down);
        Animation down= AnimationUtils.loadAnimation(this,R.anim.down_to_up);

//        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        imageView.setAnimation(up);
        progressBar.setAnimation(down);

   /*     DoLogin doLogin=new DoLogin();
        doLogin.execute();
*/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 3 seconds
                    sleep(3 * 1000);

                    if (logid==null){

                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                    else if(!logid.equals("null")) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }



}
