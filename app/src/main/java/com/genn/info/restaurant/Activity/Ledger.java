package com.genn.info.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Ledgermodel;
import com.genn.info.restaurant.Model.Ledgersubgroupmodel;
import com.genn.info.restaurant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.io.IOError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ledger extends AppCompatActivity {

    ConnectionClass connectionClass;
    Connection connect;



    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    DBHelper dbHelper;
    String bussid;

    private Validator validator;
    String logid,name,pass,userid,currentdate;


    @Length(min = 1, max = 50)
    EditText ledgername;

    TextView ledgertype;

    Spinner ledgersubspinner,ledgergroupsubspinner;

    String ledgernamestr;

    ResultSet rs;
    PreparedStatement stmt;


    ListView ledgernamelist;
    SimpleAdapter ADAhere;


    CheckBox checkBoxAgree;

    String msg;
    String ID,status,ed_uom,ledgersubgropnameid,edit_uom1,ledgergroupsubgropnameid,legergrouptype,legersubgrouptype,plantname,sunplantname,edit_plant= null,creatby,creatdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);

        connectionClass = new ConnectionClass();

        dbHelper=new DBHelper(this);
        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        currentdate=dateFormat.format(date);

        Cursor res1 = dbHelper.getAllLoginData();


        while (res1.moveToNext()) {
            logid = res1.getString(0);
            name = res1.getString(1);
            pass = res1.getString(2);
            userid = res1.getString(3);

        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Ledger.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Ledger.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Ledger.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Ledger.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Ledger.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Ledger.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Ledger.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Ledger.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Ledger.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Ledger.this, Varientmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.usergroup) {

                    startActivity(new Intent(Ledger.this, Usergroup.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }

                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contain_frame, fragment);
                    ft.commit();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomDialog();

            }
        });






        ledgernamelist=(ListView)findViewById(R.id.ledgernamelist);




        List<Map<String,String>> MyData = null;
        Ledgermodel mydata =new Ledgermodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Ledgname","LedgId"};

        int[] viewswhere = {R.id.storename ,R.id.idview};

        ADAhere = new SimpleAdapter(Ledger.this, MyData,R.layout.ledgeradd, fromwhere, viewswhere){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                status=(String)obj.get("Isactive");

                View view=super.getView(position, convertView, parent);


                ImageView imageView=view.findViewById(R.id.editicon);
                ImageView deleteicon=view.findViewById(R.id.deleteicon);
                CheckBox usergroupstatus=view.findViewById(R.id.usergroupstatus);

                if (status.equals("1")){
                    usergroupstatus.setChecked(true);
                }else{
                    usergroupstatus.setChecked(false);
                }


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("LedgId");
                        editCustomDialog();
                        Toast.makeText(Ledger.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("LedgId");
                        deleteCustomDialog();
                    }
                });
                return view;
            }
        };

        ledgernamelist.setAdapter(ADAhere);


    }


    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.ledgeraddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        ledgername=dialogView.findViewById(R.id.et_ledgername);
        ledgersubspinner=dialogView.findViewById(R.id.spinnerledgersubgroup);
        ledgergroupsubspinner=dialogView.findViewById(R.id.spinnerledgergroupsubgroup);
        ledgertype=dialogView.findViewById(R.id.ledgertype);

        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);

        checkBoxAgree.setVisibility(View.GONE);



        String storequeery = "select LedgergrpID,Ledgergrpname from Mas_Ledgergroup";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(storequeery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("LedgergrpID");
                String Bussnessname = rs.getString("Ledgergrpname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            ledgersubspinner.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        ledgersubspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = ledgersubspinner.getSelectedItem().toString();


                if (ledgersubspinner.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select LedgergrpID,Ledgergrpname,Ledgertype from Mas_Ledgergroup where Ledgergrpname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            ledgersubgropnameid = rs.getString("LedgergrpID");
                            legergrouptype = rs.getString("Ledgertype");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    ledgertype.setText(""+legergrouptype);



                    Toast.makeText(Ledger.this, "" + ledgersubgropnameid, Toast.LENGTH_SHORT)

                            .show();

                }

                if (ledgersubgropnameid != null){

                    String ledgersubgropnamestrqueery = "select LedgersgrpID,Ledgersgrpname from Mas_Ledgersubgrp where Ledgergroupid='"+ledgersubgropnameid+"'";
                    try {
                        connect=connectionClass.CONN();
                        stmt = connect.prepareStatement(ledgersubgropnamestrqueery);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        while (rs.next()) {

//                            String id = rs.getString("LedgergrpID");
                            String Bussnessname = rs.getString("Ledgersgrpname");
                            data.add(Bussnessname);

                        }
                        data.add(0,"select one");
                        String[] array = data.toArray(new String[0]);
                        ArrayAdapter NoCoreAdapter = new ArrayAdapter(Ledger.this,
                                android.R.layout.simple_list_item_1, data);
                        ledgergroupsubspinner.setAdapter(NoCoreAdapter);

                    } catch (SQLException e) {

                        e.printStackTrace();

                    }

                    ledgergroupsubspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override

                        public void onItemSelected(AdapterView<?> parent, View view,

                                                   int position, long id) {

                            String name = ledgergroupsubspinner.getSelectedItem().toString();


                            if (ledgergroupsubspinner.getSelectedItem() == "select one") {

                                //Do nothing.
                            } else {

                                String query = "select LedgersgrpID,Ledgersgrpname,Ledgertype from Mas_Ledgersubgrp where Ledgersgrpname='" + name + "'";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(query);
                                    rs = stmt.executeQuery();
                                    ArrayList<String> data = new ArrayList<String>();

                                    if (rs.next()) {
                                        ledgergroupsubgropnameid = rs.getString("LedgersgrpID");
//                                        legergrouptype = rs.getString("Ledgertype");

                                    }


                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }

//                                ledgertype.setText(""+legergrouptype);
                                Toast.makeText(Ledger.this, "" + ledgergroupsubgropnameid, Toast.LENGTH_SHORT)

                                        .show();

                            }
                        }

                        @Override

                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });



                }else{
                    Toast.makeText(Ledger.this, "Please select the Ledger group", Toast.LENGTH_SHORT).show();
                }


            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });








        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();




        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ed_uom = ledgername.getText().toString();

                if (ledgername.getText().toString().equals("")) {
                    ledgername.requestFocus();
                    ledgername.setError("FIELD CANNOT BE EMPTY");
                }else if (ledgersubspinner.getSelectedItem() == "select one") {
                    Toast.makeText(Ledger.this, "Please select the plant", Toast.LENGTH_SHORT).show();
                }else if (ledgergroupsubspinner.getSelectedItem() == "select one") {
                    Toast.makeText(Ledger.this, "Please select the plant", Toast.LENGTH_SHORT).show();
                } else {

                    ledgernamestr = ledgername.getText().toString();


                    String query = "select count(*) as row from Mas_Ledger where Ledgname='"+ledgernamestr+"'";
                    try {

                        connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            String bussinessid=String.valueOf(rs.getString("row"));
                            if (bussinessid.equals("0")){
                                UploadImage uploadImage = new UploadImage();
                                uploadImage.execute("");
                                alertDialog.dismiss();

                            }else {
                                Toast.makeText(Ledger.this, "Subdeptname Already exit", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            UploadImage uploadImage = new UploadImage();
                            uploadImage.execute("");
                            alertDialog.dismiss();
                        }




                    }catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

    }





    private void editCustomDialog() {

        String edit_uom = null,edit_plant= null,edit_type= null,edit_creatby = null,edit_creatdate = null,isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.ledgeraddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Ledger where LedgId='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid=String.valueOf(rs.getString("LedgId"));
                edit_uom=String.valueOf(rs.getString("LedgergrpId"));
                edit_uom1=String.valueOf(rs.getString("LedgersubgrpId"));
                edit_plant=String.valueOf(rs.getString("Ledgname"));
                edit_type=String.valueOf(rs.getString("Ledgertype"));
                edit_creatby=String.valueOf(rs.getString("Createby"));
                edit_creatdate=String.valueOf(rs.getString("Createdate"));

                isactive=rs.getString("Isactive");

            }

            Log.e("ffryutfffffff",""+isactive);

        }catch (SQLException e) {
            e.printStackTrace();
        }



        ledgername=dialogView.findViewById(R.id.et_ledgername);
        ledgersubspinner=dialogView.findViewById(R.id.spinnerledgersubgroup);
        ledgergroupsubspinner=dialogView.findViewById(R.id.spinnerledgergroupsubgroup);
        ledgertype=dialogView.findViewById(R.id.ledgertype);

        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);




        checkBoxAgree.setVisibility(View.VISIBLE);



        ledgername.setText(""+edit_plant);
        ledgertype.setText(""+edit_type);
        legergrouptype =""+edit_type;
        creatby=""+edit_creatby;
        creatdate=""+edit_creatdate;

        if (isactive.equals("1")){
            checkBoxAgree.setChecked(true);
        }else{
            checkBoxAgree.setChecked(false);
        }



        String subdepidnamequery = "select * from Mas_Ledgergroup  where LedgergrpID= '"+edit_uom+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(subdepidnamequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("LedgergrpID");
                plantname= rs.getString("Ledgergrpname");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String storequery = "select * from Mas_Ledgergroup  where LedgergrpID != '"+edit_uom+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(storequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("LedgergrpID");
                String Bussnessname = rs.getString("Ledgergrpname");
                data.add(Bussnessname);

            }

            data.add(0,""+plantname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            ledgersubspinner.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        ledgersubspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = ledgersubspinner.getSelectedItem().toString();


                if (ledgersubspinner.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {
                    String query = "select * from Mas_Ledgergroup where Ledgergrpname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            ledgersubgropnameid = rs.getString("LedgergrpID");
                            legergrouptype = rs.getString("Ledgertype");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }

                    ledgertype.setText(""+legergrouptype);
                    Toast.makeText(Ledger.this, "" + ledgersubgropnameid, Toast.LENGTH_SHORT)

                            .show();

                }


                if (ledgersubgropnameid != null){


                    String subdepidnamequery = "select * from Mas_Ledgersubgrp  where LedgersgrpID= '"+edit_uom1+"'";
                    try {
                        connect=connectionClass.CONN();
                        stmt = connect.prepareStatement(subdepidnamequery);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {

//                                        String id = rs.getString("LedgergrpID");
                            sunplantname= rs.getString("Ledgersgrpname");


                        }
                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    String ledgersubgropnamestrqueery = "select LedgersgrpID,Ledgersgrpname from Mas_Ledgersubgrp where Ledgergroupid='"+ledgersubgropnameid+"' and Ledgersgrpname != '"+sunplantname+"'";
                    try {
                        connect=connectionClass.CONN();
                        stmt = connect.prepareStatement(ledgersubgropnamestrqueery);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        while (rs.next()) {

//                            String id = rs.getString("LedgergrpID");
                            String Bussnessname = rs.getString("Ledgersgrpname");
                            data.add(Bussnessname);

                        }
                        data.add(0,""+sunplantname);
                        String[] array = data.toArray(new String[0]);
                        ArrayAdapter NoCoreAdapter = new ArrayAdapter(Ledger.this,
                                android.R.layout.simple_list_item_1, data);
                        ledgergroupsubspinner.setAdapter(NoCoreAdapter);

                    } catch (SQLException e) {

                        e.printStackTrace();

                    }

                    ledgergroupsubspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override

                        public void onItemSelected(AdapterView<?> parent, View view,

                                                   int position, long id) {

                            String name = ledgergroupsubspinner.getSelectedItem().toString();


                            if (ledgergroupsubspinner.getSelectedItem() == "select one") {

                                //Do nothing.
                            } else {



                                String query = "select LedgersgrpID,Ledgersgrpname,Ledgertype from Mas_Ledgersubgrp where Ledgersgrpname='" + name + "'";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(query);
                                    rs = stmt.executeQuery();
                                    ArrayList<String> data = new ArrayList<String>();

                                    if (rs.next()) {
                                        ledgergroupsubgropnameid = rs.getString("LedgersgrpID");
//                                        legergrouptype = rs.getString("Ledgertype");

                                    }


                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }

//                                ledgertype.setText(""+legergrouptype);
                                Toast.makeText(Ledger.this, "" + ledgergroupsubgropnameid, Toast.LENGTH_SHORT)

                                        .show();

                            }
                        }

                        @Override

                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });



                }else{
                    Toast.makeText(Ledger.this, "Please select the Ledger group", Toast.LENGTH_SHORT).show();
                }




            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();



        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ledgernamestr = ledgername.getText().toString();
                if (ledgername.getText().toString().equals("")) {
                    ledgername.requestFocus();
                    ledgername.setError("FIELD CANNOT BE EMPTY");
                }else if (ledgersubspinner.getSelectedItem() == "select one") {
                    Toast.makeText(Ledger.this, "Please select the plant", Toast.LENGTH_SHORT).show();
                }else if (ledgergroupsubspinner.getSelectedItem() == "select one") {
                    Toast.makeText(Ledger.this, "Please select the plant", Toast.LENGTH_SHORT).show();
                } else{
                    String query = "select count(*) as row,LedgId from Mas_Ledger where Ledgname='" + ledgernamestr + "'group by  LedgId";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


//                    String size=""+rs.getFetchSize();
//
//                    Toast.makeText(Bussinessmaster.this, ""+size, Toast.LENGTH_SHORT).show();


                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            String BussID = String.valueOf(rs.getString("LedgId"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(Ledger.this, "User already exit", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Update update = new Update();
                            update.execute("");
                            alertDialog.dismiss();
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });


    }



    private void deleteCustomDialog() {

        //Uncomment the below code to Set the message and title from the strings.xml file

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("DELETE USERGROUP");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String query = "DELETE FROM Mas_Ledger WHERE LedgId='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }

                        List<Map<String,String>> MyData = null;
                        Ledgermodel mydata =new Ledgermodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Ledgname","LedgId"};

                        int[] viewswhere = {R.id.storename ,R.id.idview};

                        ADAhere = new SimpleAdapter(Ledger.this, MyData,R.layout.ledgeradd, fromwhere, viewswhere){
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                                status=(String)obj.get("Isactive");

                                View view=super.getView(position, convertView, parent);


                                ImageView imageView=view.findViewById(R.id.editicon);
                                ImageView deleteicon=view.findViewById(R.id.deleteicon);
                                CheckBox usergroupstatus=view.findViewById(R.id.usergroupstatus);

                                if (status.equals("1")){
                                    usergroupstatus.setChecked(true);
                                }else{
                                    usergroupstatus.setChecked(false);
                                }


                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("LedgId");
                                        editCustomDialog();
                                        Toast.makeText(Ledger.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("LedgId");
                                        deleteCustomDialog();
                                    }
                                });
                                return view;
                            }
                        };

                        ledgernamelist.setAdapter(ADAhere);
                        buildDialog("Deleted Sucessfully");
                        Toast.makeText(Ledger.this, ID, Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });





        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();


    }




    public class UploadImage extends AsyncTask<String,String,String>
    {

        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPostExecute(String r)
        {


            List<Map<String,String>> MyData = null;
            Ledgermodel mydata =new Ledgermodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Ledgname","LedgId"};

            int[] viewswhere = {R.id.storename ,R.id.idview};

            ADAhere = new SimpleAdapter(Ledger.this, MyData,R.layout.ledgeradd, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status=(String)obj.get("Isactive");

                    View view=super.getView(position, convertView, parent);


                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);
                    CheckBox usergroupstatus=view.findViewById(R.id.usergroupstatus);

                    if (status.equals("1")){
                        usergroupstatus.setChecked(true);
                    }else{
                        usergroupstatus.setChecked(false);
                    }


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("LedgId");
                            editCustomDialog();
                            Toast.makeText(Ledger.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("LedgId");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            ledgernamelist.setAdapter(ADAhere);
            buildDialog("Inserted Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ledgernamestr.trim().equals("") ) {

                z = "Please enter Uomname ";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "Insert into Mas_Ledger(Ledgname,LedgergrpId,LedgersubgrpId,Ledgertype,Createby,Createdate) values ('" + ledgernamestr + "','" + ledgersubgropnameid + "','" + ledgergroupsubgropnameid + "','" + legergrouptype + "','" + userid + "','" + currentdate + "')";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        z = "Login successfull";
                        isSuccess = true;
                    }
                } catch (SQLException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 1:", msg);

                    z = "Exceptions";
                } catch (IOError ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 2:", msg);

                    z = "Exceptions";
                } catch (AndroidRuntimeException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 3:", msg);

                    z = "Exceptions";
                } catch (NullPointerException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 4:", msg);

                    z = "Exceptions";
                } catch (Exception ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 5:", msg);

                    z = "Exceptions";
                }
                System.out.println(msg);
                return "";


                //End Inserting in the database
            }
            return z;

        }
    }

    public class Update extends AsyncTask<String,String,String>
    {

        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPostExecute(String r)
        {

            List<Map<String,String>> MyData = null;
            Ledgermodel mydata =new Ledgermodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Ledgname","LedgId"};

            int[] viewswhere = {R.id.storename ,R.id.idview};

            ADAhere = new SimpleAdapter(Ledger.this, MyData,R.layout.ledgeradd, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status=(String)obj.get("Isactive");

                    View view=super.getView(position, convertView, parent);


                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);
                    CheckBox usergroupstatus=view.findViewById(R.id.usergroupstatus);

                    if (status.equals("1")){
                        usergroupstatus.setChecked(true);
                    }else{
                        usergroupstatus.setChecked(false);
                    }


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("LedgId");
                            editCustomDialog();
                            Toast.makeText(Ledger.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("LedgId");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            ledgernamelist.setAdapter(ADAhere);
            buildDialog("Updated Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ledgernamestr.trim().equals("")) {

                z = "Please enter Bussnessname";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {



//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Ledger SET Ledgname='"+ledgernamestr+"',LedgergrpId='"+ledgersubgropnameid+"',LedgersubgrpId='"+ledgergroupsubgropnameid+"',Ledgertype='"+legergrouptype+"',Createby='"+creatby+"',Createdate='"+creatdate+"',Updateby='"+userid+"',Updatedate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE LedgId='"+ID+"';";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        z = "Login successfull";
                        isSuccess = true;
                    }
                } catch (SQLException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 1:", msg);

                    z = "Exceptions";
                } catch (IOError ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 2:", msg);

                    z = "Exceptions";
                } catch (AndroidRuntimeException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 3:", msg);

                    z = "Exceptions";
                } catch (NullPointerException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 4:", msg);

                    z = "Exceptions";
                } catch (Exception ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 5:", msg);

                    z = "Exceptions";
                }
                System.out.println(msg);
                return "";


                //End Inserting in the database
            }
            return z;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void buildDialog( String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Ledger.this);
        builder.setTitle("Subdepartment Master");
        builder.setMessage(type);

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
//        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        dialog.show();
    }

}
