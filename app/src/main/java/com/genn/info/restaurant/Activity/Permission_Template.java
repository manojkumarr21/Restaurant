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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.genn.info.restaurant.Adapter.PermissionViewAdapter;
import com.genn.info.restaurant.Adapter.TemplateListViewAdapter;
import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Module;
import com.genn.info.restaurant.Model.Permissionmodule;
import com.genn.info.restaurant.R;
import com.genn.info.restaurant.Util.Friend;
import com.genn.info.restaurant.Util.Permission;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Permission_Template extends AppCompatActivity {
    Spinner s1, s2;
    LinearLayout linearMain;
    CheckBox checkBox;
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;


    String msg;
    ConnectionClass connectionClass;
    Connection connect;

    private ListView listView;
    private View btnList;


    DBHelper dbHelper;
    String bussid, isactive, tempid, moduleid,pertempid,modulename,Permissmodulename,Permissmodulid;


    String logid, name, pass,userid,currentdate;


    ResultSet rs;
    PreparedStatement stmt;
    Permissionmodule mydata;
    List<Map<String, String>> MyData;


    private ArrayList<Permission> permissions;
    private PermissionViewAdapter adapter;

    Spinner spinnertemplate,spinnermodule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission__template);
        connectionClass = new ConnectionClass();

        listView = (ListView) findViewById(R.id.list_view);

        dbHelper = new DBHelper(this);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        currentdate = dateFormat.format(date);




        Cursor res1 = dbHelper.getAllLoginData();


        while (res1.moveToNext()) {
            logid = res1.getString(0);
            name = res1.getString(1);
            pass = res1.getString(2);
            userid = res1.getString(3);
        }


        setListViewHeader();
        setListViewAdapter();
