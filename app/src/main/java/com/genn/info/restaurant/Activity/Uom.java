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
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Bussnessaddmodel;
import com.genn.info.restaurant.Model.Plantmodel;
import com.genn.info.restaurant.Model.Uommodel;
import com.genn.info.restaurant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobsandgeeks.saripaar.ValidationError;
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

public class Uom extends AppCompatActivity  implements Validator.ValidationListener {
    ConnectionClass connectionClass;
    Connection connect;



    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    DBHelper dbHelper;
    String bussid;

    private Validator validator;
    String logid,name,pass,userid,currentdate;


    @Length(min = 1, max = 50)
    EditText uom;

    @Length(min = 1, max = 50)
    EditText uomname;

    String uomnamestr,uomstr;

    ResultSet rs;
    PreparedStatement stmt;


    ListView uomlist;
    SimpleAdapter ADAhere;


    CheckBox checkBoxAgree;

    String msg;
    String ID,status,ed_uom,ed_uomname,edit_plant= null,creatby,creatdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uom);
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



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Uom.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Uom.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Uom.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Uom.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Uom.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Uom.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Uom.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Uom.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Uom.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Uom.this, Varientmaster.class));
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

        uomlist=(ListView)findViewById(R.id.uomlist);




        List<Map<String,String>> MyData = null;
        Uommodel mydata =new Uommodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Uomname","ID"};

        int[] viewswhere = {R.id.uomname ,R.id.idview};

        ADAhere = new SimpleAdapter(Uom.this, MyData,R.layout.uomadd, fromwhere, viewswhere){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                status=(String)obj.get("Isactive");

                View view=super.getView(position, convertView, parent);



                ImageView imageView=view.findViewById(R.id.editicon);
                ImageView deleteicon=view.findViewById(R.id.deleteicon);
                CheckBox uomstatus=view.findViewById(R.id.uomstatus);

                if (status.equals("1")){
                    uomstatus.setChecked(true);
                }else{
                    uomstatus.setChecked(false);
                }



                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("ID");
                          editCustomDialog();
                        Toast.makeText(Uom.this, ID, Toast.LENGTH_SHORT).show();
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

        uomlist.setAdapter(ADAhere);




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
        View dialogView = LayoutInflater.from(this).inflate(R.layout.uomaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        uom=dialogView.findViewById(R.id.et_uomname);
        uomname=dialogView.findViewById(R.id.uomname);

        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);

        checkBoxAgree.setVisibility(View.GONE);

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


                validator.validate();
                ed_uom = uom.getText().toString();
                ed_uomname = uomname.getText().toString();

                if (uom.getText().toString().equals("")) {
                    uom.requestFocus();
                    uom.setError("FIELD CANNOT BE EMPTY");
                }else if (uomname.getText().toString().equals("")) {
                    uomname.requestFocus();
                    uomname.setError("FIELD CANNOT BE EMPTY");
                } else {

                    validator.validate();
                    uomnamestr = uom.getText().toString();
                    uomstr = uomname.getText().toString();


                    String query = "select count(*) as row from Mas_Uom where Uomname='"+uomnamestr+"'";
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
                                Toast.makeText(Uom.this, "UserName Already exit", Toast.LENGTH_SHORT).show();
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

        String edit_uom = null,edit_plant= null,edit_creatby = null,edit_creatdate = null,isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.uomaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Uom where ID='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid=String.valueOf(rs.getString("ID"));
                edit_plant=String.valueOf(rs.getString("Uomname"));
                edit_uom=String.valueOf(rs.getString("Uom"));
                edit_creatby=String.valueOf(rs.getString("Createdby"));
                edit_creatdate=String.valueOf(rs.getString("Createddate"));

                isactive=rs.getString("Isactive");

            }

            Log.e("ffryutfffffff",""+isactive);

        }catch (SQLException e) {
            e.printStackTrace();
        }


        uom=dialogView.findViewById(R.id.et_uomname);
        uomname=dialogView.findViewById(R.id.uomname);

        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);



