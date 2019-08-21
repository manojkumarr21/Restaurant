package com.genn.info.restaurant.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.Build;
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
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Plantmodel;
import com.genn.info.restaurant.Model.Usermodel;
import com.genn.info.restaurant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.guna.libmultispinner.MultiSelectionSpinner;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.mobsandgeeks.saripaar.annotation.Url;

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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static android.net.wifi.WifiConfiguration.Status.strings;


public class User extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

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
    String empid,empname,usergroupid,usergroupname,tempid,plantid;
    String[] smaterialtype = {"Select One","Administrator","User"};


    Spinner spinneremp,spinnerusergroup,spinnerusertype;

    @NotEmpty
    @Length(min = 1, max = 50)
    EditText username;



    @Length(min = 1, max = 50)
    EditText password;






    @Email
    @Length(min = 1, max = 50)
    EditText email;




    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    EditText editTextPhone;




    @Checked
    private CheckBox checkBoxAgree;

    private Validator validator;
    private static final int RESULT_LOAD_IMAGE = 1;

    byte[] byteArray;
    String encodedImage;

    ResultSet rs;
    PreparedStatement stmt;
    String logid,name,pass,useridid,currentdate,usertypeid;


    ListView userlist;
    SimpleAdapter ADAhere;

    String msg;
    String ID;


    String key="Genn@GtnInfo";
    String encoded;

   String encodeedpass,decodeedpass;

    Date lptdatefor,expdatefor;

    String bussinessid,BussID,Isact,empnoooo;

    String ed_empid,ed_usergroupid,ed_usergrouptype,ed_username,ed_pass,ed_email,ed_mobile;
    String userid=null,usernamee=null,edit_usetname=null,edit_password=null,edit_mobileno=null,edit_groupname=null,edit_mailid=null,edit_usertype=null,edit_email = null,edit_web = null,edit_mobile = null,edit_lbtno = null,teit_lbtdt = null,teit_wep_dt = null,edit_gstno = null,edit_panno = null,edit_tittle=null,edit_bottom=null,edit_creatby = null,edit_creatdate = null,isactive = null
