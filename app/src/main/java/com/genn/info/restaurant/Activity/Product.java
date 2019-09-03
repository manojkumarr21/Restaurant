package com.genn.info.restaurant.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Fragment.Product_fragment;
import com.genn.info.restaurant.MainActivity;
import com.genn.info.restaurant.Model.Dishdivmodel;
import com.genn.info.restaurant.Model.Workdivmodel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Fragment.Category;
import com.genn.info.restaurant.Fragment.Discount;
import com.genn.info.restaurant.Fragment.Item;
import com.genn.info.restaurant.Fragment.Tax;
import com.genn.info.restaurant.R;

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


public class Product extends AppCompatActivity {



    ConnectionClass connectionClass;
    Connection connect;



    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    DBHelper dbHelper;
    String bussid;

    private Validator validator;
    String logid,name,pass,userid,currentdate;


    @Length(min = 1, max = 50)
    EditText workdivisionname;

    Spinner plantspinner;

    String workdivionstr;

    ResultSet rs;
    PreparedStatement stmt;


    ListView listview;
    SimpleAdapter ADAhere;


    CheckBox checkBoxAgree;

    String msg;
    String ID,status,ed_uom,prodcatgid,varientid,ledgergroupid,ledgersubgroupid,ledgerid,prodcatgname,varientname,ledgergroupname,ledgersubgroupname,ledgername,edit_plant= null,creatby,creatdate;

    String[] descriptionData = {"Product", "Detail", "Rate"};
    Button btn1;
    EditText dishname;
    EditText Platerate;

    String dishnamestr,plateratestr;
    Spinner categoryspinner;
    Spinner Subcategory;
    Spinner Ledgergroup;
    Spinner Ledgersubgroup;
    Spinner Ledger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        connectionClass = new ConnectionClass();
        dbHelper = new DBHelper(this);
        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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