checkBoxAgree.setVisibility(View.VISIBLE);



        uom.setText(""+edit_plant);
        uomname.setText(""+edit_uom);
        creatby=""+edit_creatby;
        creatdate=""+edit_creatdate;
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

       /* builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "DELETE FROM Mas_Uom WHERE UomID='"+ID+"'";
                try {
                    connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();

                }catch (SQLException e) {
                    e.printStackTrace();
                }
                Intent i=new Intent(Uom.this,Uom.class);
                startActivity(i);
                finish();
                buildDialog("Deleted Sucessfully");
            }
        });*/


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


                validator.validate();
                ed_uom = uom.getText().toString();
                ed_uomname = uomname.getText().toString();

                if (uom.getText().toString().equals("")) {
                    uom.requestFocus();
                    uom.setError("FIELD CANNOT BE EMPTY");
                }else if (uomname.getText().toString().equals("")) {
                    uomname.requestFocus();
                    uomname.setError("FIELD CANNOT BE EMPTY");
                } else {
                    String query = "select count(*) as row,ID from Mas_Uom where Uomname='" + ed_uom + "'group by  ID";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            String BussID = String.valueOf(rs.getString("ID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(Uom.this, "User already exit", Toast.LENGTH_SHORT).show();
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
                        String query = "DELETE FROM Mas_Uom WHERE ID='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }


                        buildDialog("Deleted Sucessfully");


                        List<Map<String,String>> MyData = null;
                        Uommodel mydata =new Uommodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Uomname","ID"};

                        int[] viewswhere = {R.id.uomname ,R.id.idview};

                        ADAhere = new SimpleAdapter(Uom.this, MyData,R.layout.uomadd, fromwhere, viewswhere){
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                                status=(String)obj.get("Isactive");

                                View view=super.getView(position, convertView, parent);



                                ImageView imageView=view.findViewById(R.id.editicon);
                                ImageView deleteicon=view.findViewById(R.id.deleteicon);
                                CheckBox uomstatus=view.findViewById(R.id.uomstatus);

                                if (status.equals("1")){
                                    uomstatus.setChecked(true);
                                }else{
                                    uomstatus.setChecked(false);
                                }



                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("ID");
                                        editCustomDialog();
                                        Toast.makeText(Uom.this, ID, Toast.LENGTH_SHORT).show();
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

                        uomlist.setAdapter(ADAhere);

                        Toast.makeText(Uom.this, ID, Toast.LENGTH_SHORT).show();

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
            // After successful insertion of image

//           Toast.makeText(Bussinessmaster.this , ""+z, Toast.LENGTH_LONG).show();

            Log.e("ddd1111ddddddd",""+isSuccess);
            // End After successful insertion of image


            List<Map<String,String>> MyData = null;
            Uommodel mydata =new Uommodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Uomname","ID"};

            int[] viewswhere = {R.id.uomname ,R.id.idview};

            ADAhere = new SimpleAdapter(Uom.this, MyData,R.layout.uomadd, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("ID");
                    status=(String)obj.get("Isactive");

                    View view=super.getView(position, convertView, parent);



                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);
                    CheckBox uomstatus=view.findViewById(R.id.uomstatus);

                    if (status.equals("1")){
                        uomstatus.setChecked(true);
                    }else{
                        uomstatus.setChecked(false);
                    }



                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(Uom.this, ID, Toast.LENGTH_SHORT).show();
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

            uomlist.setAdapter(ADAhere);

            buildDialog("Inserted Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (uomnamestr.trim().equals("") ) {

                z = "Please enter Uomname ";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "Insert into Mas_Uom(Uom,Uomname,Createdby,Createddate) values ('" + uomstr + "','" + uomnamestr + "','" + userid + "','" + currentdate + "')";
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
            Uommodel mydata =new Uommodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Uomname","ID"};

            int[] viewswhere = {R.id.uomname ,R.id.idview};

            ADAhere = new SimpleAdapter(Uom.this, MyData,R.layout.uomadd, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status=(String)obj.get("Isactive");

                    View view=super.getView(position, convertView, parent);



                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);
                    CheckBox uomstatus=view.findViewById(R.id.uomstatus);

                    if (status.equals("1")){
                        uomstatus.setChecked(true);
                    }else{
                        uomstatus.setChecked(false);
                    }



                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(Uom.this, ID, Toast.LENGTH_SHORT).show();
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

            uomlist.setAdapter(ADAhere);


            buildDialog("Updated Sucessfully");


        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ed_uom.trim().equals("")) {

                z = "Please enter Bussnessname";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {



//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Uom SET Uom='"+ed_uomname+"',Uomname='"+ed_uom+"',Createdby='"+creatby+"',Createddate='"+creatdate+"',Updatedby='"+userid+"',Updateddate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE ID='"+ID+"';";
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

    private void buildDialog( String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Uom.this);
        builder.setTitle("Uom Master");
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
