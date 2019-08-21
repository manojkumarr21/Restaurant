package com.genn.info.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AndroidRuntimeException;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Bussnessaddmodel;
import com.genn.info.restaurant.Model.Plantmodel;
import com.genn.info.restaurant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.mobsandgeeks.saripaar.annotation.Url;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Plantmaster extends AppCompatActivity implements Validator.ValidationListener {


    ConnectionClass connectionClass;
    Connection connect;

    DatePickerDialog picker;
    final Calendar cldr = Calendar.getInstance();
    int day = cldr.get(Calendar.DAY_OF_MONTH);
    int month = cldr.get(Calendar.MONTH);
    int year = cldr.get(Calendar.YEAR);

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    DBHelper dbHelper;
    String bussid,bussname,storeid,storename;


    Spinner spinnerBussness;
    Spinner spinnerstore;

    @NotEmpty
    @Length(min = 1, max = 50)
    EditText Plantname;



    @Length(min = 1, max = 50)
    EditText doorno;


    @Length(min = 1, max = 50)
    EditText street;


    @Length(min = 1, max = 50)
    EditText city;



    @Length(min = 1, max = 50)
    EditText state;



    @Length(min = 1, max = 50)
    EditText contry;


    @Length(min = 1, max = 10)
    EditText pincode;


    @Email
    @Length(min = 1, max = 50)
    EditText email;


    @Url
    @Length(min = 1, max = 50)
    EditText Web;

    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    EditText editTextPhone;


    @Length(min = 1, max = 30)
    EditText Lbtno;


    @Length(min = 1, max = 50)
    EditText Tittlenote;


    @Length(min = 1, max = 50)
    EditText Bottomnote;


    TextView Lbtdate;


    TextView Expdate;


    @Length(min = 1, max = 10)
    EditText gstno;

    @Length(min = 1, max = 10)
    EditText panno;


    @Checked
    private CheckBox checkBoxAgree;

    private Validator validator;
    private static final int RESULT_LOAD_IMAGE = 1;

    byte[] byteArray;
    String encodedImage;

    ResultSet rs;
    PreparedStatement stmt;



    ListView bussness;
    SimpleAdapter ADAhere;

    String msg;
    String ID;

    String ed_bussid,ed_plantname,ed_door,ed_street,ed_city,ed_state,ed_contry,ed_pincode,ed_email,ed_web,ed_mobile,ed_lbtno,te_lbtdt,te_wep_dt,ed_gstno,ed_panno,ed_title,ed_bottom;
    String logid,name,pass,userid,currentdate;
    String edit_bussname,edit_door,edit_street,edit_city,edit_state,edit_contry,edit_pincode,edit_email,edit_web,edit_mobile,edit_lbtno,teit_lbtdt,teit_wep_dt,edit_gstno,edit_panno,editimagestring,isactive,creatby,creatdate;

    Date lptdatefor,expdatefor;

    String bussinessid,BussID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantmaster);
        connectionClass = new ConnectionClass();


        validator = new Validator(this);
        validator.setValidationListener(this);

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


        bussness=(ListView)findViewById(R.id.bussness);

        List<Map<String,String>> MyData = null;
        Plantmodel mydata =new Plantmodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Plantname","Email","Mobile","LBTno","LBTdt","GSTno","panno","Add3","Expdt","PlantID"};

        int[] viewswhere = {R.id.bussness , R.id.email,R.id.phone,R.id.lbtno,R.id.lbtdate,R.id.gstno,R.id.panno,R.id.place,R.id.expdate,R.id.idview};

        ADAhere = new SimpleAdapter(Plantmaster.this, MyData,R.layout.plantaddlist, fromwhere, viewswhere){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view=super.getView(position, convertView, parent);

                ImageView imageView=view.findViewById(R.id.editicon);
                ImageView deleteicon=view.findViewById(R.id.deleteicon);


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("PlantID");
                        editCustomDialog();
                        Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("PlantID");
                        deleteCustomDialog();
                        Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        };

        bussness.setAdapter(ADAhere);






        Log.e("fffffffffffffffff",""+name);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Plantmaster.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Plantmaster.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Plantmaster.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Plantmaster.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_policy) {


                    startActivity(new Intent(Plantmaster.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(Plantmaster.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Plantmaster.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Plantmaster.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Plantmaster.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Plantmaster.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Plantmaster.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Plantmaster.this, Varientmaster.class));
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

    }




    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.plantaddmaster, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        Plantname=dialogView.findViewById(R.id.palantname);
        doorno=dialogView.findViewById(R.id.doorno);
        street=dialogView.findViewById(R.id.street);
        city=dialogView.findViewById(R.id.city);
        state=dialogView.findViewById(R.id.state);
        contry=dialogView.findViewById(R.id.contry);
        pincode=dialogView.findViewById(R.id.pincode);
        email=dialogView.findViewById(R.id.email);
        Web=dialogView.findViewById(R.id.Web);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        Lbtno=dialogView.findViewById(R.id.Lbtno);
        Lbtdate=dialogView.findViewById(R.id.Lbtdate);
        Expdate=dialogView.findViewById(R.id.Expdate);
        gstno=dialogView.findViewById(R.id.gstno);
        panno=dialogView.findViewById(R.id.panno);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        spinnerBussness=dialogView.findViewById(R.id.spinnerBussness);
        spinnerstore=dialogView.findViewById(R.id.spinnerstore);
        Tittlenote=dialogView.findViewById(R.id.tittlenote);
        Bottomnote=dialogView.findViewById(R.id.bottomnote);

        checkBoxAgree.setVisibility(View.GONE);



        String storequeery = "select StoreId,Storename from Mas_Store";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(storequeery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("StoreId");
                String Bussnessname = rs.getString("Storename");
                data.add(Bussnessname);

            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerstore.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        spinnerstore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinnerstore.getSelectedItem().toString();


                if (spinnerstore.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select StoreId,Storename from Mas_Store where Storename='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            storeid = rs.getString("StoreId");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Plantmaster.this, "" + storeid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });






        String query = "select BussID,Bussname from Mas_Business";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("BussID");
                String Bussnessname = rs.getString("Bussname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerBussness.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        spinnerBussness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

          public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinnerBussness.getSelectedItem().toString();


                if (spinnerBussness.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select BussID,Bussname from Mas_Business where Bussname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            bussid = rs.getString("BussID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Plantmaster.this, "" + bussid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        Lbtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Plantmaster.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Lbtdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                te_lbtdt=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Expdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Plantmaster.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Expdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                te_wep_dt=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                            }
                        }, year, month, day);
                picker.show();
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

//                validator.validate();
                ed_bussid=bussid;
                ed_plantname = Plantname.getText().toString();
                ed_door = doorno.getText().toString();
                ed_street = street.getText().toString();
                ed_city = city.getText().toString();
                ed_state = state.getText().toString();
                ed_contry = contry.getText().toString();
                ed_pincode = pincode.getText().toString();
                ed_email = email.getText().toString();
                ed_web = Web.getText().toString();
                ed_mobile =editTextPhone.getText().toString();
                ed_lbtno =Lbtno.getText().toString();
                ed_gstno=gstno.getText().toString();
                ed_panno=panno.getText().toString();
                ed_title=Tittlenote.getText().toString();
                ed_bottom=Bottomnote.getText().toString();

                if (te_wep_dt == null){
                    te_wep_dt="";
                    Log.e("1111111111111111111",""+te_wep_dt);
                }

                if (te_lbtdt == null){
                    te_lbtdt="";
                    Log.e("1111111111111111111",""+te_lbtdt);
                }




                if(Plantname.getText().toString().equals(""))

                {
                    Plantname.requestFocus();
                    Plantname.setError("FIELD CANNOT BE EMPTY");
                }else{
                    String query = "select count(*) as row from Mas_Plant where Plantname='"+ed_plantname+"' and BussID='"+ed_bussid+"'";
                    try {

                        connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();

                        if (rs.next()) {
                            bussinessid=String.valueOf(rs.getString("row"));
                            if (bussinessid.equals("0")){
                                UploadImage uploadImage = new UploadImage();
                                uploadImage.execute("");
                                alertDialog.dismiss();

                            }else {
                                Toast.makeText(Plantmaster.this, "UserName Already exit", Toast.LENGTH_SHORT).show();
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

        String edit_plant = null,edit_door = null,edit_street = null,edit_city = null,edit_state = null,edit_contry = null,edit_pincode = null,edit_email = null,edit_web = null,edit_mobile = null,edit_lbtno = null,teit_lbtdt = null,teit_wep_dt = null,edit_gstno = null,edit_panno = null,edit_tittle=null,edit_bottom=null,edit_creatby = null,edit_creatdate = null,isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.plantaddmaster, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select p.BussID,b.Bussname,p.Plantname,p.Add1,p.Add2,p.Add3,p.Add4,p.Add5,p.Pincode,p.Email,p.Storeid,p.web,p.Mobile,p.LBTno,p.LBTdt,p.Expdt,p.GSTno,p.panno,p.Titlenote,p.Footernote,p.Isactive,p.Createdby,p.Createddate from Mas_Business as b inner join  Mas_Plant  as p on b.BussID=p.BussID where p.PlantID='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid=String.valueOf(rs.getString("BussID"));
                storeid=String.valueOf(rs.getString("Storeid"));
//                storename=String.valueOf(rs.getString("Storename"));
                bussname=String.valueOf(rs.getString("Bussname"));
                edit_plant=String.valueOf(rs.getString("Plantname"));
                edit_door=String.valueOf(rs.getString("Add1"));
                edit_street=String.valueOf(rs.getString("Add2"));
                edit_city=String.valueOf(rs.getString("Add3"));
                edit_state=String.valueOf(rs.getString("Add4"));
                edit_contry=String.valueOf(rs.getString("Add5"));
                edit_pincode=String.valueOf(rs.getString("Pincode"));
                edit_email=String.valueOf(rs.getString("Email"));
                edit_web=String.valueOf(rs.getString("Web"));
                edit_mobile=String.valueOf(rs.getString("Mobile"));
                edit_lbtno=String.valueOf(rs.getString("LBTno"));
                teit_lbtdt=String.valueOf(rs.getString("LBTdt"));
                teit_wep_dt=String.valueOf(rs.getString("Expdt"));
                edit_gstno=String.valueOf(rs.getString("GSTno"));
                edit_panno=String.valueOf(rs.getString("panno"));
//                encodedImage=String.valueOf(rs.getString("Image"));
                edit_tittle=String.valueOf(rs.getString("Titlenote"));
                edit_bottom=String.valueOf(rs.getString("Footernote"));
                isactive=rs.getString("Isactive");
                edit_creatby=String.valueOf(rs.getString("Createdby"));
                edit_creatdate=String.valueOf(rs.getString("Createddate"));
            }

            Log.e("ffryutfffffff",""+isactive);

        }catch (SQLException e) {
            e.printStackTrace();
        }


        Plantname=dialogView.findViewById(R.id.palantname);
        doorno=dialogView.findViewById(R.id.doorno);
        street=dialogView.findViewById(R.id.street);
        city=dialogView.findViewById(R.id.city);
        state=dialogView.findViewById(R.id.state);
        contry=dialogView.findViewById(R.id.contry);
        pincode=dialogView.findViewById(R.id.pincode);
        email=dialogView.findViewById(R.id.email);
        Web=dialogView.findViewById(R.id.Web);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        Lbtno=dialogView.findViewById(R.id.Lbtno);
        Lbtdate=dialogView.findViewById(R.id.Lbtdate);
        Expdate=dialogView.findViewById(R.id.Expdate);
        gstno=dialogView.findViewById(R.id.gstno);
        panno=dialogView.findViewById(R.id.panno);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        creatby=""+edit_creatby;
        creatdate=""+edit_creatdate;
        spinnerBussness=dialogView.findViewById(R.id.spinnerBussness);
        Tittlenote=dialogView.findViewById(R.id.tittlenote);
        Bottomnote=dialogView.findViewById(R.id.bottomnote);








        String storeidnamequery = "select * from Mas_Store where StoreId = '"+storeid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(storeidnamequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("StoreId");
               storename= rs.getString("Storename");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String storequery = "select * from Mas_Store where StoreId != '"+storeid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(storequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("StoreId");
                String Bussnessname = rs.getString("Storename");
                data.add(Bussnessname);

            }

            data.add(0,""+storename);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerstore.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        spinnerstore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinnerstore.getSelectedItem().toString();


                if (spinnerstore.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select StoreId,Storename from Mas_Store where Storename='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            storeid = rs.getString("StoreId");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Plantmaster.this, "" + storeid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        String query1 = "select * from Mas_Business where BussID != '"+bussid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("BussID");
                String Bussnessname = rs.getString("Bussname");
                data.add(Bussnessname);

            }

            data.add(0,""+bussname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerBussness.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        spinnerBussness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinnerBussness.getSelectedItem().toString();


                if (spinnerBussness.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select BussID,Bussname from Mas_Business where Bussname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            bussid = rs.getString("BussID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Plantmaster.this, "" + bussid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        Plantname.setText(""+edit_plant);
        doorno.setText(""+edit_door);
        street.setText(""+edit_street);
        city.setText(""+edit_city);
        state.setText(""+edit_state);
        contry.setText(""+edit_contry);
        pincode.setText(""+edit_pincode);
        email.setText(""+edit_email);
        Web.setText(""+edit_web);
        editTextPhone.setText(""+edit_mobile);
        Lbtno.setText(""+edit_lbtno);
        Lbtdate.setText(""+teit_lbtdt);
        Expdate.setText(""+teit_wep_dt);
        gstno.setText(""+edit_gstno);
        panno.setText(""+edit_panno);
        Tittlenote.setText(""+edit_tittle);
        Bottomnote.setText(""+edit_bottom);



        te_lbtdt=teit_lbtdt;
        te_wep_dt=teit_wep_dt;


        if (isactive.equals("1")){
            checkBoxAgree.setChecked(true);
        }else{
            checkBoxAgree.setChecked(false);
        }



        Lbtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Plantmaster.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Lbtdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                te_lbtdt=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Expdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Plantmaster.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Expdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                te_wep_dt=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                            }
                        }, year, month, day);
                picker.show();
            }
        });




        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

      /*  builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "DELETE FROM Mas_Plant WHERE PlantID='"+ID+"'";
                try {
                    connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();

                }catch (SQLException e) {
                    e.printStackTrace();
                }
                Intent i=new Intent(Plantmaster.this,Plantmaster.class);
                startActivity(i);
                finish();
                buildDialog("Deleted Sucessfully");
            }
        });
*/
        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

//

            }
        });

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();



        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                validator.validate();
                ed_plantname = Plantname.getText().toString();
                ed_door = doorno.getText().toString();
                ed_street = street.getText().toString();
                ed_city = city.getText().toString();
                ed_state = state.getText().toString();
                ed_contry = contry.getText().toString();
                ed_pincode = pincode.getText().toString();
                ed_email = email.getText().toString();
                ed_web = Web.getText().toString();
                ed_mobile =editTextPhone.getText().toString();
                ed_lbtno =Lbtno.getText().toString();
                ed_gstno=gstno.getText().toString();
                ed_panno=panno.getText().toString();
                ed_title=Tittlenote.getText().toString();
                ed_bottom=Bottomnote.getText().toString();

                if (te_wep_dt == null){
                    te_wep_dt="";
                    Log.e("1111111111111111111",""+te_wep_dt);
                }

                if (te_lbtdt == null){
                    te_lbtdt="";
                    Log.e("1111111111111111111",""+te_lbtdt);
                }


                String query = "select count(*) as row,PlantID from Mas_Plant where Plantname='"+ed_plantname+"' and PlantID='"+ID+"' group by PlantID";
                try {

                    connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();


                    if (rs.next()) {
                        bussinessid=String.valueOf(rs.getString("row"));
                        BussID=String.valueOf(rs.getString("PlantID"));

                        if (bussinessid.equals("1") || BussID.equals(""+ID) ){
                            Update update = new Update();
                            update.execute("");
                            alertDialog.dismiss();

                        }else{
                            Toast.makeText(Plantmaster.this, "User already exit", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Update update = new Update();
                        update.execute("");
                        alertDialog.dismiss();
                    }




                }catch (SQLException e) {
                    e.printStackTrace();
                }



            }
        });

    }



    private void deleteCustomDialog() {

        //Uncomment the below code to Set the message and title from the strings.xml file

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("DELETE VARIENT");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String query = "DELETE FROM Mas_Plant WHERE PlantID='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }
                      /*  Intent i=new Intent(Plantmaster.this,Plantmaster.class);
                        startActivity(i);
                        finish();*/
                        buildDialog("Deleted Sucessfully");


                        List<Map<String,String>> MyData = null;
                        Plantmodel mydata =new Plantmodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Plantname","Email","Mobile","LBTno","LBTdt","GSTno","panno","Add3","Expdt","PlantID"};

                        int[] viewswhere = {R.id.bussness , R.id.email,R.id.phone,R.id.lbtno,R.id.lbtdate,R.id.gstno,R.id.panno,R.id.place,R.id.expdate,R.id.idview};

                        ADAhere = new SimpleAdapter(Plantmaster.this, MyData,R.layout.plantaddlist, fromwhere, viewswhere){
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                View view=super.getView(position, convertView, parent);

                                ImageView imageView=view.findViewById(R.id.editicon);
                                ImageView deleteicon=view.findViewById(R.id.deleteicon);


                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("PlantID");
                                        editCustomDialog();
                                        Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("PlantID");
                                        deleteCustomDialog();
                                        Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return view;
                            }
                        };

                        bussness.setAdapter(ADAhere);




                        Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();

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



    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "We got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }





    public class UploadImage extends AsyncTask<String,String,String>
    {

        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPostExecute(String r)
        {
            // After successful insertion of image

//           Toast.makeText(Bussinessmaster.this , ""+z, Toast.LENGTH_LONG).show();

            Log.e("ddd1111ddddddd",""+isSuccess);
            // End After successful insertion of image



            List<Map<String,String>> MyData = null;
            Plantmodel mydata =new Plantmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Plantname","Email","Mobile","LBTno","LBTdt","GSTno","panno","Add3","Expdt","PlantID"};

            int[] viewswhere = {R.id.bussness , R.id.email,R.id.phone,R.id.lbtno,R.id.lbtdate,R.id.gstno,R.id.panno,R.id.place,R.id.expdate,R.id.idview};

            ADAhere = new SimpleAdapter(Plantmaster.this, MyData,R.layout.plantaddlist, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("PlantID");
                            editCustomDialog();
                            Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("PlantID");
                            deleteCustomDialog();
                            Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };

            bussness.setAdapter(ADAhere);

            buildDialog("Inserted Sucessfully");
        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ed_plantname.trim().equals("") && ed_bussid.trim().equals("")) {

                z = "Please enter Bussnessname and Bussiness id";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        if (te_lbtdt.equals("null")){
                            te_lbtdt="";
                        }
                        if (te_wep_dt.equals("null")){
                            te_wep_dt="";
                        }


                        String query = "Insert into Mas_Plant(BussID,Storeid,Plantname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Titlenote,Footernote,Createdby,Createddate) values ('" + ed_bussid + "','" + storeid + "','" + ed_plantname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + ed_title + "','" + ed_bottom + "','" + userid + "','" + currentdate + "')";
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
            // After successful insertion of image

//           Toast.makeText(Bussinessmaster.this , ""+z, Toast.LENGTH_LONG).show();

            Log.e("ddd1111ddddddd",""+isSuccess);
            // End After successful insertion of image


            List<Map<String,String>> MyData = null;
            Plantmodel mydata =new Plantmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Plantname","Email","Mobile","LBTno","LBTdt","GSTno","panno","Add3","Expdt","PlantID"};

            int[] viewswhere = {R.id.bussness , R.id.email,R.id.phone,R.id.lbtno,R.id.lbtdate,R.id.gstno,R.id.panno,R.id.place,R.id.expdate,R.id.idview};

            ADAhere = new SimpleAdapter(Plantmaster.this, MyData,R.layout.plantaddlist, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("PlantID");
                            editCustomDialog();
                            Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("PlantID");
                            deleteCustomDialog();
                            Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };

            bussness.setAdapter(ADAhere);

            buildDialog( "Updated Sucessfully");

        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ed_plantname.trim().equals("")) {

                z = "Please enter Bussnessname";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        if (te_lbtdt.equals("null")){
                            te_lbtdt="";
                        }
                        if (te_wep_dt.equals("null")){
                            te_wep_dt="";
                        }

//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Plant SET BussID='"+bussid+"',Storeid='"+storeid+"',Plantname='"+ed_plantname+"', Add1='"+ed_door+"',Add2='"+ed_street+"',Add3='"+ed_city+"',Add4='"+ed_state+"',Add5='"+ed_contry+"',Pincode='"+ed_pincode+"',Email='"+ed_email+"',Web='"+ed_web+"',Mobile='"+ed_mobile+"',LBTno='"+ed_lbtno+"',LBTdt='"+te_lbtdt+"',Expdt='"+te_wep_dt+"',GSTno='"+ed_gstno+"',panno='"+ed_panno+"',Titlenote='"+ed_title+"',Footernote='"+ed_bottom+"',Createdby='"+creatby+"',Createddate='"+creatdate+"',Updatedby='"+name+"',Updateddate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE PlantID='"+ID+"';";
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
    private void delete() {
        List<Map<String,String>> MyData = null;
        Plantmodel mydata =new Plantmodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Plantname","Email","Mobile","LBTno","LBTdt","GSTno","panno","Add3","Expdt","PlantID"};

        int[] viewswhere = {R.id.bussness , R.id.email,R.id.phone,R.id.lbtno,R.id.lbtdate,R.id.gstno,R.id.panno,R.id.place,R.id.expdate,R.id.idview};

        ADAhere = new SimpleAdapter(Plantmaster.this, MyData,R.layout.plantaddlist, fromwhere, viewswhere){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view=super.getView(position, convertView, parent);

                ImageView imageView=view.findViewById(R.id.editicon);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("PlantID");
                        editCustomDialog();
                        Toast.makeText(Plantmaster.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        };


    }

    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Plantmaster.this);
        builder.setTitle("Bussness Master");
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