        NavigationView navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Product.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Product.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {

                    startActivity(new Intent(Product.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Product.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }
                else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Product.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Product.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Product.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Product.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Product.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Product.this, Varientmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }

                //replacing the fragment
                if (fragment != null)
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contain_frame, fragment);
                    ft.commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });




        listview=(ListView)findViewById(R.id.listview);

      List<Map<String,String>> MyData = null;
            Dishdivmodel mydata =new Dishdivmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Dishname","DishID"};

            int[] viewswhere = {R.id.storename ,R.id.idview};

            ADAhere = new SimpleAdapter(Product.this, MyData,R.layout.productadd, fromwhere, viewswhere){
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
                            ID=(String)obj.get("DishID");
                            editCustomDialog();
                            Toast.makeText(Product.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("DishID");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

        listview.setAdapter(ADAhere);


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();

            }
        });


    }





    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.productaddlistmaster, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(Product.this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        dishname=dialogView.findViewById(R.id.dishname);
        Platerate=dialogView.findViewById(R.id.Platerate);
        categoryspinner=dialogView.findViewById(R.id.categoryspinner);
        Subcategory=dialogView.findViewById(R.id.Subcategory);
        Ledgergroup=dialogView.findViewById(R.id.Ledgergroup);
        Ledgersubgroup=dialogView.findViewById(R.id.Ledgersubgroup);
        Ledger=dialogView.findViewById(R.id.Ledger);

        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);

        checkBoxAgree.setVisibility(View.GONE);





        String storequeery = "select ID,Name from Mas_Prodcatg";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(storequeery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("ID");
                String Bussnessname = rs.getString("Name");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            categoryspinner.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = categoryspinner.getSelectedItem().toString();


                if (categoryspinner.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select ID,Name from Mas_Prodcatg where Name='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            prodcatgid = rs.getString("ID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Product.this, "" + prodcatgid, Toast.LENGTH_SHORT)

                            .show();

                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



   String sucatgqueery = "select ID,Variantname from Mas_Variant";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(sucatgqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("ID");
                String Bussnessname = rs.getString("Variantname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            Subcategory.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        Subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = Subcategory.getSelectedItem().toString();


                if (Subcategory.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select ID,Variantname from Mas_Variant where Variantname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            varientid = rs.getString("ID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Product.this, "" + prodcatgid, Toast.LENGTH_SHORT)

                            .show();

                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



  String ledgergroupueery = "select LedgergrpID,Ledgergrpname from Mas_Ledgergroup";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(ledgergroupueery);
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
            Ledgergroup.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        Ledgergroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = Ledgergroup.getSelectedItem().toString();


                if (Ledgergroup.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select LedgergrpID,Ledgergrpname from Mas_Ledgergroup where Ledgergrpname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            ledgergroupid = rs.getString("LedgergrpID");
                        }



                        String ledgsubqueery = "select LedgersgrpID,Ledgersgrpname from Mas_Ledgersubgrp where Ledgergroupid='"+ledgergroupid+"'";
                        try {
                            connect=connectionClass.CONN();
                            stmt = connect.prepareStatement(ledgsubqueery);
                            rs = stmt.executeQuery();
                            ArrayList<String> ledgersub = new ArrayList<String>();

                            while (rs.next()) {

                                String ledgersubgroupid = rs.getString("LedgersgrpID");
                                String Bussnessname = rs.getString("Ledgersgrpname");
                                ledgersub.add(Bussnessname);

                            }
                            ledgersub.add(0,"select one");
                            String[] array = ledgersub.toArray(new String[0]);
                            ArrayAdapter NoCoreAdapter = new ArrayAdapter(Product.this,
                                    android.R.layout.simple_list_item_1, ledgersub);
                            Ledgersubgroup.setAdapter(NoCoreAdapter);

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }

                        Ledgersubgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override

                            public void onItemSelected(AdapterView<?> parent, View view,

                                                       int position, long id) {

                                String name = Ledgersubgroup.getSelectedItem().toString();


                                if (Ledgersubgroup.getSelectedItem() == "select one") {

                                    //Do nothing.
                                } else {

                                    String query = "select LedgersgrpID,Ledgersgrpname from Mas_Ledgersubgrp where Ledgersgrpname='" + name + "'";
                                    try {
                                        connect = connectionClass.CONN();
                                        stmt = connect.prepareStatement(query);
                                        rs = stmt.executeQuery();
                                        ArrayList<String> data = new ArrayList<String>();

                                        if (rs.next()) {
                                            ledgersubgroupid = rs.getString("LedgersgrpID");
                                        }




                                        String ledgerqueery = "select LedgId,Ledgname from Mas_Ledger where LedgersubgrpId='"+ledgersubgroupid+"'";
                                        try {
                                            connect=connectionClass.CONN();
                                            stmt = connect.prepareStatement(ledgerqueery);
                                            rs = stmt.executeQuery();
                                            ArrayList<String> ledger = new ArrayList<String>();

                                            while (rs.next()) {

//                                                String id = rs.getString("LedgergrpID");
                                                String Bussnessname = rs.getString("Ledgname");
                                                ledger.add(Bussnessname);

                                            }
                                            ledger.add(0,"select one");
                                            String[] array = ledger.toArray(new String[0]);
                                            ArrayAdapter NoCoreAdapter = new ArrayAdapter(Product.this,
                                                    android.R.layout.simple_list_item_1, ledger);
                                            Ledger.setAdapter(NoCoreAdapter);

                                        } catch (SQLException e) {

                                            e.printStackTrace();

                                        }


                                        Ledger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                            @Override

                                            public void onItemSelected(AdapterView<?> parent, View view,

                                                                       int position, long id) {

                                                String name = Ledger.getSelectedItem().toString();


                                                if (Ledger.getSelectedItem() == "select one") {

                                                    //Do nothing.
                                                } else {

                                                    String query = "select LedgId,Ledgname from Mas_Ledger where Ledgname='" + name + "'";
                                                    try {
                                                        connect = connectionClass.CONN();
                                                        stmt = connect.prepareStatement(query);
                                                        rs = stmt.executeQuery();
                                                        ArrayList<String> data = new ArrayList<String>();

                                                        if (rs.next()) {
                                                            ledgerid = rs.getString("LedgId");
                                                        }


                                                    } catch (SQLException e) {

                                                        e.printStackTrace();

                                                    }


                                                    Toast.makeText(Product.this, "" + ledgerid, Toast.LENGTH_SHORT)

                                                            .show();

                                                }
                                            }
                                            @Override

                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }

                                        });



                                    } catch (SQLException e) {

                                        e.printStackTrace();

                                    }


                                    Toast.makeText(Product.this, "" + prodcatgid, Toast.LENGTH_SHORT)

                                            .show();

                                }
                            }
                            @Override

                            public void onNothingSelected(AdapterView<?> parent) {

                            }

                        });



                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Product.this, "" + prodcatgid, Toast.LENGTH_SHORT)

                            .show();

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

                dishnamestr = dishname.getText().toString();
                plateratestr = Platerate.getText().toString();
