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
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.TaxViewsearh;
import com.genn.info.restaurant.Model.Taxmodel;
import com.genn.info.restaurant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobsandgeeks.saripaar.Validator;

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

public class Tax extends AppCompatActivity {

    ConnectionClass connectionClass;
    Connection connect;
    ResultSet rs;
    PreparedStatement stmt;
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    DBHelper dbHelper;
    String bussid;

    private Validator validator;
    String logid, name, pass, currentdate;


    protected TextView selectColoursButton;

    protected CharSequence[] colours = { "ALL", "TAX1", "TAX2", "TAX3", "TAX4", "TAX5" };

    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    ListView list;

    EditText editsearch;
    String[] rank;
    String[] country;
    String[] quantity;
    String[] population;
    int[] listviewImage;

    ArrayList<TaxViewsearh> arraylist = new ArrayList<TaxViewsearh>();
    CheckBox checkBoxAgree;

    String[] Company = {"ALL","FAVORTE","JUICE","LUNCH","DINNER","ICE CREAM"};
    String[] variant = {"ALL","FAVORTE","JUICE","LUNCH","DINNER","ICE CREAM"};
    int[] tax = {10,20,30,40,50,60};

    EditText Taxname,percen,cgst,sgst;

    String taxname,taxpercen,userid;
    double cgstval,sgstval;
    SimpleAdapter ADAhere;

    ImageView editicon;
    TextView id;

