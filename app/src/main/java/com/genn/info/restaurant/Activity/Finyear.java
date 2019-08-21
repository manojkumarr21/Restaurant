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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Finyearmodel;
import com.genn.info.restaurant.Model.Usergroupmodel;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Finyear extends AppCompatActivity {


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
    String bussid,ed_start,ed_end;

    private Validator validator;
    String logid,name,pass,userid,currentdate,start,end;


    @Length(min = 1, max = 50)
    EditText finyear;

    String finyearstr;

    ResultSet rs;
    PreparedStatement stmt;


    ListView finyearlist;
    SimpleAdapter ADAhere;


    CheckBox checkBoxAgree;

    String msg;
    String ID,status,ed_uom,edit_plant= null,creatby,creatdate;

    TextView startdate;


    TextView todate;
    Date dateend,datestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finyear);

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

                    startActivity(new Intent(Finyear.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Finyear.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Finyear.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Finyear.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_policy) {


                    startActivity(new Intent(Finyear.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(Finyear.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Finyear.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Finyear.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Finyear.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Finyear.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Finyear.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Finyear.this, Varientmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.usergroup) {

                    startActivity(new Intent(Finyear.this, Usergroup.class));
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


        finyearlist=(ListView)findViewById(R.id.finyearlist);




        List<Map<String,String>> MyData = null;
        Finyearmodel mydata =new Finyearmodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Yeardesc","ID"};

        int[] viewswhere = {R.id.usergroupname ,R.id.idview};

        ADAhere = new SimpleAdapter(Finyear.this, MyData,R.layout.finyearadd, fromwhere, viewswhere){
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
                        ID=(String)obj.get("ID");
                        editCustomDialog();
                        Toast.makeText(Finyear.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("ID");
                        deleteCustomDialog();
                    }
                });
                return view;
            }
        };

        finyearlist.setAdapter(ADAhere);




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
        View dialogView = LayoutInflater.from(this).inflate(R.layout.finyearaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        finyear=dialogView.findViewById(R.id.et_finyear);
        startdate=dialogView.findViewById(R.id.startdate);
        todate=dialogView.findViewById(R.id.todate);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);

        checkBoxAgree.setVisibility(View.GONE);


        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Finyear.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                start=""+year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                            }
                        }, year, month, day);

                picker.show();
            }
        });

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Finyear.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                end=""+year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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


                ed_uom = finyear.getText().toString();

                if (finyear.getText().toString().equals("")) {
                    finyear.requestFocus();
                    finyear.setError("FIELD CANNOT BE EMPTY");
                } else {

                    finyearstr = finyear.getText().toString();


                    String query = "select count(*) as row from Mas_Finyear where Yeardesc='"+finyearstr+"'";
                    try {

                        connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            boolean b = false;
                            SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                            String bussinessid=String.valueOf(rs.getString("row"));
                            if (bussinessid.equals("0")){

                                if(dfDate.parse(start).before(dfDate.parse(end)))
                                {
                                    b = true;//If start date is before end date
                                    UploadImage uploadImage = new UploadImage();
                                    uploadImage.execute("");
                                    alertDialog.dismiss();
                                    Log.e("dddddddddddddddeee","before");
                                }
                                else if(dfDate.parse(start).equals(dfDate.parse(end)))
                                {
                                    Log.e("dddddddddddddddeee","equal");
                                    b = true;//If two dates are equal
                                }
                                else
                                {
                                    Log.e("dddddddddddddddeee","after");
                                    b = false; //If start date is after the end date
                                }


                              /*  if (datestart.compareTo(dateend) > 0) {
                                    System.out.println("Date1 is After Date2");
                                    buildDialog("Date1 is After Date2");
                                } else if (datestart.compareTo(dateend) < 0) {

                                    UploadImage uploadImage = new UploadImage();
                                    uploadImage.execute("");
                                    alertDialog.dismiss();
                                } else if (datestart.compareTo(dateend) == 0) {
                                    System.out.println("Date1 is equal to Date2");
                                    buildDialog("Date1 is equal to Date2");
                                } else {
                                    System.out.println("How to get here?");
                                    buildDialog("enterthe startdate and end date");
                                }
*/

                            }else {
                                Toast.makeText(Finyear.this, "UserName Already exit", Toast.LENGTH_SHORT).show();
                            }

                        }else{


                            boolean b = false;
                            SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");

                            if(dfDate.parse(start).before(dfDate.parse(end)))
                            {
                                b = true;//If start date is before end date
                                UploadImage uploadImage = new UploadImage();
                                uploadImage.execute("");
                                alertDialog.dismiss();
                                Log.e("dddddddddddddddeee","before");
                            }
                            else if(dfDate.parse(start).equals(dfDate.parse(end)))
                            {
                                Log.e("dddddddddddddddeee","equal");
                                b = true;//If two dates are equal
                            }
                            else
                            {
                                Log.e("dddddddddddddddeee","after");
                                b = false; //If start date is after the end date
                            }



                         /*   if (datestart.compareTo(dateend) > 0) {
                                System.out.println("Date1 is After Date2");
                                buildDialog("Date1 is After Date2");
                            } else if (datestart.compareTo(dateend) < 0) {

                                UploadImage uploadImage = new UploadImage();
                                uploadImage.execute("");
                                alertDialog.dismiss();
                            } else if (datestart.compareTo(dateend) == 0) {
                                System.out.println("Date1 is equal to Date2");
                                buildDialog("Date1 is equal to Date2");
                            } else {
                                System.out.println("How to get here?");
                                buildDialog("enterthe startdate and end date");
                            }*/
                        }




                    }catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

    }

    private void editCustomDialog() {

        String edit_uom = null,edit_plant= null,edit_creatby = null,edit_creatdate = null,isactive = null;

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.finyearaddlist, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Finyear where ID='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid=String.valueOf(rs.getString("ID"));
                edit_plant=String.valueOf(rs.getString("Yeardesc"));
                ed_start=String.valueOf(rs.getString("Starton"));
                ed_end=String.valueOf(rs.getString("Endon"));
                edit_creatby=String.valueOf(rs.getString("Createdby"));
                edit_creatdate=String.valueOf(rs.getString("Createddate"));

                isactive=rs.getString("Isactive");

            }

            Log.e("ffryutfffffff",""+isactive);

        }catch (SQLException e) {
            e.printStackTrace();
        }


        finyear=dialogView.findViewById(R.id.et_finyear);
        startdate=dialogView.findViewById(R.id.startdate);
        todate=dialogView.findViewById(R.id.todate);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);



        checkBoxAgree.setVisibility(View.VISIBLE);



        finyear.setText(""+edit_plant);
        startdate.setText(""+ed_start);
        todate.setText(""+ed_end);
        start=""+ed_start;
        end=""+ed_end;
        creatby=""+edit_creatby;
        creatdate=""+edit_creatdate;

        if (isactive.equals("1")){
            checkBoxAgree.setChecked(true);
        }else{
            checkBoxAgree.setChecked(false);
        }



        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Finyear.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                start=""+year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                            }
                        }, year, month, day);

                picker.show();
            }
        });

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Finyear.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                end=""+year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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


                finyearstr = finyear.getText().toString();

                if (finyear.getText().toString().equals("")) {
                    finyear.requestFocus();
                    finyear.setError("FIELD CANNOT BE EMPTY");
                } else {
                    String query = "select count(*) as row,ID from Mas_Finyear where Yeardesc='" + finyearstr + "'group by  ID";
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
                            String BussID = String.valueOf(rs.getString("ID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                                boolean b = false;
                                SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
                                if(dfDate.parse(start).before(dfDate.parse(end)))
                                {
                                    Update update = new Update();
                                    update.execute("");
                                    alertDialog.dismiss();
                                    b = true;//If start date is before end date
                                    Log.e("dddddddddddddddeee","before");
                                }
                                else if(dfDate.parse(start).equals(dfDate.parse(end)))
                                {
                                    Log.e("dddddddddddddddeee","equal");
                                    b = true;//If two dates are equal
                                }
                                else
                                {
                                    Log.e("dddddddddddddddeee","after");
                                    b = false; //If start date is after the end date
                                }


                            /*    if (datestart.compareTo(dateend) > 0) {
                                    System.out.println("Date1 is After Date2");
                                    buildDialog("Date1 is After Date2");
                                } else if (datestart.compareTo(dateend) < 0) {
                                    Update update = new Update();
                                    update.execute("");
                                    alertDialog.dismiss();
                                } else if (datestart.compareTo(dateend) == 0) {
                                    System.out.println("Date1 is equal to Date2");
                                    buildDialog("Date1 is equal to Date2");
                                } else {
                                    System.out.println("How to get here?");
                                    buildDialog("enterthe startdate and end date");
                                }
*/


                            } else
                                {
                                Toast.makeText(Finyear.this, "User already exit", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                            {
                                boolean b = false;
                                SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");

                                if(dfDate.parse(start).before(dfDate.parse(end)))
                                {
                                    Update update = new Update();
                                    update.execute("");
                                    alertDialog.dismiss();
                                    b = true;//If start date is before end date
                                    Log.e("dddddddddddddddeee","before");
                                }
                                else if(dfDate.parse(start).equals(dfDate.parse(end)))
                                {
                                    Log.e("dddddddddddddddeee","equal");
                                    b = true;//If two dates are equal
                                }
                                else
                                {
                                    Log.e("dddddddddddddddeee","after");
                                    b = false; //If start date is after the end date
                                }
                        /*    if (datestart.compareTo(dateend) > 0) {
                                System.out.println("Date1 is After Date2");
                                buildDialog("Date1 is After Date2");
                            } else if (datestart.compareTo(dateend) < 0) {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();
                            } else if (datestart.compareTo(dateend) == 0) {
                                System.out.println("Date1 is equal to Date2");
                                buildDialog("Date1 is equal to Date2");
                            } else {
                                System.out.println("How to get here?");
                                buildDialog("enterthe startdate and end date");
                            }*/
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        });


    }
    private void deleteCustomDialog() {

        //Uncomment the below code to Set the message and title from the strings.xml file

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("DELETE FINYEAR");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String query = "DELETE FROM Mas_Finyear WHERE ID='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }

                        List<Map<String,String>> MyData = null;
                        Finyearmodel mydata =new Finyearmodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Yeardesc","ID"};

                        int[] viewswhere = {R.id.usergroupname ,R.id.idview};

                        ADAhere = new SimpleAdapter(Finyear.this, MyData,R.layout.finyearadd, fromwhere, viewswhere){
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
                                        ID=(String)obj.get("ID");
                                        editCustomDialog();
                                        Toast.makeText(Finyear.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("ID");
                                        deleteCustomDialog();
                                    }
                                });
                                return view;
                            }
                        };

                        finyearlist.setAdapter(ADAhere);

                        buildDialog("Deleted Sucessfully");
                        Toast.makeText(Finyear.this, ID, Toast.LENGTH_SHORT).show();

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
            Finyearmodel mydata =new Finyearmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Yeardesc","ID"};

            int[] viewswhere = {R.id.usergroupname ,R.id.idview};

            ADAhere = new SimpleAdapter(Finyear.this, MyData,R.layout.finyearadd, fromwhere, viewswhere){
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
                            ID=(String)obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(Finyear.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            finyearlist.setAdapter(ADAhere);


            buildDialog("Inserted Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (finyearstr.trim().equals("") ) {

                z = "Please enter Uomname ";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "Insert into Mas_Finyear(Yeardesc,Starton,Endon,Createdby,Createddate) values ('" + finyearstr + "','" + start + "','" + end + "','" + userid + "','" + currentdate + "')";
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
            Finyearmodel mydata =new Finyearmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Yeardesc","ID"};

            int[] viewswhere = {R.id.usergroupname ,R.id.idview};

            ADAhere = new SimpleAdapter(Finyear.this, MyData,R.layout.finyearadd, fromwhere, viewswhere){
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
                            ID=(String)obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(Finyear.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            finyearlist.setAdapter(ADAhere);

            buildDialog("Updated Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (finyearstr.trim().equals("")) {

                z = "Please enter Bussnessname";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {



//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Finyear SET Yeardesc='"+finyearstr+"',Starton='"+start+"',Endon='"+end+"',Createdby='"+creatby+"',Createddate='"+creatdate+"',Updatedby='"+userid+"',Updateddate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE ID='"+ID+"';";
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


    private void buildDialog( String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Finyear.this);
        builder.setTitle("Finyear Master");
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