,creatby,creatdate;

    ArrayList<String> temparr=null,plantarr=null;
    MultiSelectionSpinner multiSelectionSpinner,multiSelectionSpinner1;

    String tempstring,planstring;
    int[] spinnerselctplantid;
    int[] spinnerselcttempid;
    String[] spinnerselctedplantid;
    String[] spinnerselctedtempid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
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
            useridid = res1.getString(3);
        }


        userlist=(ListView)findViewById(R.id.userlist);


        List<Map<String,String>> MyData = null;
        Usermodel mydata =new Usermodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Empno","Username","Mailid","Mobileno","Usertype","Groupname","UserID"};

        int[] viewswhere = {R.id.empnoo , R.id.username,R.id.email,R.id.phone,R.id.usertypeee,R.id.usergrouppp,R.id.idview};

        ADAhere = new SimpleAdapter(User.this, MyData,R.layout.useraddlist, fromwhere, viewswhere){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view=super.getView(position, convertView, parent);

                ImageView imageView=view.findViewById(R.id.editicon);
                ImageView deleteicon=view.findViewById(R.id.deleteicon);


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("UserID");
                       editCustomDialog();
                        Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("UserID");
                      deleteCustomDialog();
                        Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        };

        userlist.setAdapter(ADAhere);




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

                    startActivity(new Intent(User.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(User.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(User.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(User.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_policy) {


                    startActivity(new Intent(User.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(User.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(User.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(User.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(User.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(User.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(User.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(User.this, Varientmaster.class));
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
        View dialogView = LayoutInflater.from(this).inflate(R.layout.useraddmaster, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        username=dialogView.findViewById(R.id.username);
        password=dialogView.findViewById(R.id.password);
        email=dialogView.findViewById(R.id.email);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        spinneremp=dialogView.findViewById(R.id.spinneremp);
        spinnerusergroup=dialogView.findViewById(R.id.spinnerusergroup);
        spinnerusertype=dialogView.findViewById(R.id.spinnerusertype);
        checkBoxAgree.setVisibility(View.GONE);





        final String plantqurey = "select PlantID,Plantname from Mas_Plant";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(plantqurey);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("PlantID");
                String Bussnessname = rs.getString("Plantname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array1 = data.toArray(new String[0]);
            MultiSelectionSpinner multiSelectionSpinner =dialogView.findViewById(R.id.plant);
            multiSelectionSpinner.setItems(array1);

//            multiSelectionSpinner.setSelection(new int[]{0});
            multiSelectionSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
                @Override
                public void selectedIndices(List<Integer> indices) {

                    for (int i=0;i<indices.size();i++){

                        Log.e("fffff------333333334",""+indices.get(i));
                        planstring=""+indices.get(i);
//                        Toast.makeText(User.this, ""+strings.get(i), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void selectedStrings(List<String> strings) {
                    plantarr = new ArrayList<String>();



                    for (int i=0;i<strings.size();i++){

                        if (strings.get(i) == "select one") {

                            //Do nothing.
                        } else {

                            String query = "select PlantID,Plantname from Mas_Plant where Plantname='" +strings.get(i)+ "'";
                            try {
                                connect = connectionClass.CONN();
                                stmt = connect.prepareStatement(query);
                                rs = stmt.executeQuery();


                                while (rs.next()) {
                                    plantid = rs.getString("PlantID");
                                    plantarr.add(plantid);
                                }


                            } catch (SQLException e) {

                                e.printStackTrace();

                            }


                            Toast.makeText(User.this, "" + plantarr, Toast.LENGTH_SHORT)

                                    .show();

                        }
                    }


                }
            });

        } catch (SQLException e) {

            e.printStackTrace();

        }





        String templatequrey = "select TempID,Templatename from Mas_TempPermission";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(templatequrey);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("TempID");
                String Bussnessname = rs.getString("Templatename");
                data.add(Bussnessname);

            }
          data.add(0,"select one");
            String[] array2 = data.toArray(new String[0]);
            MultiSelectionSpinner multiSelectionSpinner1 =dialogView.findViewById(R.id.template);
            multiSelectionSpinner1.setItems(array2);
//            multiSelectionSpinner1.setSelection(new int[]{0});



            multiSelectionSpinner1.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {



                @Override
                public void selectedIndices(List<Integer> indices) {



                    for (int i=0;i<indices.size();i++){

                        Log.e("fffff------333333334",""+indices.get(i));
                        tempstring=""+indices.get(i);
//                        Toast.makeText(User.this, ""+strings.get(i), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void selectedStrings(List<String> strings1) {

                    temparr = new ArrayList<String>();






                    for (int i=0;i<strings1.size();i++){

                        if (strings1.get(i) == "select one") {

                            //Do nothing.
                        } else {

                            String query = "select TempID,Templatename from Mas_TempPermission where Templatename='" + strings1.get(i) + "'";
                            try {
                                connect = connectionClass.CONN();
                                stmt = connect.prepareStatement(query);
                                rs = stmt.executeQuery();


                                while (rs.next()) {
                                    tempid = rs.getString("TempID");
                                    temparr.add(tempid);
                                }

                                Log.e("fefe====gfegetgtytry",""+temparr.size());
                            } catch (SQLException e) {

                                e.printStackTrace();

                            }


                            Toast.makeText(User.this, "" + tempid, Toast.LENGTH_SHORT)

                                    .show();

                        }


                    }

                    for (int i=0;i<temparr.size();i++)
                    {
                        Log.e("fefe====rger324235425",temparr.get(i));
                    }

//      Log.e("feferger324235425",""+temparr.size());

                }
            });

        } catch (SQLException e) {

            e.printStackTrace();

        }




        String query = "select Empno,Name from Mas_Employee";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("Empno");
                String Bussnessname = rs.getString("Name");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinneremp.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        spinneremp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinneremp.getSelectedItem().toString();


                if (spinneremp.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select Empno,Name from Mas_Employee where Name='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            empid = rs.getString("Empno");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(User.this, "" + empid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String query1 = "select UserGroupID,Groupname from Mas_Usergroup";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("UserGroupID");
                String Bussnessname = rs.getString("Groupname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerusergroup.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }



        spinnerusergroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinnerusergroup.getSelectedItem().toString();


                if (spinnerusergroup.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select UserGroupID,Groupname from Mas_Usergroup where Groupname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            usergroupid = rs.getString("UserGroupID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(User.this, "" + usergroupid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, smaterialtype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerusertype.setAdapter(adapter2);


        spinnerusertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (spinnerusertype.getSelectedItem() == "Select One") {

                    //Do nothing.
                } else {

                    Toast.makeText(getApplicationContext(), ""+spinnerusertype.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    usertypeid=""+spinnerusertype.getSelectedItem();

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

//                validator.validate();
                ed_empid=empid;
                ed_usergroupid=usergroupid;
                ed_usergrouptype=usertypeid;
                ed_username = username.getText().toString();
                ed_pass = password.getText().toString();
                ed_email = email.getText().toString();
                ed_mobile =editTextPhone.getText().toString();



Log.e("fwefwfgrgfw4444r23232",""+planstring);
Log.e("fwefwfgrgfw4444r23232",""+tempstring);

                if(username.getText().toString().equals(""))

                {
                    username.requestFocus();
                    username.setError("FIELD CANNOT BE EMPTY");
                }
                if(password.getText().toString().equals(""))
                {
                    password.requestFocus();
                    password.setError("FIELD CANNOT BE EMPTY");
                }
                if(editTextPhone.getText().toString().equals(""))
                {
                    editTextPhone.requestFocus();
                    editTextPhone.setError("FIELD CANNOT BE EMPTY");
                }
                if(ed_usergrouptype.equals("Select One"))
                {
                    Toast.makeText(User.this, "Choose the User group", Toast.LENGTH_SHORT).show();
                }


                if(planstring == null || tempstring == null || planstring == "" || tempstring == "")
                {
                    Toast.makeText(User.this, "Choose the Plant", Toast.LENGTH_SHORT).show();
                }

                else{
                    String query = "select count(*) as row from Mas_User where Username='"+ed_username+"' or Empno='"+ed_empid+"' or (Mobileno='"+ed_mobile+"' and Isactive=1)";
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
                                Toast.makeText(User.this, "UserName Already exit", Toast.LENGTH_SHORT).show();
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



    private void deleteCustomDialog() {

        //Uncomment the below code to Set the message and title from the strings.xml file

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("DELETE VARIENT");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String deleteplantuse = "DELETE FROM MAs_UserPlantDetail WHERE UserId='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(deleteplantuse);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }


                        String deletetempuse = "DELETE FROM Mas_UserTempDetail WHERE UserId='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(deletetempuse);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }

                        String deletetempmenuuse = "DELETE FROM Mas_UserTempMenu WHERE UserId='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(deletetempmenuuse);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }


                        String query = "DELETE FROM Mas_User WHERE UserID='"+ID+"'";
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
                        Usermodel mydata =new Usermodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Empno","Username","Mailid","Mobileno","Usertype","Groupname","UserID"};

                        int[] viewswhere = {R.id.empnoo , R.id.username,R.id.email,R.id.phone,R.id.usertypeee,R.id.usergrouppp,R.id.idview};

                        ADAhere = new SimpleAdapter(User.this, MyData,R.layout.useraddlist, fromwhere, viewswhere){
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                View view=super.getView(position, convertView, parent);

                                ImageView imageView=view.findViewById(R.id.editicon);
                                ImageView deleteicon=view.findViewById(R.id.deleteicon);


                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("UserID");
                                        editCustomDialog();
                                        Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("UserID");
                      deleteCustomDialog();
                                        Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return view;
                            }
                        };

                        userlist.setAdapter(ADAhere);

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




    private void editCustomDialog() {

        String userid=null,usernamee=null,tempusermulid=null,empname=null,edit_usetname=null,edit_password=null,edit_mobileno=null,edit_groupname=null,edit_mailid=null,edit_usertype=null,edit_email = null,edit_web = null,edit_mobile = null,edit_lbtno = null,teit_lbtdt = null,teit_wep_dt = null,edit_gstno = null,edit_panno = null,edit_tittle=null,edit_bottom=null,edit_creatby = null,edit_creatdate = null,isactive = null;

        ArrayList<String> userplantid = new ArrayList<String>();
        ArrayList<String> tempuserid = new ArrayList<String>();
        String positionidconcat="";
        String positionidconcat1="";
        plantarr = new ArrayList<String>();
        temparr = new ArrayList<String>();
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.useraddmaster, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_User as mu left outer join Mas_Employee as me on mu.Empno=me.Empno inner join Mas_Usergroup as mg on mu.Usergrpid=mg.UserGroupID where UserId='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//          stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                userid=String.valueOf(rs.getString("UserID"));
                empname=String.valueOf(rs.getString("Name"));
                edit_usetname=String.valueOf(rs.getString("Username"));
                edit_password=String.valueOf(rs.getString("Userpwd"));

                edit_mobileno=String.valueOf(rs.getString("Mobileno"));
                edit_groupname=String.valueOf(rs.getString("Groupname"));
                edit_mailid=String.valueOf(rs.getString("Mailid"));
                edit_usertype=String.valueOf(rs.getString("Usertype"));
                isactive=rs.getString("Isactive");
                edit_creatby=String.valueOf(rs.getString("Createdby"));
                edit_creatdate=String.valueOf(rs.getString("Createddate"));
            }
            Decrypt(edit_password,key);
            Log.e("D====gg44444444ta", ""+decodeedpass);
            Log.e("ffryutfffffff",""+isactive);

        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        username=dialogView.findViewById(R.id.username);
        password=dialogView.findViewById(R.id.password);
        email=dialogView.findViewById(R.id.email);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        spinneremp=dialogView.findViewById(R.id.spinneremp);
        spinnerusergroup=dialogView.findViewById(R.id.spinnerusergroup);
        spinnerusertype=dialogView.findViewById(R.id.spinnerusertype);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        creatby=""+edit_creatby;
        creatdate=""+edit_creatdate;


        String userplant = "select * from MAs_UserPlantDetail where UserId='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//          stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(userplant);
            rs = stmt.executeQuery();

            while (rs.next())
            {
               userid=String.valueOf(rs.getString("UserID"));
               usernamee=String.valueOf(rs.getString("PlantID"));
                userplantid.add(usernamee);

            }


        }catch (SQLException e) {
            e.printStackTrace();
        }






        final String plantqurey = "select PlantID,Plantname from Mas_Plant";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(plantqurey);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            ArrayList<String> data1 = new ArrayList<String>();

            while (rs.next())
            {
                String Bussnessname;
                String id ;
                id = rs.getString("PlantID");
                Bussnessname = rs.getString("Plantname");
                data.add(Bussnessname);
                data1.add(id);

            }



            spinnerselctplantid = new int [data1.size()];
            spinnerselctedplantid = new String [data1.size()];
            for (int i=0;i<data1.size();i++){
                for (int j=0;j<userplantid.size();j++) {

                    if (userplantid.get(j).equals(""+data1.get(i))) {
                        int position = data1.indexOf(data1.get(i))+1;
                        spinnerselctedplantid[j]=""+data1.get(i);
                        spinnerselctplantid[j]=position;
//                       String positionid = String.valueOf(position);
//                        positionidconcat1 = positionidconcat1.concat("" + positionid+",");
                    }

                }
            }

Log.e("dfdfdfdfd323232",""+positionidconcat1);
            data.add(0,"select one");
            String[] array1 = data.toArray(new String[0]);
            MultiSelectionSpinner multiSelectionSpinner =dialogView.findViewById(R.id.plant);
            multiSelectionSpinner.setItems(array1);
           multiSelectionSpinner.setSelection(spinnerselctplantid);


            multiSelectionSpinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
                @Override
                public void selectedIndices(List<Integer> indices) {

                    for (int i=0;i<indices.size();i++){
                        Log.e("fffff------333333334",""+indices.get(i));
                        planstring=""+indices.get(i);
//                        Toast.makeText(User.this, ""+strings.get(i), Toast.LENGTH_LONG).show();
                    }
                }


                @Override
                public void selectedStrings(List<String> strings) {


                    for (int i=0;i<strings.size();i++){

                        if (strings.get(i) == "select one") {

                            //Do nothing.
                        } else {

                            String query = "select PlantID,Plantname from Mas_Plant where Plantname='" +strings.get(i)+ "'";
                            try {
                                connect = connectionClass.CONN();
                                stmt = connect.prepareStatement(query);
                                rs = stmt.executeQuery();


                                while (rs.next()) {
                                    plantid = rs.getString("PlantID");
                                    plantarr.add(plantid);
                                }


                            } catch (SQLException e) {

                                e.printStackTrace();

                            }


                            Toast.makeText(User.this, "" + plantarr, Toast.LENGTH_SHORT)

                                    .show();

                        }
                    }

                }
            });

        } catch (SQLException e) {

            e.printStackTrace();

        }



        String tempuser = "select * from Mas_UserTempDetail where UserId='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//          stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(tempuser);
            rs = stmt.executeQuery();

            while (rs.next())
            {
                userid=String.valueOf(rs.getString("UserID"));
                usernamee=String.valueOf(rs.getString("TempId"));
                tempuserid.add(usernamee);

            }
Log.e("33333333333333333",""+tempuserid.size());

        }catch (SQLException e) {
            e.printStackTrace();
        }




        String templatequrey = "select TempID,Templatename from Mas_TempPermission";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(templatequrey);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            ArrayList<String> data1 = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("TempID");
                String Bussnessname = rs.getString("Templatename");
                data.add(Bussnessname);
                data1.add(id);

            }

            spinnerselcttempid = new int [data1.size()];
            spinnerselctedtempid = new String [data1.size()];
            for (int i=0;i<data1.size();i++){
                for (int j=0;j<tempuserid.size();j++) {

                    if (tempuserid.get(j).equals(""+data1.get(i))) {
                        int position = data1.indexOf(data1.get(i))+1;
                        spinnerselctedtempid[j]=""+data1.get(i);
                        spinnerselcttempid[j]=position;
                  }

                }
            }

            data.add(0,"select one");
            String[] array2 = data.toArray(new String[0]);
            MultiSelectionSpinner multiSelectionSpinner1 =dialogView.findViewById(R.id.template);
            multiSelectionSpinner1.setItems(array2);
          multiSelectionSpinner1.setSelection(spinnerselcttempid);




            multiSelectionSpinner1.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
                @Override
                public void selectedIndices(List<Integer> indices) {

                    for (int i=0;i<indices.size();i++){

                        Log.e("fffff------333333334",""+indices.get(i));
                        tempstring=""+indices.get(i);
//                        Toast.makeText(User.this, ""+strings.get(i), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void selectedStrings(List<String> strings1) {



                    for (int i=0;i<strings1.size();i++){

                        if (strings1.get(i) == "select one") {

                            //Do nothing.
                        } else {

                            String query = "select TempID,Templatename from Mas_TempPermission where Templatename='" + strings1.get(i) + "'";
                            try {
                                connect = connectionClass.CONN();
                                stmt = connect.prepareStatement(query);
                                rs = stmt.executeQuery();


                                while (rs.next()) {
                                    tempid = rs.getString("TempID");
                                    temparr.add(tempid);
                                }

                                Log.e("fefe====gfegetgtytry",""+temparr.size());
                            } catch (SQLException e) {

                                e.printStackTrace();

                            }


                            Toast.makeText(User.this, "" + tempid, Toast.LENGTH_SHORT)

                                    .show();

                        }


                    }

                    for (int i=0;i<temparr.size();i++)
                    {
                        Log.e("fefe====rger324235425",temparr.get(i));
                    }

//      Log.e("feferger324235425",""+temparr.size());

                }
            });

        } catch (SQLException e) {

            e.printStackTrace();

        }




        spinneremp.setEnabled(true);




        String query1 = "select Empno,Name from Mas_Employee where Name !='"+empname+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("Empno");
                String Bussnessname = rs.getString("Name");
                data.add(Bussnessname);

            }
            data.add(0,""+empname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinneremp.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        spinneremp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinneremp.getSelectedItem().toString();


                if (spinneremp.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select Empno,Name from Mas_Employee where Name='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            empid = rs.getString("Empno");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(User.this, "" + empid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String query2 = "select UserGroupID,Groupname from Mas_Usergroup where Groupname != '"+edit_groupname+"' ";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query2);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("UserGroupID");
                String Bussnessname = rs.getString("Groupname");
                data.add(Bussnessname);

            }
            data.add(0,""+edit_groupname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerusergroup.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }



        spinnerusergroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = spinnerusergroup.getSelectedItem().toString();


                if (spinnerusergroup.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select UserGroupID,Groupname from Mas_Usergroup where Groupname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            usergroupid = rs.getString("UserGroupID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(User.this, "" + usergroupid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        smaterialtype[0]=""+edit_usertype;
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, smaterialtype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerusertype.setAdapter(adapter2);


        spinnerusertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (spinnerusertype.getSelectedItem() == "Select One") {

                    //Do nothing.
                } else {

                    Toast.makeText(getApplicationContext(), ""+spinnerusertype.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    usertypeid=""+spinnerusertype.getSelectedItem();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        username.setText(""+edit_usetname);
        password.setText(""+decodeedpass);
        email.setText(""+edit_mailid);
        editTextPhone.setText(""+edit_mobileno);




        if (isactive.equals("1")){
            checkBoxAgree.setChecked(true);
        }else{
            checkBoxAgree.setChecked(false);
        }






        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


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
                ed_empid=empid;
                ed_usergroupid=usergroupid;
                ed_usergrouptype=usertypeid;
                ed_username = username.getText().toString();
                ed_pass = password.getText().toString();
                ed_email = email.getText().toString();
                ed_mobile =editTextPhone.getText().toString();


                if(username.getText().toString().equals(""))

                {
                    username.requestFocus();
                    username.setError("FIELD CANNOT BE EMPTY");
                }
                if(password.getText().toString().equals(""))
                {
                    password.requestFocus();
                    password.setError("FIELD CANNOT BE EMPTY");
                }
                if(editTextPhone.getText().toString().equals(""))
                {
                    editTextPhone.requestFocus();
                    editTextPhone.setError("FIELD CANNOT BE EMPTY");
                }
                if(ed_usergrouptype.equals("Select One"))
                {
                    Toast.makeText(User.this, "Choose the User group", Toast.LENGTH_SHORT).show();
                }


                if(planstring == null || tempstring == null || planstring == "" || tempstring == "")
                {
                    Toast.makeText(User.this, "Choose the Plant", Toast.LENGTH_SHORT).show();
                }
//                String query = "select count(*) as row,UserID from Mas_User where Username='"+ed_username+"' or Empno='"+ed_empid+"' or (Mobileno='"+ed_mobile+"' and Isactive=1) and UserID <> '"+ID+"' group by UserID";
                String query = "select count(*) as row,UserID,Isactive,Empno from Mas_User where (Username='"+ed_username+"' or (Empno='"+ed_empid+"' and Empno is not null) or (Mobileno='"+ed_mobile+"' and Isactive=1)) and UserID not in ('"+ID+"') group by UserID,Isactive,Empno";
                try {

                    connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        bussinessid=String.valueOf(rs.getString("row"));
                        empnoooo=String.valueOf(rs.getString("Empno"));
                        BussID=String.valueOf(rs.getString("UserID"));
                        if ((bussinessid.equals("0")) ){
                            Update update = new Update();
                            update.execute("");
                            alertDialog.dismiss();

                        }/*else if (empnoooo.equals("null")){


                            String empquery = "select count(*) as row,UserID,Isactive,Empno from Mas_User where (Username='"+ed_username+"' or (Mobileno='"+ed_mobile+"' and Isactive=1)) and UserID not in ('"+ID+"') group by UserID,Isactive,Empno";
                            try {

                                connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                                stmt = connect.prepareStatement(query);
                                rs = stmt.executeQuery();

                                if (rs.next()) {
                                    bussinessid=String.valueOf(rs.getString("row"));
                                    empnoooo=String.valueOf(rs.getString("Empno"));
                                    BussID=String.valueOf(rs.getString("UserID"));
                                    if ((bussinessid.equals("0")) ){
                                        Update update = new Update();
                                        update.execute("");
                                        alertDialog.dismiss();

                                    }else{
                                        Toast.makeText(User.this, "User already exit", Toast.LENGTH_SHORT).show();
                                    }

                                } }catch (SQLException e) {
                                e.printStackTrace();
                            }


                        }*/else{
                            Toast.makeText(User.this, "User already exit", Toast.LENGTH_SHORT).show();
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

    @Override
    public void selectedIndices(List<Integer> indices) {
//        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectedStrings(List<String> strings) {
//        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }


    public class UploadImage extends AsyncTask<String,String,String>
    {

        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPostExecute(String r)
        {

            String query = "select * from Mas_User where Empno='"+ed_empid+"'";
            try {

                connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                stmt = connect.prepareStatement(query);
                rs = stmt.executeQuery();

                if (rs.next()) {
//                    bussinessid=String.valueOf(rs.getString("row"));
                    BussID=String.valueOf(rs.getString("UserID"));





                    for (int i=0;i<plantarr.size();i++){
                        Log.e("feferger324235425",plantarr.get(i));

                        String query3 = "Insert into MAs_UserPlantDetail(UserId,PlantID,Createdby,Createddate) values ('" +BussID+ "','" + plantarr.get(i) + "','" + useridid + "','" + currentdate + "')";
                        try {
                            connect = connectionClass.CONN();
                            stmt = connect.prepareStatement(query3);
                            rs = stmt.executeQuery();

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }


                    }

                    List<Map<String, String>> tempmenu = null;
                    tempmenu = new ArrayList<Map<String, String>>();
                    for (int i=0;i<temparr.size();i++){
                        Log.e("feferger324235425",temparr.get(i));

                        String query3 = "Insert into Mas_UserTempDetail(UserId,TempId,Createdby,Createddate) values ('" +BussID+ "','" + temparr.get(i) + "','" + useridid + "','" + currentdate + "')";
                        try {
                            connect = connectionClass.CONN();
                            stmt = connect.prepareStatement(query3);
                            rs = stmt.executeQuery();

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }


                        String tempmenuquery = "select * from Mas_TempPermissionDetail where TempId='"+ temparr.get(i)+"'";
                        try {
                            connect = connectionClass.CONN();
                            stmt = connect.prepareStatement(tempmenuquery);
                            rs = stmt.executeQuery();

                            while (rs.next()){

                                String tempmenuinsert = "Insert into Mas_UserTempMenu(UserID,TempId,ModuleId,MenuID,Menuright,FA,FS,FD,FV,FX) values ('" +BussID+ "','" + rs.getString("TempId") + "','" + rs.getString("ModuleId") + "','" + rs.getString("MenuID")  + "','" + rs.getString("Menuright")  + "','" + rs.getString("FA")+ "','" + rs.getString("FS")+ "','" + rs.getString("FD")+ "','" + rs.getString("FV")+ "','" + rs.getString("FX")+ "')";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(tempmenuinsert);
                                    rs = stmt.executeQuery();

                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }


                            }


                        } catch (SQLException e) {

                            e.printStackTrace();

                        }




                    }


                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }



            List<Map<String,String>> MyData = null;
            Usermodel mydata =new Usermodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Empno","Username","Mailid","Mobileno","Usertype","Groupname","UserID"};
            temparr=null;
            plantarr=null;
            int[] viewswhere = {R.id.empnoo , R.id.username,R.id.email,R.id.phone,R.id.usertypeee,R.id.usergrouppp,R.id.idview};

            ADAhere = new SimpleAdapter(User.this, MyData,R.layout.useraddlist, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("UserID");
                            editCustomDialog();
                            Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("UserID");
                      deleteCustomDialog();
                            Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });


                    return view;
                }
            };

            userlist.setAdapter(ADAhere);
            buildDialog("Inserted Sucessfully");
        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";





            if (ed_username.trim().equals("") && ed_pass.trim().equals("")) {

                z = "Please enter Username and password";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        Encrypt(ed_pass,key);
                        planstring="";
                        tempstring="";

                        Log.e("gsyhryuryu213242424rt",""+encodeedpass);
                        String query = "Insert into Mas_User(Empno,Username,Userpwd,Usergrpid,Mobileno,Mailid,Usertype,Createdby,Createddate) values ('" + ed_empid + "','" + ed_username + "','" + encodeedpass + "','" + ed_usergroupid + "','" + ed_mobile + "','" + ed_email + "','" + ed_usergrouptype + "','" + useridid + "','" + currentdate + "')";
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

            String deleteplantuse = "DELETE FROM MAs_UserPlantDetail WHERE UserId='"+ID+"'";
            try {
                connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                stmt = connect.prepareStatement(deleteplantuse);
                rs = stmt.executeQuery();

            }catch (SQLException e) {
                e.printStackTrace();
            }
  String deletetempuse = "DELETE FROM Mas_UserTempDetail WHERE UserId='"+ID+"'";
            try {
                connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                stmt = connect.prepareStatement(deletetempuse);
                rs = stmt.executeQuery();

            }catch (SQLException e) {
                e.printStackTrace();
            }
  String deletetempmenuuse = "DELETE FROM Mas_UserTempMenu WHERE UserId='"+ID+"'";
            try {
                connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                stmt = connect.prepareStatement(deletetempmenuuse);
                rs = stmt.executeQuery();

            }catch (SQLException e) {
                e.printStackTrace();
            }




            // End After successful insertion of image



            String query = "select * from Mas_User where UserID='"+ID+"'";
            try {

                connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                stmt = connect.prepareStatement(query);
                rs = stmt.executeQuery();

                if (rs.next()) {
//                    bussinessid=String.valueOf(rs.getString("row"));
                    BussID=String.valueOf(rs.getString("UserID"));


                  if (plantarr.size() != 0){
                      for (int i=0;i<plantarr.size();i++){
                          Log.e("feferger324235425",plantarr.get(i));

                          String query3 = "Insert into MAs_UserPlantDetail(UserId,PlantID,Createdby,Createddate) values ('" +BussID+ "','" + plantarr.get(i) + "','" + useridid + "','" + currentdate + "')";
                          try {
                              connect = connectionClass.CONN();
                              stmt = connect.prepareStatement(query3);
                              rs = stmt.executeQuery();

                          } catch (SQLException e) {

                              e.printStackTrace();

                          }
                      }

                  }
                  else
                      {
                          for (int i=0;i<spinnerselctedplantid.length;i++){


                              String query3 = "Insert into MAs_UserPlantDetail(UserId,PlantID,Createdby,Createddate) values ('" +BussID+ "','" + spinnerselctedplantid[i] + "','" + useridid + "','" + currentdate + "')";
                              try {
                                  connect = connectionClass.CONN();
                                  stmt = connect.prepareStatement(query3);
                                  rs = stmt.executeQuery();

                              } catch (SQLException e) {

                                  e.printStackTrace();

                              }
                          }

                      }

                if (temparr.size()!=0){
                    for (int i=0;i<temparr.size();i++){
                        Log.e("feferger324235425",temparr.get(i));

                        String query3 = "Insert into Mas_UserTempDetail(UserId,TempId,Createdby,Createddate) values ('" +BussID+ "','" + temparr.get(i) + "','" + useridid + "','" + currentdate + "')";
                        try {
                            connect = connectionClass.CONN();
                            stmt = connect.prepareStatement(query3);
                            rs = stmt.executeQuery();

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }


                        String tempmenuquery = "select * from Mas_TempPermissionDetail where TempId='"+ temparr.get(i)+"'";
                        try {
                            connect = connectionClass.CONN();
                            stmt = connect.prepareStatement(tempmenuquery);
                            rs = stmt.executeQuery();

                            while (rs.next()){

                                String tempmenuinsert = "Insert into Mas_UserTempMenu(UserID,TempId,ModuleId,MenuID,Menuright,FA,FS,FD,FV,FX) values ('" +BussID+ "','" + rs.getString("TempId") + "','" + rs.getString("ModuleId") + "','" + rs.getString("MenuID")  + "','" + rs.getString("Menuright")  + "','" + rs.getString("FA")+ "','" + rs.getString("FS")+ "','" + rs.getString("FD")+ "','" + rs.getString("FV")+ "','" + rs.getString("FX")+ "')";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(tempmenuinsert);
                                    rs = stmt.executeQuery();

                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }


                            }


                        } catch (SQLException e) {

                            e.printStackTrace();

                        }



                    }
                }else{
                    for (int i=0;i<spinnerselctedtempid.length;i++){
//                        Log.e("feferger324235425",temparr.get(i));

                        String query3 = "Insert into Mas_UserTempDetail(UserId,TempId,Createdby,Createddate) values ('" +BussID+ "','" + spinnerselctedtempid[i] + "','" + useridid + "','" + currentdate + "')";
                        try {
                            connect = connectionClass.CONN();
                            stmt = connect.prepareStatement(query3);
                            rs = stmt.executeQuery();

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }



                        String tempmenuquery = "select * from Mas_TempPermissionDetail where TempId='"+ spinnerselctedtempid[i] +"'";
                        try {
                            connect = connectionClass.CONN();
                            stmt = connect.prepareStatement(tempmenuquery);
                            rs = stmt.executeQuery();

                            while (rs.next()){

                                String tempmenuinsert = "Insert into Mas_UserTempMenu(UserID,TempId,ModuleId,MenuID,Menuright,FA,FS,FD,FV,FX) values ('" +BussID+ "','" + rs.getString("TempId") + "','" + rs.getString("ModuleId") + "','" + rs.getString("MenuID")  + "','" + rs.getString("Menuright")  + "','" + rs.getString("FA")+ "','" + rs.getString("FS")+ "','" + rs.getString("FD")+ "','" + rs.getString("FV")+ "','" + rs.getString("FX")+ "')";
                                try {
                                    connect = connectionClass.CONN();
                                    stmt = connect.prepareStatement(tempmenuinsert);
                                    rs = stmt.executeQuery();

                                } catch (SQLException e) {

                                    e.printStackTrace();

                                }


                            }


                        } catch (SQLException e) {

                            e.printStackTrace();

                        }


                    }
                }



                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            List<Map<String,String>> MyData = null;
            Usermodel mydata =new Usermodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Empno","Username","Mailid","Mobileno","Usertype","Groupname","UserID"};

            int[] viewswhere = {R.id.empnoo , R.id.username,R.id.email,R.id.phone,R.id.usertypeee,R.id.usergrouppp,R.id.idview};

            ADAhere = new SimpleAdapter(User.this, MyData,R.layout.useraddlist, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("UserID");
                            editCustomDialog();
                            Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("UserID");
                         deleteCustomDialog();
                            Toast.makeText(User.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };

            userlist.setAdapter(ADAhere);


            buildDialog( "Updated Sucessfully");

        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ed_username.trim().equals("") && ed_pass.trim().equals("")) {

                z = "Please enter Username and password";

            } else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        Encrypt(ed_pass,key);
//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_User SET Empno='"+ed_empid+"',Username='"+ed_username+"', Userpwd='"+encodeedpass+"',Usergrpid='"+ed_usergroupid+"',Mobileno='"+ed_mobile+"',Mailid='"+ed_email+"',Usertype='"+ed_usergrouptype+"',Createdby='"+creatby+"',Createddate='"+creatdate+"',Updatedby='"+useridid+"',Updateddate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' where UserID='"+ID+"';";
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

    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
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