    String msg;
    String ID, status, ed_uom, edit_plant = null, creatby, creatdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax);



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

                    startActivity(new Intent(Tax.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Tax.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Tax.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Tax.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_policy) {


                    startActivity(new Intent(Tax.this, Customer.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_earningDeatils) {


                    startActivity(new Intent(Tax.this, Ingredient.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Tax.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Tax.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Tax.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Tax.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Tax.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Tax.this, Varientmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.usergroup) {

                    startActivity(new Intent(Tax.this, Usergroup.class));
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



        // Locate the ListView in listview_main.xml
        list =findViewById(R.id.listview);


        editsearch=findViewById(R.id.search);

        List<Map<String, String>> MyData = null;
        Taxmodel mydata = new Taxmodel();
        MyData = mydata.doInBackground();



        String[] fromwhere = {"Taxname", "Taxper", "TaxID"};

        int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};

        ADAhere = new SimpleAdapter(getApplicationContext(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                status = (String) obj.get("Isactive");

                View view = super.getView(position, convertView, parent);


                ImageView imageView = view.findViewById(R.id.editicon);
                ImageView imageView1 = view.findViewById(R.id.deleteicon);
                CheckBox uomstatus = view.findViewById(R.id.quantity);
                uomstatus.setEnabled(false);

                if (status.equals("1")) {
                    uomstatus.setChecked(true);
                } else {
                    uomstatus.setChecked(false);
                }


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                        ID = (String) obj.get("TaxID");
                        editCustomDialog();
                        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                    }
                });
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                        ID = (String) obj.get("TaxID");
                        deleteCustomDialog();
                        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }
        };

        list.setAdapter(ADAhere);
    }



    public void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.taxaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        Taxname = dialogView.findViewById(R.id.et_itemname);
        percen = dialogView.findViewById(R.id.et_price);
        cgst = dialogView.findViewById(R.id.CGST);
        sgst = dialogView.findViewById(R.id.SGST);


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

        percen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int per= Integer.parseInt(percen.getText().toString());

                cgst.setText(""+per/2);
                sgst.setText(""+per/2);


            }
        });
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                taxname = Taxname.getText().toString();
                taxpercen = percen.getText().toString();
                cgstval = Double.parseDouble(cgst.getText().toString());
                sgstval = Double.parseDouble(sgst.getText().toString());



                if (Taxname.getText().toString().equals("")) {
                    Taxname.requestFocus();
                    Taxname.setError("FIELD CANNOT BE EMPTY");
                }else if (percen.getText().toString().equals("")){
                    percen.requestFocus();
                    percen.setError("FIELD CANNOT BE EMPTY");
                }else if(cgstval+sgstval != Double.parseDouble(taxpercen))
                {
                    buildDialog("Tax percentage not Currect");
                }
                else {

                    String query = "select count(*) as row from Mas_Tax where Taxname='" +Taxname+ "'";
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
                                Toast.makeText(getApplicationContext(), "Sorting order  Already exit", Toast.LENGTH_SHORT).show();
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

    public void editCustomDialog() {

        String edit_uom = null, edit_plant = null, edit_sort = null, edit_creatby = null, edit_creatdate = null, isactive = null;
        String edit_cgst = null,edit_sgst= null;
//        view=inflater.inflate(R.layout.fragment_tax, container, false);
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.taxaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Tax where TaxID='" + ID + "'";
        try {

            connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid = String.valueOf(rs.getString("TaxID"));

                edit_plant = String.valueOf(rs.getString("Taxname"));

                edit_sort = String.valueOf(rs.getString("Taxper"));

                edit_cgst = String.valueOf(rs.getString("CGST"));

                edit_sgst = String.valueOf(rs.getString("SGST"));

                edit_creatby = String.valueOf(rs.getString("Createdby"));

                edit_creatdate = String.valueOf(rs.getString("Createddate"));

                isactive = rs.getString("Isactive");

            }

            Log.e("ffryutfffffff", "" + isactive);

        } catch (SQLException e) {
            e.printStackTrace();
        }



        Taxname = dialogView.findViewById(R.id.et_itemname);
        percen = dialogView.findViewById(R.id.et_price);
        cgst = dialogView.findViewById(R.id.CGST);
        sgst = dialogView.findViewById(R.id.SGST);
        checkBoxAgree = dialogView.findViewById(R.id.checkBoxAgree);


        checkBoxAgree.setVisibility(View.VISIBLE);


        Taxname.setText("" + edit_plant);
        percen.setText("" + edit_sort);
        cgst.setText(""+edit_cgst);
        sgst.setText(""+edit_sgst);
        creatby = "" + edit_creatby;
        creatdate = "" + edit_creatdate;
        if (isactive.equals("1")) {
            checkBoxAgree.setChecked(true);
        } else {
            checkBoxAgree.setChecked(false);
        }


        percen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int per= Integer.parseInt(percen.getText().toString());
                cgst.setText(""+per/2);
                sgst.setText(""+per/2);
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
                String query = "DELETE FROM Mas_Tax WHERE TaxID='" + ID + "'";
                try {
                    connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
               *//* Intent i = new Intent(getActivity(), Tax.class);
                startActivity(i);*//*



                List<Map<String, String>> MyData = null;
                Taxmodel mydata = new Taxmodel();
                MyData = mydata.doInBackground();



                String[] fromwhere = {"Taxname", "Taxper", "TaxID"};

                int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};

                ADAhere = new SimpleAdapter(getApplicationContext(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {

                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                        status = (String) obj.get("Isactive");

                        View view = super.getView(position, convertView, parent);


                        ImageView imageView = view.findViewById(R.id.editicon);
                        CheckBox uomstatus = view.findViewById(R.id.quantity);
                        uomstatus.setEnabled(false);

                        if (status.equals("1")) {
                            uomstatus.setChecked(true);
                        } else {
                            uomstatus.setChecked(false);
                        }


                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                                ID = (String) obj.get("TaxID");
                                editCustomDialog();
                                Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                            }
                        });
                        return view;
                    }
                };

                list.setAdapter(ADAhere);

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



                taxname = Taxname.getText().toString();
                taxpercen = percen.getText().toString();

                Log.e("**********",""+cgst.getText().toString());

                cgstval = Double.parseDouble(cgst.getText().toString());
                sgstval = Double.parseDouble(sgst.getText().toString());



                if (Taxname.getText().toString().equals("")) {
                    Taxname.requestFocus();
                    Taxname.setError("FIELD CANNOT BE EMPTY");
                }else if (percen.getText().toString().equals("")){
                    percen.requestFocus();
                    percen.setError("FIELD CANNOT BE EMPTY");
                }else if(cgstval+sgstval != Double.parseDouble(taxpercen))
                {
                    buildDialog("Tax percentage not Currect");
                }
                else {
                    String query = "select count(*) as row,TaxID from Mas_Tax where Taxname='" + Taxname + "'group by  TaxID";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();

                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            String BussID = String.valueOf(rs.getString("TaxID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(getApplicationContext(), "User already exit", Toast.LENGTH_SHORT).show();
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
                        String query = "DELETE FROM Mas_Tax WHERE TaxID='"+ID+"'";
                        try {
                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();

                        }catch (SQLException e) {
                            e.printStackTrace();
                        }
//                      Intent i=new Intent(Usergroup.this,Usergroup.class);
//                      startActivity(i);
//                      finish();


                        List<Map<String,String>> MyData = null;
                        Taxmodel mydata = new Taxmodel();
                        MyData= mydata.doInBackground();

                        String[] fromwhere = {"Taxname", "Taxper", "TaxID"};

                        int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};

                        ADAhere = new SimpleAdapter(getApplicationContext(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                                status = (String) obj.get("Isactive");

                                View view = super.getView(position, convertView, parent);


                                ImageView imageView = view.findViewById(R.id.editicon);
                                ImageView imageView1 = view.findViewById(R.id.deleteicon);
                                CheckBox uomstatus = view.findViewById(R.id.quantity);
                                uomstatus.setEnabled(false);

                                if (status.equals("1")) {
                                    uomstatus.setChecked(true);
                                } else {
                                    uomstatus.setChecked(false);
                                }


                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                                        ID = (String) obj.get("TaxID");
                                        editCustomDialog();
                                        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                imageView1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                                        ID = (String) obj.get("TaxID");
                                        deleteCustomDialog();
                                        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return view;
                            }
                        };

                        list.setAdapter(ADAhere);

                        buildDialog("Deleted Sucessfully");
                        Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();

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

            List<Map<String, String>> MyData = null;
            Taxmodel mydata = new Taxmodel();
            MyData = mydata.doInBackground();



            String[] fromwhere = {"Taxname", "Taxper", "TaxID"};

            int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};

            ADAhere = new SimpleAdapter(getApplicationContext(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status = (String) obj.get("Isactive");

                    View view = super.getView(position, convertView, parent);


                    ImageView imageView = view.findViewById(R.id.editicon);
                    ImageView imageView1 = view.findViewById(R.id.deleteicon);
                    CheckBox uomstatus = view.findViewById(R.id.quantity);
                    uomstatus.setEnabled(false);

                    if (status.equals("1")) {
                        uomstatus.setChecked(true);
                    } else {
                        uomstatus.setChecked(false);
                    }


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("TaxID");
                            editCustomDialog();
                            Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("TaxID");
                            deleteCustomDialog();
                            Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return view;
                }
            };

            list.setAdapter(ADAhere);


            buildDialog("Inserted Sucessfully");

        }

        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (taxname.trim().equals("")) {

                z = "Please enter taxname ";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "Insert into Mas_Tax(Taxname,Taxper,CGST,SGST,Createdby,Createddate) values ('" + taxname + "','" + taxpercen + "','" + cgstval + "','" + sgstval + "','" + userid + "','" + currentdate + "')";
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

            List<Map<String, String>> MyData = null;
            Taxmodel mydata = new Taxmodel();
            MyData = mydata.doInBackground();



            String[] fromwhere = {"Taxname", "Taxper", "TaxID"};

            int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};


            ADAhere = new SimpleAdapter(getApplicationContext(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status = (String) obj.get("Isactive");

                    View view = super.getView(position, convertView, parent);


                    ImageView imageView = view.findViewById(R.id.editicon);
                    ImageView imageView1 = view.findViewById(R.id.deleteicon);
                    CheckBox uomstatus = view.findViewById(R.id.quantity);
                    uomstatus.setEnabled(false);

                    if (status.equals("1")) {
                        uomstatus.setChecked(true);
                    } else {
                        uomstatus.setChecked(false);
                    }


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("TaxID");
                            editCustomDialog();
                            Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("TaxID");
                            deleteCustomDialog();
                            Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return view;
                }
            };

            list.setAdapter(ADAhere);


            buildDialog( "Updated Sucessfully");
        }

        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (taxname.trim().equals("") || taxpercen.trim().equals("")) {

                z = "Please enter taxpercen and price";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Tax SET Taxname='" + taxname + "',Taxper='" + taxpercen + "',CGST='" + cgstval + "',SGST='" + sgstval + "',Createdby='" + creatby + "',Createddate='" + creatdate + "',Updatedby='" + userid + "',Updateddate='" + currentdate + "' ,Isactive='" + checkBoxAgree.isChecked() + "' WHERE TaxID='" + ID + "';";
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tax Master");
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