//        setAdapterData();
        btnList.setOnClickListener(gotoSelectedListActivity());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Permission_Template.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Permission_Template.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                } else if (id == R.id.product) {


                    startActivity(new Intent(Permission_Template.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                } else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Permission_Template.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                } else if (id == R.id.nav_policy) {


                    startActivity(new Intent(Permission_Template.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                } else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(Permission_Template.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                } else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Permission_Template.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                } else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Permission_Template.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.bussiness) {

                    startActivity(new Intent(Permission_Template.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.plant) {

                    startActivity(new Intent(Permission_Template.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.uom) {

                    startActivity(new Intent(Permission_Template.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.Varient) {

                    startActivity(new Intent(Permission_Template.this, Varientmaster.class));
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
                R.layout.permission_header_listview, listView, false);
        listView.addHeaderView(header, null, false);

        btnList = (ImageButton) findViewById(R.id.button);
        spinnertemplate = (Spinner) findViewById(R.id.spinnertemplate);
        spinnermodule = (Spinner) findViewById(R.id.spinnermodule);



        String query1 = "select * from Mas_TempPermission";
        try {
            connect = connectionClass.CONN();
            stmt = connect.prepareStatement(query1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("TempID");
                String Bussnessname = rs.getString("Templatename");
                data.add(Bussnessname);

            }

            data.add(0, "select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(Permission_Template.this,
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
                    bussid = null;
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(0, "select one");
                    String[] array = data.toArray(new String[0]);
                    ArrayAdapter NoCoreAdapter = new ArrayAdapter(Permission_Template.this,
                            android.R.layout.simple_list_item_1, data);
                    spinnermodule.setAdapter(NoCoreAdapter);
//                    setListViewAdapter();
//                    setAdapterData();
                    //Do nothing.
                } else {
                    String query = "select TempID,Templatename from Mas_TempPermission where Templatename='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            pertempid = rs.getString("TempID");
//                            setListViewAdapter();
//                            setAdapterData();

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Permission_Template.this, "" + pertempid, Toast.LENGTH_SHORT)

                            .show();

                }


                String query = "select * from Mas_TempModule as tm inner join Mas_Module as mo on tm.ModuleId=mo.ModuleId where tm.TempID='"+pertempid+"'";
                try {
                    connect = connectionClass.CONN();
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();
                    ArrayList<String> data = new ArrayList<String>();

                    while (rs.next()) {
                        Permissmodulename = rs.getString("ModuleName");
//                        setListViewAdapter();
//                        setAdapterData();
                            data.add(""+Permissmodulename);
                    }

                    data.add(0, "select one");
                    String[] array = data.toArray(new String[0]);
                    ArrayAdapter NoCoreAdapter = new ArrayAdapter(Permission_Template.this,
                            android.R.layout.simple_list_item_1, data);
                    spinnermodule.setAdapter(NoCoreAdapter);

                } catch (SQLException e) {

                    e.printStackTrace();

                }



            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

spinnermodule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        String name = spinnermodule.getSelectedItem().toString();


        if (spinnermodule.getSelectedItem() == "select one") {
//            setListViewAdapter();
//            setAdapterData();
            //Do nothing.
        } else {
            String query = "select ModuleId,ModuleName from Mas_Module where ModuleName='" + name + "'";
            try {
                connect = connectionClass.CONN();
                stmt = connect.prepareStatement(query);
                rs = stmt.executeQuery();
                ArrayList<String> data = new ArrayList<String>();

                if (rs.next()) {
                    Permissmodulid = rs.getString("ModuleId");
                    MyData = null;
                    mydata = new Permissionmodule();
                    MyData = mydata.doInBackground(Permissmodulid);

                            setListViewAdapter();
                            setAdapterData();

                }


            } catch (SQLException e) {

                e.printStackTrace();

            }


            Toast.makeText(Permission_Template.this, "" + pertempid, Toast.LENGTH_SHORT)

                    .show();

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});


    }





    private void setListViewAdapter() {
        permissions = new ArrayList<Permission>();
        adapter = new PermissionViewAdapter(this, R.layout.item_listview, permissions);
        listView.setAdapter(adapter);
    }

    private void setAdapterData() {

        for (int i = 0; i < MyData.size(); i++) {
            String moduleid = MyData.get(i).get("MenuID");
           /* String menurights= MyData.get(i).get("Menuright");
            boolean menuright;

            if (menurights == null){
                menuright=false;
            }else if (menurights.equals("1")){
                menuright=true;
            }else{
                menuright=false;
            }*/


            if (spinnermodule.getSelectedItem() == "select one") {
                permissions.add(new Permission("" + MyData.get(i).get("Menuname"), false,false,false,false,false,false));
            } else {

//                    String query = "select count(*) as row from Mas_TempModule where ModuleId='" + moduleid + "'and TempID='" + bussid + "'";
                    String query = "select count(*) as row,Menuright,FA,FS,FD,FV,FX from Mas_TempPermissionDetail where ModuleId='" + Permissmodulid + "'and TempID='" + pertempid + "' and MenuID='"+moduleid+"' group by Menuright,FA,FS,FD,FV,FX";
                    try {
                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();




                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            String Menurights = String.valueOf(rs.getString("Menuright"));
                            String FA = String.valueOf(rs.getString("FA"));
                            String FS = String.valueOf(rs.getString("FS"));
                            String FD = String.valueOf(rs.getString("FD"));
                            String FV = String.valueOf(rs.getString("FV"));
                            String FX = String.valueOf(rs.getString("FX"));
                            Log.e("ddddddddddddddd33333333",""+Menurights);
                            boolean menuright;
                            boolean FAb;
                            boolean FSb;
                            boolean FDb;
                            boolean FVb;
                            boolean FXb;


                           if (Menurights.equals("1")){
                                menuright=true;
                            }else{
                                menuright=false;
                            }

                             if (FA.equals("1")){
                                 FAb=true;
                            }else{
                                 FAb=false;
                            }


                            if (FS.equals("1")){
                                FSb=true;
                            }else{
                                FSb=false;
                            }


                            if (FD.equals("1")){
                                FDb=true;
                            }else{
                                FDb=false;
                            }

                            if (FV.equals("1")){
                                FVb=true;
                            }else{
                                FVb=false;
                            }


                            if (FX.equals("1")){
                                FXb=true;
                            }else{
                                FXb=false;
                            }




                            if (bussinessid.equals("1")) {

                                permissions.add(new Permission("" + MyData.get(i).get("Menuname"), menuright,FAb,FSb,FDb,FVb,FXb));

                            } else {


                                permissions.add(new Permission("" + MyData.get(i).get("Menuname"), menuright,FAb,FSb,FDb,FVb,FXb));
                            }


                        } else {

                          permissions.add(new Permission("" + MyData.get(i).get("Menuname"), false,false,false,false,false,false));
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


//                friends.add(new Friend("" + MyData.get(i).get("ModuleName")));

            }


            adapter.notifyDataSetChanged(); // update adapter
        }
    }

    /**
     * handle footer listview button event
     *
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
                if (spinnermodule.getSelectedItem() == "select one" || spinnertemplate.getSelectedItem() == "select one") {

                    buildDialog("Selete The Template");

                } else {

                    if (!pertempid.equals(null)) {
                        int i = 0;
                        if (permissions.size() == i) { //do nothing
                        }
                        while (permissions.size() > i) {
                            final Permission friend = permissions.get(i);


                                String query = "select MenuID,Menuname from Mas_Menus where Menuname='" + friend.getName() + "'";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(query);
                                    rs = stmt.executeQuery();
                                    ArrayList<String> data = new ArrayList<String>();

                                    if (rs.next()) {
                                        tempid = rs.getString("MenuID");
                                    }

                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }


                                String query3 = "Insert into Mas_TempPermissionDetail(TempId,ModuleId,MenuID,Menuright,FA,FS,FD,FV,FX) values ('" +pertempid+ "','" + Permissmodulid + "','" + tempid + "','" +friend.isMenuright()+ "','" + friend.isFA() + "','" + friend.isFS() + "','" + friend.isFD() + "','" + friend.isFV() + "','" + friend.isFX() + "')";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(query3);
                                    rs = stmt.executeQuery();

                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }
                                Log.e("ListActivity1111111111", "here" + tempid + "" + bussid);
                                buildDialog("Update The Template");

                            i++; // rise i
                        }

                    } else {
                        Toast.makeText(Permission_Template.this, "Select the Template name", Toast.LENGTH_SHORT).show();
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


    public class UploadImage extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPostExecute(String r) {

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
                    String query = "Insert into Mas_TempModule(TempID,ModuleId,Createby,Createdate) values ('" + bussid + "','" + tempid + "','" + name + "','" + currentdate + "')";
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

    public class delete extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPostExecute(String r) {


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
                    String query = "delete from Mas_TempPermissionDetail where TempId='" + pertempid +"' and ModuleId='"+Permissmodulid+"'";
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

    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Permission_Template.this);
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