//                varisort = catesortid.getText().toString();
//                imagestring=encodedImage;

                if (dishname.getText().toString().equals("")) {
                    dishname.requestFocus();
                    dishname.setError("FIELD CANNOT BE EMPTY");
                }else if (Platerate.getText().toString().equals("")) {
                    Platerate.requestFocus();
                    Platerate.setError("FIELD CANNOT BE EMPTY");
                }else if (categoryspinner.getSelectedItem() == "select one") {
                    buildDialog("Please select the Productcatg");
                }else if (categoryspinner.getSelectedItem() == "select one") {
                    buildDialog("Please select the Productcatg");
                }else if (Subcategory.getSelectedItem() == "select one") {
                    buildDialog("Please select the ProductSubcatg");
                }else if (Ledgergroup.getSelectedItem() == "select one") {
                    buildDialog("Please select the LedgerGroup");
                }else if (Ledgersubgroup.getSelectedItem() == "select one") {
                    buildDialog("Please select the LedgerSubgroup");
                }else if (Ledger.getSelectedItem() == "select one") {
                    buildDialog("Please select the Ledger");
                }
                else {

                    String query = "select count(*) as row from Mas_Dish where Dishname='" + dishname +"'";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            if (bussinessid.equals("0")) {

                                UploadImage uploadImage = new UploadImage();
                                uploadImage.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(getApplicationContext(), "ledgergroup  Already exit", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            UploadImage uploadImage = new UploadImage();
                            uploadImage.execute("");
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    private void editCustomDialog() {

        String edit_uom = null,edit_plant= null,edit_dishname=null,edit_platerate=null,edit_creatby = null,edit_creatdate = null,isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.productaddlistmaster, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Dish where DishID='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//          stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid=String.valueOf(rs.getString("DishID"));
                edit_dishname=String.valueOf(rs.getString("Dishname"));
                prodcatgid=String.valueOf(rs.getString("DishcatgID"));
                varientid=String.valueOf(rs.getString("DishsubcatgId"));
                edit_platerate=String.valueOf(rs.getString("Platewt"));
                ledgergroupid=String.valueOf(rs.getString("LedgergrpID"));
                ledgersubgroupid=String.valueOf(rs.getString("LedgersgrpID"));
                ledgerid=String.valueOf(rs.getString("LedgerID"));
//                edit_uom=String.valueOf(rs.getString("PlantId"));
//                edit_plant=String.valueOf(rs.getString("WorkLocation"));
                edit_creatby=String.valueOf(rs.getString("Createby"));
                edit_creatdate=String.valueOf(rs.getString("Createdate"));

                isactive=rs.getString("Isactive");

            }

            Log.e("ffryutfffffff",""+isactive);

        }catch (SQLException e) {
            e.printStackTrace();
        }


        dishname=dialogView.findViewById(R.id.dishname);
        Platerate=dialogView.findViewById(R.id.Platerate);
        categoryspinner=dialogView.findViewById(R.id.categoryspinner);
        Subcategory=dialogView.findViewById(R.id.Subcategory);
        Ledgergroup=dialogView.findViewById(R.id.Ledgergroup);
        Ledgersubgroup=dialogView.findViewById(R.id.Ledgersubgroup);
        Ledger=dialogView.findViewById(R.id.Ledger);

        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);


        checkBoxAgree.setVisibility(View.VISIBLE);



        dishname.setText(""+edit_dishname);
        Platerate.setText(""+edit_platerate);

        creatby=""+edit_creatby;
        creatdate=""+edit_creatdate;

        if (isactive.equals("1")){
            checkBoxAgree.setChecked(true);
        }else{
            checkBoxAgree.setChecked(false);
        }




        String prodcatgquerry = "select * from Mas_Prodcatg  where ID = '"+prodcatgid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(prodcatgquerry);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("ID");
                prodcatgname= rs.getString("Name");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String storequery = "select * from Mas_Prodcatg  where ID != '"+prodcatgid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(storequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("ID");
                String Bussnessname = rs.getString("Name");
                data.add(Bussnessname);

            }

            data.add(0,""+prodcatgname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            categoryspinner.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }




    String prodsubcatgquerry = "select * from Mas_Variant  where ID = '"+varientid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(prodsubcatgquerry);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("ID");
                varientname= rs.getString("Variantname");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String prodsubcatgquerry1 = "select * from Mas_Variant  where ID != '"+varientid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(prodsubcatgquerry1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("ID");
                String Bussnessname = rs.getString("Variantname");
                data.add(Bussnessname);

            }

            data.add(0,""+varientname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            Subcategory.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }
        Subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = Subcategory.getSelectedItem().toString();


                if (Subcategory.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select ID,Variantname from Mas_Variant where Variantname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            varientid = rs.getString("ID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Product.this, "" + varientid, Toast.LENGTH_SHORT)

                            .show();

                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        String ledgergroupqueery = "select * from Mas_Ledgergroup  where LedgergrpID = '"+ledgergroupid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(ledgergroupqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("LedgergrpID");
                ledgergroupname= rs.getString("Ledgergrpname");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String ledgergroupqueery1 = "select * from Mas_Ledgergroup  where LedgergrpID != '"+ledgergroupid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(ledgergroupqueery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("LedgergrpID");
                String Bussnessname = rs.getString("Ledgergrpname");
                data.add(Bussnessname);

            }

            data.add(0,""+ledgergroupname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            Ledgergroup.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }




        String ledgersubgroupqueery = "select * from Mas_Ledgersubgrp  where LedgersgrpID = '"+ledgersubgroupid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(ledgersubgroupqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("LedgersgrpID");
                ledgersubgroupname= rs.getString("Ledgersgrpname");

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String ledgersubgroupqueery1 = "select * from Mas_Ledgersubgrp  where LedgersgrpID != '"+ledgersubgroupid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(ledgersubgroupqueery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("LedgersgrpID");
                String Bussnessname = rs.getString("Ledgersgrpname");
                data.add(Bussnessname);

            }

            data.add(0,""+ledgersubgroupname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            Ledgersubgroup.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }




        String ledgerqueery = "select * from Mas_Ledger  where LedgId = '"+ledgerid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(ledgerqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("LedgId");
                ledgername= rs.getString("Ledgname");

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String ledgerqueery1 = "select * from Mas_Ledger  where LedgId != '"+ledgerid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(ledgerqueery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("LedgId");
                String Bussnessname = rs.getString("Ledgname");
                data.add(Bussnessname);

            }

            data.add(0,""+ledgername);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            Ledger.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        Ledgergroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = Ledgergroup.getSelectedItem().toString();


                if (Ledgergroup.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select LedgergrpID,Ledgergrpname from Mas_Ledgergroup where Ledgergrpname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            ledgergroupid = rs.getString("LedgergrpID");
                        }



                        String ledgsubqueery = "select LedgersgrpID,Ledgersgrpname from Mas_Ledgersubgrp where Ledgergroupid='"+ledgergroupid+"'";
                        try {
                            connect=connectionClass.CONN();
                            stmt = connect.prepareStatement(ledgsubqueery);
                            rs = stmt.executeQuery();
                            ArrayList<String> ledgersub = new ArrayList<String>();

                            while (rs.next()) {

                                String ledgersubgroupid = rs.getString("LedgersgrpID");
                                String Bussnessname = rs.getString("Ledgersgrpname");
                                ledgersub.add(Bussnessname);

                            }
                            ledgersub.add(0,""+ledgersubgroupname);
                            String[] array = ledgersub.toArray(new String[0]);
                            ArrayAdapter NoCoreAdapter = new ArrayAdapter(Product.this,
                                    android.R.layout.simple_list_item_1, ledgersub);
                            Ledgersubgroup.setAdapter(NoCoreAdapter);

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }

                        Ledgersubgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override

                            public void onItemSelected(AdapterView<?> parent, View view,

                                                       int position, long id) {

                                String name = Ledgersubgroup.getSelectedItem().toString();


                                if (Ledgersubgroup.getSelectedItem() == "select one") {

                                    //Do nothing.
                                } else {

                                    String query = "select LedgersgrpID,Ledgersgrpname from Mas_Ledgersubgrp where Ledgersgrpname='" + name + "'";
                                    try {
                                        connect = connectionClass.CONN();
                                        stmt = connect.prepareStatement(query);
                                        rs = stmt.executeQuery();
                                        ArrayList<String> data = new ArrayList<String>();

                                        if (rs.next()) {
                                            ledgersubgroupid = rs.getString("LedgersgrpID");
                                        }




                                        String ledgerqueery = "select LedgId,Ledgname from Mas_Ledger where LedgersubgrpId='"+ledgersubgroupid+"'";
                                        try {
                                            connect=connectionClass.CONN();
                                            stmt = connect.prepareStatement(ledgerqueery);
                                            rs = stmt.executeQuery();
                                            ArrayList<String> ledger = new ArrayList<String>();

                                            while (rs.next()) {

//                                                String id = rs.getString("LedgergrpID");
                                                String Bussnessname = rs.getString("Ledgname");
                                                ledger.add(Bussnessname);

                                            }
                                            ledger.add(0,""+ledgername);
                                            String[] array = ledger.toArray(new String[0]);
                                            ArrayAdapter NoCoreAdapter = new ArrayAdapter(Product.this,
                                                    android.R.layout.simple_list_item_1, ledger);
                                            Ledger.setAdapter(NoCoreAdapter);

                                        } catch (SQLException e) {

                                            e.printStackTrace();

                                        }


                                        Ledger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                            @Override

                                            public void onItemSelected(AdapterView<?> parent, View view,

                                                                       int position, long id) {

                                                String name = Ledger.getSelectedItem().toString();


                                                if (Ledger.getSelectedItem() == "select one") {

                                                    //Do nothing.
                                                } else {

                                                    String query = "select LedgId,Ledgname from Mas_Ledger where Ledgname='" + name + "'";
                                                    try {
                                                        connect = connectionClass.CONN();
                                                        stmt = connect.prepareStatement(query);
                                                        rs = stmt.executeQuery();
                                                        ArrayList<String> data = new ArrayList<String>();

                                                        if (rs.next()) {
                                                            ledgerid = rs.getString("LedgId");
                                                        }


                                                    } catch (SQLException e) {

                                                        e.printStackTrace();

                                                    }


                                                    Toast.makeText(Product.this, "" + ledgerid, Toast.LENGTH_SHORT)

                                                            .show();

                                                }
                                            }
                                            @Override

                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }

                                        });



                                    } catch (SQLException e) {

                                        e.printStackTrace();

                                    }


                                    Toast.makeText(Product.this, "" + prodcatgid, Toast.LENGTH_SHORT)

                                            .show();

                                }
                            }
                            @Override

                            public void onNothingSelected(AdapterView<?> parent) {

                            }

                        });



                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Product.this, "" + prodcatgid, Toast.LENGTH_SHORT)

                            .show();

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


                dishnamestr = dishname.getText().toString();
                plateratestr = Platerate.getText().toString();
