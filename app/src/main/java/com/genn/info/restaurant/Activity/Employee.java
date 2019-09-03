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
import com.genn.info.restaurant.Model.Employeemodel;
import com.genn.info.restaurant.Model.Plantmodel;
import com.genn.info.restaurant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee extends AppCompatActivity {


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





    @NotEmpty
    @Length(min = 1, max = 10)
    EditText Empno;



    @Length(min = 1, max = 10)
    EditText Cardno;




    @Length(min = 1, max = 50)
    EditText Name;



   Spinner PlantId,WorkLocation,DeptId,SubdeptId,EmpcatgId,Designid;


    @Email
    @Length(min = 1, max = 50)
    EditText email;


    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    EditText editTextPhone;


    @Length(min = 1, max = 30)
    EditText Salary;


    @Length(min = 1, max = 50)
    EditText Aadhaarno;


    @Length(min = 1, max = 50)
    EditText Bottomnote;


    TextView Doj;


    TextView Dol;


    TextView Dob;





    @Checked
    private CheckBox checkBoxAgree,Isfree;

    private Validator validator;
    private static final int RESULT_LOAD_IMAGE = 1;

    byte[] byteArray;
    String encodedImage;

    ResultSet rs;
    PreparedStatement stmt;



    ListView Employeee;
    SimpleAdapter ADAhere;

    String msg;
    String ID;

    String ed_Empno,ed_Cardno,ed_Name,ed_email,WorkLocationid,plantid,depid,subdepid,empcatgId,designid,ed_editTextPhone,ed_Salary,ed_Aadhaarno,ed_Doj,ed_Dol,ed_Dob;
    String logid,name,pass,userid,currentdate;
    String edit_Empno,edit_Cardno,edit_Name,edit_workid,edit_plantid,edit_depid,edit_subdepid,edit_empid,edit_desigid,edit_email,edit_editTextPhone,edit_Salary,edit_Aadhaarno,edit_Doj,edit_Dol,edit_Dob,isfree,isactive,creatby,creatdate;

    Date lptdatefor,expdatefor;

    String bussinessid,BussID,plantname,worklocname,depname,subdepname,Empcatgname,designname,status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);


        connectionClass = new ConnectionClass();


 /*       validator = new Validator(this);
        validator.setValidationListener(this);*/

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

                    startActivity(new Intent(Employee.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(Employee.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(Employee.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(Employee.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(Employee.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(Employee.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(Employee.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(Employee.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(Employee.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(Employee.this, Varientmaster.class));
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

        Employeee=(ListView)findViewById(R.id.Employeee);

        List<Map<String,String>> MyData = null;
        Employeemodel mydata =new Employeemodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Empno","Mail","Mobile","Name","Salary","Cardno","Status","ID"};

        int[] viewswhere = {R.id.empno ,R.id.email , R.id.phone,R.id.name,R.id.salary,R.id.cardno,R.id.status,R.id.idview};

        ADAhere = new SimpleAdapter(Employee.this, MyData,R.layout.employeeaddlist, fromwhere, viewswhere){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view=super.getView(position, convertView, parent);

                ImageView imageView=view.findViewById(R.id.editicon);
                ImageView deleteicon=view.findViewById(R.id.deleteicon);


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("ID");
                        editCustomDialog();
                        Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("ID");
                        deleteCustomDialog();
                        Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        };

        Employeee.setAdapter(ADAhere);


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
        View dialogView = LayoutInflater.from(this).inflate(R.layout.employeaddmaster, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        Empno=dialogView.findViewById(R.id.Empno);
        Cardno=dialogView.findViewById(R.id.Cardno);
        Name=dialogView.findViewById(R.id.name);
        PlantId=dialogView.findViewById(R.id.plantspinner);
        WorkLocation=dialogView.findViewById(R.id.WorkLocationspinner);
        DeptId=dialogView.findViewById(R.id.DeptIdspinner);
        SubdeptId=dialogView.findViewById(R.id.SubdeptIdspinner);
        EmpcatgId=dialogView.findViewById(R.id.EmpcatgIdspinner);
        Designid=dialogView.findViewById(R.id.Designidspinner);
        email=dialogView.findViewById(R.id.email);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        Doj=dialogView.findViewById(R.id.dojdate);
        Dol=dialogView.findViewById(R.id.doldate);
        Dob=dialogView.findViewById(R.id.datebirth);
        Salary=dialogView.findViewById(R.id.salary);
        Aadhaarno=dialogView.findViewById(R.id.Adtherno);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        Isfree=dialogView.findViewById(R.id.isfree);


        checkBoxAgree.setVisibility(View.GONE);



        String workqueery = "select WorkdivID,WorkLocation from Mas_Workdiv";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(workqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("WorkdivID");
                String Bussnessname = rs.getString("WorkLocation");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            WorkLocation.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        } WorkLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = WorkLocation.getSelectedItem().toString();


                if (WorkLocation.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select WorkdivID,WorkLocation from Mas_Workdiv where WorkLocation='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            WorkLocationid = rs.getString("WorkdivID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Employee.this, "" + WorkLocationid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String plantqueery = "select PlantID,Plantname from Mas_Plant";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(plantqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("PlantID");
                String Bussnessname = rs.getString("Plantname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            PlantId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }



        PlantId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = PlantId.getSelectedItem().toString();


                if (PlantId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select PlantID,Plantname from Mas_Plant where Plantname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            plantid = rs.getString("PlantID");

                        }

                    } catch (SQLException e) {

                        e.printStackTrace();
                    }

                    Toast.makeText(Employee.this, "" + plantid, Toast.LENGTH_SHORT)

                            .show();
                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        String depqueery = "select DeptId,Deptname from Mas_Dept";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(depqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("DeptId");
                String Bussnessname = rs.getString("Deptname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            DeptId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }



        DeptId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = DeptId.getSelectedItem().toString();


                if (DeptId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select DeptId,Deptname from Mas_Dept where Deptname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            depid = rs.getString("DeptId");

                        }

                    } catch (SQLException e) {

                        e.printStackTrace();
                    }

                    Toast.makeText(Employee.this, "" + depid, Toast.LENGTH_SHORT)

                            .show();
                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        String Subdepqueery = "select SubdeptId,Subdeptname from Mas_Subdept";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(Subdepqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("SubdeptId");
                String Bussnessname = rs.getString("Subdeptname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            SubdeptId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }



        SubdeptId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = SubdeptId.getSelectedItem().toString();


                if (SubdeptId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select SubdeptId,Subdeptname from Mas_Subdept where Subdeptname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            subdepid = rs.getString("SubdeptId");

                        }

                    } catch (SQLException e) {

                        e.printStackTrace();
                    }

                    Toast.makeText(Employee.this, "" + subdepid, Toast.LENGTH_SHORT)

                            .show();
                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




 String EmpcatgIdqueery = "select EmpcatgId,Catgname from Mas_Empcatg";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(EmpcatgIdqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("EmpcatgId");
                String Bussnessname = rs.getString("Catgname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            EmpcatgId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }



        EmpcatgId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = EmpcatgId.getSelectedItem().toString();


                if (EmpcatgId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select EmpcatgId,Catgname from Mas_Empcatg where Catgname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            empcatgId = rs.getString("EmpcatgId");

                        }

                    } catch (SQLException e) {

                        e.printStackTrace();
                    }

                    Toast.makeText(Employee.this, "" + empcatgId, Toast.LENGTH_SHORT)

                            .show();
                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        String Designidqueery = "select DesigId,Designame from Mas_Designation";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(Designidqueery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("DesigId");
                String Bussnessname = rs.getString("Designame");
                data.add(Bussnessname);

            }
            data.add(0,"select one");
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            Designid.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }



        Designid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = Designid.getSelectedItem().toString();


                if (Designid.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select DesigId,Designame from Mas_Designation where Designame='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            designid = rs.getString("DesigId");

                        }

                    } catch (SQLException e) {

                        e.printStackTrace();
                    }

                    Toast.makeText(Employee.this, "" + designid, Toast.LENGTH_SHORT)

                            .show();
                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        Doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Employee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Doj.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                ed_Doj=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Dol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Employee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Dol.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                ed_Dol=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                            }
                        }, year, month, day);
                picker.show();
            }
        });


        Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Employee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                ed_Dob=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

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

                ed_Empno =Empno.getText().toString();
                ed_Cardno = Cardno.getText().toString();
                ed_Name = Name.getText().toString();
                ed_editTextPhone =editTextPhone.getText().toString();
                ed_email = email.getText().toString();
                ed_Salary = Salary.getText().toString();
                ed_Aadhaarno =Aadhaarno.getText().toString();


                if (ed_Dob == null){
                    ed_Dob="";
                    Log.e("1111111111111111111",""+ed_Dob);
                }

                if (ed_Dol == null){
                    ed_Dol="";
                    Log.e("1111111111111111111",""+ed_Dol);
                }
                if (ed_Doj == null){
                    ed_Doj="";
                    Log.e("1111111111111111111",""+ed_Doj);
                }




                if(Empno.getText().toString().equals(""))

                {
                    Empno.requestFocus();
                    Empno.setError("FIELD CANNOT BE EMPTY");
                }
                else if(Cardno.getText().toString().equals(""))

                {
                    Cardno.requestFocus();
                    Cardno.setError("FIELD CANNOT BE EMPTY");
                }
              else if(Name.getText().toString().equals(""))
              {
                    Name.requestFocus();
                    Name.setError("FIELD CANNOT BE EMPTY");
                }
              else if(PlantId.getSelectedItem() == "select one")
              {
                  Toast.makeText(Employee.this, "Please Select the Plant", Toast.LENGTH_SHORT).show();
                }
              else if(WorkLocation.getSelectedItem() == "select one")
              {
                  Toast.makeText(Employee.this, "Please Select the WorkLocation", Toast.LENGTH_SHORT).show();
                }
              else if(EmpcatgId.getSelectedItem() == "select one")
              {
                  Toast.makeText(Employee.this, "Please Select the Employee", Toast.LENGTH_SHORT).show();
                }
             else if(Designid.getSelectedItem() == "select one")
              {
                  Toast.makeText(Employee.this, "Please Select the Designaton", Toast.LENGTH_SHORT).show();
                }
             else if(ed_Doj == "")
              {
                  Toast.makeText(Employee.this, "Please Select the DOJ", Toast.LENGTH_SHORT).show();
                }
             else if(Salary.getText().toString().equals(""))
                {
                    Salary.requestFocus();
                    Salary.setError("FIELD CANNOT BE EMPTY");
                }

                else
                    {
                    String query = "select count(*) as row from Mas_Employee where Empno='"+ed_Empno+"'";
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
                                Toast.makeText(Employee.this, "UserName Already exit", Toast.LENGTH_SHORT).show();
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

        String edit_plant = null,edit_work=null,edit_door = null,edit_street = null,edit_city = null,edit_state = null,edit_contry = null,edit_pincode = null,edit_email = null,edit_web = null,edit_mobile = null,edit_lbtno = null,teit_lbtdt = null,teit_wep_dt = null,edit_gstno = null,edit_panno = null,edit_tittle=null,edit_bottom=null,edit_creatby = null,edit_creatdate = null,isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.employeaddmaster, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "Select * from Mas_Employee where ID='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                edit_Empno=String.valueOf(rs.getString("Empno"));
                edit_Cardno=String.valueOf(rs.getString("Cardno"));
//                storename=String.valueOf(rs.getString("Storename"));
                edit_Name=String.valueOf(rs.getString("Name"));
                edit_plantid=String.valueOf(rs.getString("PlantId"));
                edit_workid=String.valueOf(rs.getString("WorkLocation"));
                edit_depid=String.valueOf(rs.getString("DeptId"));
                edit_subdepid=String.valueOf(rs.getString("SubdeptId"));
                edit_empid=String.valueOf(rs.getString("EmpcatgId"));
                edit_desigid=String.valueOf(rs.getString("Designid"));
                edit_Doj=String.valueOf(rs.getString("Doj"));
                edit_Dol=String.valueOf(rs.getString("Dol"));
                edit_Dob=String.valueOf(rs.getString("Dob"));
                edit_email=String.valueOf(rs.getString("Mail"));
                edit_mobile=String.valueOf(rs.getString("Mobile"));
                edit_Salary=String.valueOf(rs.getString("Salary"));
                edit_Aadhaarno=String.valueOf(rs.getString("Aadhaarno"));
                isfree=rs.getString("Isfree");
                isactive=rs.getString("Status");
                creatby=String.valueOf(rs.getString("Createby"));
                creatdate=String.valueOf(rs.getString("Createdate"));
            }

            Log.e("ffryutfffffff",""+isactive);

        }catch (SQLException e) {
            e.printStackTrace();
        }






        Empno=dialogView.findViewById(R.id.Empno);
        Cardno=dialogView.findViewById(R.id.Cardno);
        Name=dialogView.findViewById(R.id.name);
        PlantId=dialogView.findViewById(R.id.plantspinner);
        WorkLocation=dialogView.findViewById(R.id.WorkLocationspinner);
        DeptId=dialogView.findViewById(R.id.DeptIdspinner);
        SubdeptId=dialogView.findViewById(R.id.SubdeptIdspinner);
        EmpcatgId=dialogView.findViewById(R.id.EmpcatgIdspinner);
        Designid=dialogView.findViewById(R.id.Designidspinner);
        email=dialogView.findViewById(R.id.email);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        Doj=dialogView.findViewById(R.id.dojdate);
        Dol=dialogView.findViewById(R.id.doldate);
        Dob=dialogView.findViewById(R.id.datebirth);
        Salary=dialogView.findViewById(R.id.salary);
        Aadhaarno=dialogView.findViewById(R.id.Adtherno);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        Isfree=dialogView.findViewById(R.id.isfree);




        String plantnamequery = "select * from Mas_Plant where PlantID = '"+edit_plantid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(plantnamequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("PlantID");
                plantname= rs.getString("Plantname");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String plantnamequery1 = "select * from Mas_Plant where PlantID != '"+edit_plantid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(plantnamequery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("PlantID");
                String Bussnessname = rs.getString("Plantname");
                data.add(Bussnessname);

            }

            data.add(0,""+plantname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            PlantId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        PlantId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = PlantId.getSelectedItem().toString();


                if (PlantId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select * from Mas_Plant where Plantname ='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            edit_plantid = rs.getString("PlantID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Employee.this, "" + edit_plantid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        String worklocationnamequery = "select * from Mas_Workdiv where WorkdivID = '"+edit_workid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(worklocationnamequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("WorkdivID");
                worklocname= rs.getString("WorkLocation");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String worklocationnamequery1 = "select * from Mas_Workdiv where WorkdivID != '"+edit_workid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(worklocationnamequery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("WorkdivID");
                String Bussnessname = rs.getString("WorkLocation");
                data.add(Bussnessname);

            }

            data.add(0,""+worklocname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            WorkLocation.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        WorkLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = WorkLocation.getSelectedItem().toString();


                if (WorkLocation.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select * from Mas_Workdiv where WorkLocation ='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            edit_workid = rs.getString("WorkdivID");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Employee.this, "" + edit_workid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String DeptIdnamequery = "select * from Mas_Dept where DeptId = '"+edit_depid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(DeptIdnamequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("DeptId");
                depname= rs.getString("Deptname");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String DeptIdnamequery1 = "select * from Mas_Dept where DeptId != '"+edit_depid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(DeptIdnamequery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("DeptId");
                String Bussnessname = rs.getString("Deptname");
                data.add(Bussnessname);

            }

            data.add(0,""+depname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            DeptId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        DeptId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = DeptId.getSelectedItem().toString();


                if (DeptId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select * from Mas_Dept where Deptname ='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            edit_depid = rs.getString("DeptId");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Employee.this, "" + edit_depid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String Subdeptnamequery = "select * from Mas_Subdept where SubdeptId = '"+edit_subdepid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(Subdeptnamequery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("SubdeptId");
                subdepname= rs.getString("Subdeptname");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String Subdeptnamequery1 = "select * from Mas_Subdept where SubdeptId != '"+edit_subdepid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(Subdeptnamequery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("SubdeptId");
                String Bussnessname = rs.getString("Subdeptname");
                data.add(Bussnessname);

            }

            data.add(0,""+subdepname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            SubdeptId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        SubdeptId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = SubdeptId.getSelectedItem().toString();


                if (SubdeptId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select * from Mas_Subdept where Subdeptname ='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            edit_subdepid = rs.getString("SubdeptId");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Employee.this, "" + edit_subdepid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String EmpcatgIdquery = "select * from Mas_Empcatg where EmpcatgId = '"+edit_empid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(EmpcatgIdquery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("EmpcatgId");
                Empcatgname= rs.getString("Catgname");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String EmpcatgIdquery1 = "select * from Mas_Empcatg where EmpcatgId != '"+edit_empid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(EmpcatgIdquery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("EmpcatgId");
                String Bussnessname = rs.getString("Catgname");
                data.add(Bussnessname);

            }

            data.add(0,""+Empcatgname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            EmpcatgId.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        EmpcatgId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = EmpcatgId.getSelectedItem().toString();


                if (EmpcatgId.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select * from Mas_Empcatg where Catgname ='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            edit_empid = rs.getString("EmpcatgId");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Employee.this, "" + edit_empid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        String DesignidIdquery = "select * from Mas_Designation where DesigId = '"+edit_desigid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(DesignidIdquery);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            if (rs.next()) {

                String id = rs.getString("DesigId");
                designname= rs.getString("Designame");


            }
        } catch (SQLException e) {

            e.printStackTrace();

        }


        String DesignidIdquery1 = "select * from Mas_Designation where DesigId != '"+edit_desigid+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(DesignidIdquery1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("DesigId");
                String Bussnessname = rs.getString("Designame");
                data.add(Bussnessname);

            }

            data.add(0,""+designname);
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            Designid.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        Designid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = Designid.getSelectedItem().toString();


                if (Designid.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {

                    String query = "select * from Mas_Designation where Designame ='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            edit_desigid = rs.getString("DesigId");

                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(Employee.this, "" + edit_desigid, Toast.LENGTH_SHORT)

                            .show();

                }
            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        Doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Employee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Doj.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                ed_Doj=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        Dol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Employee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Dol.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                ed_Dol=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                            }
                        }, year, month, day);
                picker.show();
            }
        });


        Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(Employee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                ed_Dob=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

                            }
                        }, year, month, day);
                picker.show();
            }
        });




        Empno.setText(""+edit_Empno);
        Cardno.setText(""+edit_Cardno);
        Name.setText(""+edit_Name);
        email.setText(""+edit_email);
        editTextPhone.setText(""+edit_mobile);
        Doj.setText(""+edit_Doj);
        Dol.setText(""+edit_Dol);
        Dob.setText(""+edit_Dob);
        Salary.setText(""+edit_Salary);
        Aadhaarno.setText(""+edit_Aadhaarno);


        ed_Dob=""+edit_Dob;
        ed_Dol=""+edit_Dol;
        ed_Doj=""+edit_Doj;


        if (isfree.equals("1")){
            Isfree.setChecked(true);
        }else{
            Isfree.setChecked(false);
        }



        if (isactive.equals("Onroll")){
            checkBoxAgree.setChecked(true);
            status="Onroll";
        }else{
            checkBoxAgree.setChecked(false);
            status="Left";
        }



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
                ed_Empno =Empno.getText().toString();
                ed_Cardno = Cardno.getText().toString();
                ed_Name = Name.getText().toString();
                ed_editTextPhone =editTextPhone.getText().toString();
                ed_email = email.getText().toString();
                ed_Salary = Salary.getText().toString();
                ed_Aadhaarno =Aadhaarno.getText().toString();


                if (ed_Dob == null){
                    ed_Dob="";
                    Log.e("1111111111111111111",""+ed_Dob);
                }

                if (ed_Dol == null){
                    ed_Dol="";
                    Log.e("1111111111111111111",""+ed_Dol);
                }
                if (ed_Doj == null){
                    ed_Doj="";
                    Log.e("1111111111111111111",""+ed_Doj);
                }




                if(Empno.getText().toString().equals(""))

                {
                    Empno.requestFocus();
                    Empno.setError("FIELD CANNOT BE EMPTY");
                }
                else if(Cardno.getText().toString().equals(""))

                {
                    Cardno.requestFocus();
                    Cardno.setError("FIELD CANNOT BE EMPTY");
                }
                else if(Name.getText().toString().equals(""))
                {
                    Name.requestFocus();
                    Name.setError("FIELD CANNOT BE EMPTY");
                }
                else if(PlantId.getSelectedItem() == "select one")
                {
                    Toast.makeText(Employee.this, "Please Select the Plant", Toast.LENGTH_SHORT).show();
                }
                else if(WorkLocation.getSelectedItem() == "select one")
                {
                    Toast.makeText(Employee.this, "Please Select the WorkLocation", Toast.LENGTH_SHORT).show();
                }
                else if(EmpcatgId.getSelectedItem() == "select one")
                {
                    Toast.makeText(Employee.this, "Please Select the Employee", Toast.LENGTH_SHORT).show();
                }
                else if(Designid.getSelectedItem() == "select one")
                {
                    Toast.makeText(Employee.this, "Please Select the Designaton", Toast.LENGTH_SHORT).show();
                }
                else if(ed_Doj == "")
                {
                    Toast.makeText(Employee.this, "Please Select the DOJ", Toast.LENGTH_SHORT).show();
                }
                else if(Salary.getText().toString().equals(""))
                {
                    Salary.requestFocus();
                    Salary.setError("FIELD CANNOT BE EMPTY");
                }

                    else {

                    String query = "select count(*) as row,ID from Mas_Employee where Empno='" + ed_Empno + "'  group by ID";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            bussinessid = String.valueOf(rs.getString("row"));
                            BussID = String.valueOf(rs.getString("ID"));

                            if (bussinessid.equals("1") || BussID.equals("" + ID)) {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(Employee.this, "User already exit", Toast.LENGTH_SHORT).show();
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
            Employeemodel mydata =new Employeemodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Empno","Mail","Mobile","Name","Salary","Cardno","Status","ID"};

            int[] viewswhere = {R.id.empno ,R.id.email , R.id.phone,R.id.name,R.id.salary,R.id.cardno,R.id.status,R.id.idview};

            ADAhere = new SimpleAdapter(Employee.this, MyData,R.layout.employeeaddlist, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                            Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };

            Employeee.setAdapter(ADAhere);


            buildDialog("Inserted Sucessfully");
        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ed_Empno.trim().equals("") && ed_Name.trim().equals("")) {

                z = "Please enter Bussnessname and Bussiness id";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        if (ed_Dob == null){
                            ed_Dob="";
                            Log.e("1111111111111111111",""+ed_Dob);
                        }

                        if (ed_Dol == null){
                            ed_Dol="";
                            Log.e("1111111111111111111",""+ed_Dol);
                        }
                        if (ed_Doj == null){
                            ed_Doj="";
                            Log.e("1111111111111111111",""+ed_Doj);
                        }


                        String query = "Insert into Mas_Employee(Empno,Cardno,Name,PlantId,WorkLocation,DeptId,SubdeptId,EmpcatgId,Designid,Doj,Dol,Dob,Mobile,Mail,Salary,Aadhaarno,Isfree,Status,Createby,Createdate) values ('" + ed_Empno + "','" + ed_Cardno + "','" + ed_Name + "','" + plantid + "','" + WorkLocationid + "','" + depid + "','" + subdepid + "','" + empcatgId + "','" + designid + "','" + ed_Doj + "','" + ed_Dol + "','" + ed_Dob + "','" + ed_editTextPhone + "','" + ed_email + "','" + ed_Salary + "','" + ed_Aadhaarno + "','" + Isfree.isChecked() + "','" + "Onroll" + "','" + userid + "','" + currentdate + "')";
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
            Employeemodel mydata =new Employeemodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Empno","Mail","Mobile","Name","Salary","Cardno","Status","ID"};

            int[] viewswhere = {R.id.empno ,R.id.email , R.id.phone,R.id.name,R.id.salary,R.id.cardno,R.id.status,R.id.idview};

            ADAhere = new SimpleAdapter(Employee.this, MyData,R.layout.employeeaddlist, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editicon);
                    ImageView deleteicon=view.findViewById(R.id.deleteicon);


                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                            Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };

            Employeee.setAdapter(ADAhere);
            buildDialog( "Updated Sucessfully");

        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";
            if (ed_Empno.trim().equals("") && ed_Name.trim().equals("")) {

                z = "Please enter Bussnessname and Bussiness id";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        if (ed_Dob == null){
                            ed_Dob="";
                            Log.e("1111111111111111111",""+ed_Dob);
                        }

                        if (ed_Dol == null){
                            ed_Dol="";
                            Log.e("1111111111111111111",""+ed_Dol);
                        }
                        if (ed_Doj == null){
                            ed_Doj="";
                            Log.e("1111111111111111111",""+ed_Doj);
                        }

                        if (checkBoxAgree.isChecked()){
                            status="Onroll";
                        }else{
                            status="Left";
                        }


//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Employee SET Empno='"+ed_Empno+"',Cardno='"+ed_Cardno+"',Name='"+ed_Name+"', PlantId='"+edit_plantid+"',WorkLocation='"+edit_workid+"',DeptId='"+edit_depid+"',SubdeptId='"+edit_subdepid+"',EmpcatgId='"+edit_empid+"',Designid='"+edit_depid+"',Doj='"+ed_Doj+"',Dol='"+ed_Dol+"',Dob='"+ed_Dob+"',Mail='"+ed_email+"',Mobile='"+ed_editTextPhone+"',Salary='"+ed_Salary+"',Aadhaarno='"+ed_Aadhaarno+"',Isfree='"+Isfree.isChecked()+"',Status='"+status+"',Createby='"+creatby+"',Createdate='"+creatdate+"',Updateby='"+userid+"',Updatedate='"+currentdate+"' where ID='"+ID+"'";
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

    private void deleteCustomDialog() {

        //Uncomment the below code to Set the message and title from the strings.xml file

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("DELETE VARIENT");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String query = "DELETE FROM Mas_Employee WHERE ID='"+ID+"'";
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
                        Employeemodel mydata =new Employeemodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Empno","Mail","Mobile","Name","Salary","Cardno","Status","ID"};

                        int[] viewswhere = {R.id.empno ,R.id.email , R.id.phone,R.id.name,R.id.salary,R.id.cardno,R.id.status,R.id.idview};

                        ADAhere = new SimpleAdapter(Employee.this, MyData,R.layout.employeeaddlist, fromwhere, viewswhere){
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                View view=super.getView(position, convertView, parent);

                                ImageView imageView=view.findViewById(R.id.editicon);
                                ImageView deleteicon=view.findViewById(R.id.deleteicon);


                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("ID");
                                        editCustomDialog();
                                        Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("ID");
                                        deleteCustomDialog();
                                        Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return view;
                            }
                        };

                        Employeee.setAdapter(ADAhere);
                        Toast.makeText(Employee.this, ID, Toast.LENGTH_SHORT).show();

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




    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Employee.this);
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
