package com.genn.info.restaurant.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.MainActivity;
import com.genn.info.restaurant.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Login extends AppCompatActivity {

    Toolbar toolbar;
    EditText email, password;
    //    CheckBox checkBox;
    ImageButton signin;

    String UserNameStr, PasswordStr;

    // connection

    ConnectionClass connectionClass;
    Connection connect;

    DBHelper dbHelper;


    ProgressBar pbar;


    String Usertype;
    String id;
    String active;
    String userrole;

    String encodeedpass,decodeedpass;

    String key="Genn@GtnInfo";
    String encoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        connectionClass = new ConnectionClass();
        pbar = findViewById(R.id.log_progressBar);

        dbHelper = new DBHelper(this);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        signin = (ImageButton) findViewById(R.id.signin);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserNameStr = email.getText().toString();
                PasswordStr = password.getText().toString();


                DoLogin doLogin = new DoLogin();
                doLogin.execute("");

            }
        });


    }


    public class DoLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;


        @Override
        protected void onPreExecute() {
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbar.setVisibility(View.GONE);
            Toast.makeText(Login.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                if (active.equals("1")) {
                    email.setText("");
                    password.setText("");
                    password.setText("");
                    dbHelper.insertData(UserNameStr, PasswordStr,id);
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);

                    finish();
                }
            } else {

                email.setText("");
                password.setText("");

            }
        }


        @Override
        protected String doInBackground(String... params) {


            if (UserNameStr.trim().equals("") || PasswordStr.trim().equals(""))
                z = "Please enter User Id and Password";
            else {
                try {
                    Encrypt(PasswordStr,key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "select * from Mas_User where Username='" + UserNameStr + "' and Userpwd='" + encodeedpass + "'";

                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);


                        if (rs.next()) {

                            Usertype = String.valueOf(rs.getString("Usertype"));
                            id = String.valueOf(rs.getString("UserID"));
                            active = String.valueOf(rs.getString("Isactive"));
                         /*   editor.putString("disname", emp_name);
                            editor.putString("disno", emp_no);
                            editor.putString("User_status", emp_status);
                            editor.commit();*/

//                            Log.e("Dis_name", emp_name);

                            z = "Login successfull";
                            isSuccess = true;
                        } else {
                            z = "Invalid Credentials";
                            isSuccess = false;
                        }

                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }


    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public String Decrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance
                ("AES/CBC/PKCS5Padding"); //this parameters should not be changed
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] cipherText = decoder.decode(text.getBytes("UTF8"));
        decodeedpass = new String(cipher.doFinal(cipherText), "UTF-8");
//        decrpt.setText(""+decryptedText);


        Log.e("D====gg44444444ta", ""+decodeedpass);
        return decodeedpass; // it returns the result as a String
    }

    public String Encrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encoded = Base64.getEncoder().encodeToString(results);
        }

        encodeedpass=encoded;

        return encoded; // it returns the result as a String
    }

}
