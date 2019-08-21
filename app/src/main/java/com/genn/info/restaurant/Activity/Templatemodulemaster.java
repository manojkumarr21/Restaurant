package com.genn.info.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Adapter.TemplateListViewAdapter;
import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.MainActivity;
import com.genn.info.restaurant.Model.Module;
import com.genn.info.restaurant.Model.Plantmodel;
import com.genn.info.restaurant.Model.Varientmodel;
import com.genn.info.restaurant.R;
import com.genn.info.restaurant.Util.Friend;
import com.google.android.material.navigation.NavigationView;

import java.io.IOError;
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

public class Templatemodulemaster extends AppCompatActivity {


    private ListView listView;
    private View btnList;

    DatePickerDialog picker;
    final Calendar cldr = Calendar.getInstance();
    int day = cldr.get(Calendar.DAY_OF_MONTH);
    int month = cldr.get(Calendar.MONTH);
    int year = cldr.get(Calendar.YEAR);

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;
    String logid,name,pass,userid,currentdate;

    DBHelper dbHelper;

    private ArrayList<Friend> friends;
    private TemplateListViewAdapter adapter;
    ConnectionClass connectionClass;
    Connection connect;
    ResultSet rs;
    PreparedStatement stmt;
    Module mydata;
    List<Map<String, String>> MyData;
    Spinner spinnertemplate;
    String bussid,bussname,tempid;

    String msg;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templatemodulemaster);
        connectionClass = new ConnectionClass();

        listView = (ListView) findViewById(R.id.list_view);

        dbHelper=new DBHelper(this);
        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        currentdate=dateFormat.format(date);

        MyData = null;
        mydata = new Module();
        MyData = mydata.doInBackground();


        Cursor res1 = dbHelper.getAllLoginData();


        while (res1.moveToNext()) {
            logid = res1.getString(0);
            name = res1.getString(1);
            pass = res1.getString(2);
            userid = res1.getString(3);

        }



        setListViewHeader();
        setListViewAdapter();
        setAdapterData();
        btnList.setOnClickListener(gotoSelectedListActivity());





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Templatemodulemaster.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Templatemodulemaster.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Templatemodulemaster.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Templatemodulemaster.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_policy) {


                    startActivity(new Intent(Templatemodulemaster.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(Templatemodulemaster.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Templatemodulemaster.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Templatemodulemaster.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Templatemodulemaster.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Templatemodulemaster.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Templatemodulemaster.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Templatemodulemaster.this, Varientmaster.class));
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




    }


    private void setListViewHeader() {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(
                R.layout.header_listview, listView, false);
        listView.addHeaderView(header, null, false);

        btnList = (ImageButton) findViewById(R.id.button);
        spinnertemplate = (Spinner) findViewById(R.id.spinnertemplate);


        String query1 = "select * from Mas_TempPermission";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("TempID");
                String Bussnessname = rs.getString("Templatename");
                data.add(Bussnessname);

            }

            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnertemplate.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        spinnertemplate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinnertemplate.getSelectedItem().toString();


                if (spinnertemplate.getSelectedItem() == "select one") {
                    bussid =null;
                    setListViewAdapter();
                    setAdapterData();
                    //Do nothing.
                }
                else
                    {
                    String query = "select TempID,Templatename from Mas_TempPermission where Templatename='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            bussid = rs.getString("TempID");
                            setListViewAdapter();
                            setAdapterData();

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Templatemodulemaster.this, "" + bussid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    private void setListViewAdapter() {
        friends = new ArrayList<Friend>();
        adapter = new TemplateListViewAdapter(this, R.layout.item_listview, friends);
        listView.setAdapter(adapter);
    }

    private void setAdapterData() {

        for (int i = 0; i < MyData.size(); i++) {
            String moduleid=MyData.get(i).get("ModuleId");

            if (spinnertemplate.getSelectedItem() == "select one")
            {
//                friends.add(new Friend("" + MyData.get(i).get("ModuleName"),false));
            }
            else{
                if (MyData.get(i).get("Isactive").equals("1"))
                {
//                    String query = "select count(*) as row from Mas_TempModule where ModuleId='" + moduleid + "'and TempID='" + bussid + "'";
                    String query = "select count(*) as row from Mas_TempModule where ModuleId='" + moduleid + "'and TempID='" + bussid + "'";
                    try {
                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));


                            if (bussinessid.equals("1")) {
                                friends.add(new Friend("" + MyData.get(i).get("ModuleName"),true));

                            }
                            else
                            {
                                friends.add(new Friend("" + MyData.get(i).get("ModuleName"),false));
                            }


                        }
                        else
                        {
                            friends.add(new Friend("" + MyData.get(i).get("ModuleName"),true));
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }




//                friends.add(new Friend("" + MyData.get(i).get("ModuleName")));
                }
            }


            adapter.notifyDataSetChanged(); // update adapter
        }
    }
    /**
     * handle footer listview button event
     * @return
     */
    private View.OnClickListener gotoSelectedListActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Templatemodulemaster.this, SelectedListActivity.class);
                intent.putParcelableArrayListExtra("Checked List", friends);


                startActivity(intent);*/

                delete delete = new delete();
                delete.execute("");
                if (spinnertemplate.getSelectedItem() == "select one")
                {

                    buildDialog("Selete The Template");

                }
                else {

                    if(!bussid.equals(null)) {
                        int i = 0;
                        if (friends.size() == i) { //do nothing
                        }
                        while (friends.size() > i) {
                            final Friend friend = friends.get(i);
                            if (friend.isSelected()) {

                                String query = "select ModuleId,ModuleName from Mas_Module where ModuleName='" + friend.getName()+"'";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(query);
                                    rs = stmt.executeQuery();
                                    ArrayList<String> data = new ArrayList<String>();

                                    if (rs.next()) {
                                        tempid = rs.getString("ModuleId");
                                    }

                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }


                                String query3 = "Insert into Mas_TempModule(TempID,ModuleId,Createby,Createdate) values ('" + bussid + "','" + tempid + "','" + userid + "','" + currentdate + "')";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(query3);
                                    rs = stmt.executeQuery();

                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }
                                Log.e("ListActivity1111111111", "here" + tempid+""+bussid);
                                buildDialog("Update The Template");
                            }
                            i++; // rise i
                        }

                    }
                    else{
                        Toast.makeText(Templatemodulemaster.this, "Select the Template name", Toast.LENGTH_SHORT).show();
                    }
                }


                setListViewAdapter();
                setAdapterData();


            }
        };
    }



   /* private void deleteCustomDialog() {

        //Uncomment the below code to Set the message and title from the strings.xml file

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Template Module");

        //Setting message manually and performing action on button click
        builder.setMessage("Master")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

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
*/


    public class UploadImage extends AsyncTask<String,String,String>
    {

        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPostExecute(String r)
        {

        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";


            try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "Insert into Mas_TempModule(TempID,ModuleId,Createby,Createdate) values ('" + bussid + "','" + tempid + "','" + userid + "','" + currentdate + "')";
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
    }
    public class delete extends AsyncTask<String,String,String>
    {

        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPostExecute(String r)
        {



        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";


            try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "delete from Mas_TempModule where TempID='" + bussid + "'";
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(Templatemodulemaster.this);
        builder.setTitle("Template Master");
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