//                varisort = catesortid.getText().toString();
//                imagestring=encodedImage;

                if (dishname.getText().toString().equals("")) {
                    dishname.requestFocus();
                    dishname.setError("FIELD CANNOT BE EMPTY");
                }else if (Platerate.getText().toString().equals("")) {
                    Platerate.requestFocus();
                    Platerate.setError("FIELD CANNOT BE EMPTY");
                }else if (categoryspinner.getSelectedItem() == "select one") {
                    buildDialog("Please select the Productcatg");
                }else if (categoryspinner.getSelectedItem() == "select one") {
                    buildDialog("Please select the Productcatg");
                }else if (Subcategory.getSelectedItem() == "select one") {
                    buildDialog("Please select the ProductSubcatg");
                }else if (Ledgergroup.getSelectedItem() == "select one") {
                    buildDialog("Please select the LedgerGroup");
                }else if (Ledgersubgroup.getSelectedItem() == "select one") {
                    buildDialog("Please select the LedgerSubgroup");
                }else if (Ledger.getSelectedItem() == "select one") {
                    buildDialog("Please select the Ledger");
                }else {
                    String query = "select count(*) as row,DishID from Mas_Dish where Dishname='" + dishnamestr + "'group by  DishID";
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
                            String BussID = String.valueOf(rs.getString("DishID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(Product.this, "User already exit", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("DELETE DISH");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String query = "DELETE FROM Mas_Dish WHERE DishID='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }

                        listview=(ListView)findViewById(R.id.listview);

                        List<Map<String,String>> MyData = null;
                        Dishdivmodel mydata =new Dishdivmodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Dishname","DishID"};

                        int[] viewswhere = {R.id.storename ,R.id.idview};

                        ADAhere = new SimpleAdapter(Product.this, MyData,R.layout.productadd, fromwhere, viewswhere){
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
                                        ID=(String)obj.get("DishID");
                                        editCustomDialog();
                                        Toast.makeText(Product.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("DishID");
                                        deleteCustomDialog();
                                    }
                                });
                                return view;
                            }
                        };

                        listview.setAdapter(ADAhere);
                        buildDialog("Deleted Sucessfully");
                        Toast.makeText(Product.this, ID, Toast.LENGTH_SHORT).show();

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

            listview=(ListView)findViewById(R.id.listview);

            List<Map<String,String>> MyData = null;
            Dishdivmodel mydata =new Dishdivmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Dishname","DishID"};

            int[] viewswhere = {R.id.storename ,R.id.idview};

            ADAhere = new SimpleAdapter(Product.this, MyData,R.layout.productadd, fromwhere, viewswhere){
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
                            ID=(String)obj.get("DishID");
                            editCustomDialog();
                            Toast.makeText(Product.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("DishID");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            listview.setAdapter(ADAhere);
            buildDialog("Inserted Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (dishnamestr.trim().equals("") ) {

                z = "Please enter Uomname ";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "Insert into Mas_Dish(Dishname,DishcatgID,DishsubcatgId,Platewt,LedgergrpID,LedgersgrpID,LedgerID,Createby,Createdate) values ('" + dishnamestr + "','" + prodcatgid + "','" + varientid + "','" + plateratestr + "','" + ledgergroupid + "','" + ledgersubgroupid + "','" + ledgerid + "','" + userid + "','" + currentdate + "')";
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
            listview=(ListView)findViewById(R.id.listview);

            List<Map<String,String>> MyData = null;
            Dishdivmodel mydata =new Dishdivmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Dishname","DishID"};

            int[] viewswhere = {R.id.storename ,R.id.idview};

            ADAhere = new SimpleAdapter(Product.this, MyData,R.layout.productadd, fromwhere, viewswhere){
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
                            ID=(String)obj.get("DishID");
                            editCustomDialog();
                            Toast.makeText(Product.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("DishID");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            listview.setAdapter(ADAhere);
            buildDialog("Updated Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (dishnamestr.trim().equals("")) {

                z = "Please enter Bussnessname";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {



//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Dish SET Dishname='"+dishnamestr+"',DishcatgID='"+prodcatgid+"',DishsubcatgId='"+varientid+"',Platewt='"+plateratestr+"',LedgergrpID='"+ledgergroupid+"',LedgersgrpID='"+ledgersubgroupid+"',LedgerID='"+ledgerid+"',Createby='"+creatby+"',Createdate='"+creatdate+"',Updateby='"+userid+"',Updatedate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE DishID='"+ID+"';";
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
        getMenuInflater().inflate(R.menu.product_menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
   //noinspection SimplifiableIfStatement
        if (id == R.id.menu_printer)  {
            Toast.makeText(this, "Android Menu is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Android Menu is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }



    private void buildDialog( String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Product.this);
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