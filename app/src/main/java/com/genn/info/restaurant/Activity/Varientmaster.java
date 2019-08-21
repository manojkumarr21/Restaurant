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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Uommodel;
import com.genn.info.restaurant.Model.Usergroupmodel;
import com.genn.info.restaurant.Model.Varientmodel;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Varientmaster extends AppCompatActivity implements Validator.ValidationListener {

    ConnectionClass connectionClass;
    Connection connect;


    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    DBHelper dbHelper;
    String bussid;

    private Validator validator;
    String logid, name, pass,userid, currentdate;


    @Length(min = 1, max = 50)
    EditText varientname;

    @Length(min = 1, max = 50)
    EditText sortid;

    String varinamestr, varisort;

    ResultSet rs;
    PreparedStatement stmt;


    ListView varientlist;
    SimpleAdapter ADAhere;


    CheckBox checkBoxAgree;

    String msg;
    String ID, status, ed_uom, edit_plant = null, creatby, creatdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varientmaster);
        connectionClass = new ConnectionClass();

        validator = new Validator(this);
        validator.setValidationListener(this);


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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                //creating fragment object
                Fragment fragment = null;

                if (id == R.id.nav_profile) {

                    startActivity(new Intent(Varientmaster.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Varientmaster.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Varientmaster.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Varientmaster.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_policy) {


                    startActivity(new Intent(Varientmaster.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(Varientmaster.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Varientmaster.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Varientmaster.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Varientmaster.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Varientmaster.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Varientmaster.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Varientmaster.this, Varientmaster.class));
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

        varientlist = (ListView) findViewById(R.id.varientlist);


        List<Map<String, String>> MyData = null;
        Varientmodel mydata = new Varientmodel();
        MyData = mydata.doInBackground();
        String[] fromwhere = {"Variantname", "Sortorder", "VariantID"};

        int[] viewswhere = {R.id.variname, R.id.sort, R.id.idview};

        ADAhere = new SimpleAdapter(Varientmaster.this, MyData, R.layout.varientadd, fromwhere, viewswhere) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                status = (String) obj.get("Isactive");

                View view = super.getView(position, convertView, parent);


                ImageView imageView = view.findViewById(R.id.editicon);
                ImageView deleteicon=view.findViewById(R.id.deleteicon);
                CheckBox uomstatus = view.findViewById(R.id.varistatus);

                if (status.equals("1")) {
                    uomstatus.setChecked(true);
                } else {
                    uomstatus.setChecked(false);
                }


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                        ID = (String) obj.get("VariantID");
                       editCustomDialog();
                        Toast.makeText(Varientmaster.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });


                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("VariantID");
                        deleteCustomDialog();
                    }
                });
                return view;
            }
        };

        varientlist.setAdapter(ADAhere);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        View dialogView = LayoutInflater.from(this).inflate(R.layout.varientaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        varientname = dialogView.findViewById(R.id.et_varientname);
        sortid = dialogView.findViewById(R.id.et_varientorder);

        checkBoxAgree = dialogView.findViewById(R.id.checkBoxAgree);

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


                varinamestr = varientname.getText().toString();
                varisort = sortid.getText().toString();


                if (varientname.getText().toString().equals("")) {
                    varientname.requestFocus();
                    varientname.setError("FIELD CANNOT BE EMPTY");
                }else if (sortid.getText().toString().equals("")){
                    sortid.requestFocus();
                    sortid.setError("FIELD CANNOT BE EMPTY");
                }
                else {

                    String query = "select count(*) as row from Mas_Variant where Sortorder='" + varisort + "'";
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
                                Toast.makeText(Varientmaster.this, "Sorting order  Already exit", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            UploadImage uploadImage = new UploadImage();
                            uploadImage.execute("");
                            alertDialog.dismiss();
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


    }

    private void editCustomDialog() {

        String edit_uom = null, edit_plant = null, edit_sort = null, edit_creatby = null, edit_creatdate = null, isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.varientaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Variant where VariantID='" + ID + "'";
        try {

            connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid = String.valueOf(rs.getString("VariantID"));
                edit_plant = String.valueOf(rs.getString("Variantname"));
                edit_sort = String.valueOf(rs.getString("Sortorder"));
                edit_creatby = String.valueOf(rs.getString("Createdby"));
                edit_creatdate = String.valueOf(rs.getString("Createddate"));

                isactive = rs.getString("Isactive");

            }

            Log.e("ffryutfffffff", "" + isactive);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        varientname = dialogView.findViewById(R.id.et_varientname);
        sortid = dialogView.findViewById(R.id.et_varientorder);

        checkBoxAgree = dialogView.findViewById(R.id.checkBoxAgree);


        checkBoxAgree.setVisibility(View.VISIBLE);


        varientname.setText("" + edit_plant);
        sortid.setText("" + edit_sort);

        creatby = "" + edit_creatby;
        creatdate = "" + edit_creatdate;
        if (isactive.equals("1")) {
            checkBoxAgree.setChecked(true);
        } else {
            checkBoxAgree.setChecked(false);
        }


        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

/*
        builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "DELETE FROM Mas_Variant WHERE VariantID='" + ID + "'";
                try {
                    connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Varientmaster.this, Varientmaster.class);
                startActivity(i);
                finish();
                buildDialog("Deleted Sucessfully");
            }
        });
*/


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



                varinamestr = varientname.getText().toString();
                varisort = sortid.getText().toString();


                if (varientname.getText().toString().equals("")) {
                    varientname.requestFocus();
                    varientname.setError("FIELD CANNOT BE EMPTY");
                }else if (sortid.getText().toString().equals("")){
                    sortid.requestFocus();
                    sortid.setError("FIELD CANNOT BE EMPTY");
                }
                else {
                    String query = "select count(*) as row,VariantID from Mas_Variant where Variantname='" + varinamestr + "'group by  VariantID";
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
                            String BussID = String.valueOf(rs.getString("VariantID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(Varientmaster.this, "User already exit", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("DELETE VARIENT");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String query = "DELETE FROM Mas_Variant WHERE VariantID='" + ID + "'";
                        try {
                            connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }



                        List<Map<String, String>> MyData = null;
                        Varientmodel mydata = new Varientmodel();
                        MyData = mydata.doInBackground();
                        String[] fromwhere = {"Variantname", "Sortorder", "VariantID"};

                        int[] viewswhere = {R.id.variname, R.id.sort, R.id.idview};

                        ADAhere = new SimpleAdapter(Varientmaster.this, MyData, R.layout.varientadd, fromwhere, viewswhere) {
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                                status = (String) obj.get("Isactive");

                                View view = super.getView(position, convertView, parent);


                                ImageView imageView = view.findViewById(R.id.editicon);
                                ImageView deleteicon=view.findViewById(R.id.deleteicon);
                                CheckBox uomstatus = view.findViewById(R.id.varistatus);

                                if (status.equals("1")) {
                                    uomstatus.setChecked(true);
                                } else {
                                    uomstatus.setChecked(false);
                                }


                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                                        ID = (String) obj.get("VariantID");
                                        editCustomDialog();
                                        Toast.makeText(Varientmaster.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });


                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("VariantID");
                                        deleteCustomDialog();
                                    }
                                });
                                return view;
                            }
                        };

                        varientlist.setAdapter(ADAhere);

                        buildDialog("Deleted Sucessfully");
                        Toast.makeText(Varientmaster.this, ID, Toast.LENGTH_SHORT).show();

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


    public class UploadImage extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPostExecute(String r) {
            // After successful insertion of image

//           Toast.makeText(Bussinessmaster.this , ""+z, Toast.LENGTH_LONG).show();

            Log.e("ddd1111ddddddd", "" + isSuccess);
            // End After successful insertion of image



            List<Map<String, String>> MyData = null;
            Varientmodel mydata = new Varientmodel();
            MyData = mydata.doInBackground();
            String[] fromwhere = {"Variantname", "Sortorder", "VariantID"};

            int[] viewswhere = {R.id.variname, R.id.sort, R.id.idview};

            ADAhere = new SimpleAdapter(Varientmaster.this, MyData, R.layout.varientadd, fromwhere, viewswhere) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status = (String) obj.get("Isactive");

                    View view = super.getView(position, convertView, parent);


                    ImageView imageView = view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);
                    CheckBox uomstatus = view.findViewById(R.id.varistatus);

                    if (status.equals("1")) {
                        uomstatus.setChecked(true);
                    } else {
                        uomstatus.setChecked(false);
                    }


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("VariantID");
                            editCustomDialog();
                            Toast.makeText(Varientmaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });


                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("VariantID");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            varientlist.setAdapter(ADAhere);

            buildDialog("Inserted Sucessfully");

        }

        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (varinamestr.trim().equals("")) {

                z = "Please enter Uomname ";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "Insert into Mas_Variant(Variantname,Sortorder,Createdby,Createddate) values ('" + varinamestr + "','" + varisort + "','" + userid + "','" + currentdate + "')";
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

    public class Update extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPostExecute(String r) {
            // After successful insertion of image

//           Toast.makeText(Bussinessmaster.this , ""+z, Toast.LENGTH_LONG).show();

            Log.e("ddd1111ddddddd", "" + isSuccess);
            // End After successful insertion of image


            List<Map<String, String>> MyData = null;
            Varientmodel mydata = new Varientmodel();
            MyData = mydata.doInBackground();
            String[] fromwhere = {"Variantname", "Sortorder", "VariantID"};

            int[] viewswhere = {R.id.variname, R.id.sort, R.id.idview};

            ADAhere = new SimpleAdapter(Varientmaster.this, MyData, R.layout.varientadd, fromwhere, viewswhere) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status = (String) obj.get("Isactive");

                    View view = super.getView(position, convertView, parent);


                    ImageView imageView = view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);
                    CheckBox uomstatus = view.findViewById(R.id.varistatus);

                    if (status.equals("1")) {
                        uomstatus.setChecked(true);
                    } else {
                        uomstatus.setChecked(false);
                    }


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("VariantID");
                            editCustomDialog();
                            Toast.makeText(Varientmaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });


                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("VariantID");
                            deleteCustomDialog();
                        }
                    });
                    return view;
                }
            };

            varientlist.setAdapter(ADAhere);

            buildDialog( "Updated Sucessfully");
        }

        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (varinamestr.trim().equals("") || varisort.trim().equals("")) {

                z = "Please enter Variname and sortorder";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Variant SET Variantname='" + varinamestr + "',Sortorder='" + varisort + "',Createdby='" + creatby + "',Createddate='" + creatdate + "',Updatedby='" + userid + "',Updateddate='" + currentdate + "' ,Isactive='" + checkBoxAgree.isChecked() + "' WHERE VariantID='" + ID + "';";
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


    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Varientmaster.this);
        builder.setTitle("Varient Master");
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