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
import com.genn.info.restaurant.Model.Customeraddmodel;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerMaster extends AppCompatActivity {

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
    @Length(min = 1, max = 50)
    EditText Customername;

String Customernamestr;

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



    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    EditText editTextPhone;


    TextView dobdate;


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



    ListView cuslist;
    SimpleAdapter ADAhere;

    String msg;
    String ID;

    String ed_customer,ed_door,ed_street,ed_city,ed_state,ed_contry,ed_pincode,ed_email,ed_web,ed_mobile,ed_lbtno,te_lbtdt,te_wep_dt,ed_gstno,ed_panno,imagestring;
    String logid,name,pass,userid,currentdate;
    String edit_bussname,bussinessid,BussID,edit_door,edit_street,edit_city,edit_state,edit_contry,edit_pincode,edit_email,edit_web,edit_mobile,edit_lbtno,teit_lbtdt,teit_wep_dt,edit_gstno,edit_panno,editimagestring,isactive,creatby,creatdate;

    Date lptdatefor,expdatefor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_master);


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



        cuslist=(ListView)findViewById(R.id.customerlist);

        List<Map<String,String>> MyData = null;
        Customeraddmodel mydata =new Customeraddmodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Cusname","Contactmail","Contactmobile","DOB","ID"};

        int[] viewswhere = {R.id.bussness ,R.id.email,R.id.phone,R.id.DOB,R.id.idview};

        ADAhere = new SimpleAdapter(CustomerMaster.this, MyData,R.layout.customeraddlist, fromwhere, viewswhere){
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
                        Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });
                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("ID");
                        deleteCustomDialog();
                        Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        };

        cuslist.setAdapter(ADAhere);






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

                    startActivity(new Intent(CustomerMaster.this, Admin.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                } else if (id == R.id.nav_dashboard) {


                    startActivity(new Intent(CustomerMaster.this, Userview.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.product) {


                    startActivity(new Intent(CustomerMaster.this, Product.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_settings) {


                    startActivity(new Intent(CustomerMaster.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_claims) {


                    startActivity(new Intent(CustomerMaster.this, Report.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
              /*      dash_linearLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));*/

                }else if (id == R.id.nav_signout) {

                    dbHelper.deleteAll();
                    startActivity(new Intent(CustomerMaster.this, Login.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.bussiness) {

                    startActivity(new Intent(CustomerMaster.this, Bussinessmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.plant) {

                    startActivity(new Intent(CustomerMaster.this, Plantmaster.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.uom) {

                    startActivity(new Intent(CustomerMaster.this, Uom.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                }else if (id == R.id.Varient) {

                    startActivity(new Intent(CustomerMaster.this, Varientmaster.class));
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
        View dialogView = LayoutInflater.from(this).inflate(R.layout.customermasadd, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        Customername=dialogView.findViewById(R.id.customername);
        doorno=dialogView.findViewById(R.id.doorno);
        street=dialogView.findViewById(R.id.street);
        city=dialogView.findViewById(R.id.city);
        state=dialogView.findViewById(R.id.state);
        contry=dialogView.findViewById(R.id.contry);
        pincode=dialogView.findViewById(R.id.pincode);
        email=dialogView.findViewById(R.id.email);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        dobdate=dialogView.findViewById(R.id.dobdate);
        gstno=dialogView.findViewById(R.id.gstno);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        checkBoxAgree.setVisibility(View.GONE);







        dobdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(CustomerMaster.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dobdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                te_lbtdt=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
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
                ed_customer = Customername.getText().toString();
                ed_door = doorno.getText().toString();
                ed_street = street.getText().toString();
                ed_city = city.getText().toString();
                ed_state = state.getText().toString();
                ed_contry = contry.getText().toString();
                ed_pincode = pincode.getText().toString();
                ed_email = email.getText().toString();
                ed_mobile =editTextPhone.getText().toString();
                ed_gstno=gstno.getText().toString();



                Log.e("vgvfgdhdh333dhfghfg",""+te_lbtdt);
                Log.e("vgvfgdhdhdhfghfg",""+te_wep_dt);



                if (te_lbtdt == null){
                    te_lbtdt="";
                    Log.e("1111111111111111111",""+te_lbtdt);
                }


                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if(Customername.getText().toString().equals(""))
                {
                    Customername.requestFocus();
                    Customername.setError("FIELD CANNOT BE EMPTY");
                }
                else
                {
                    String query = "select count(*) as row from Mas_Cust where Cusname='"+ed_customer+"'";
                    try {

                        connect=connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();

                        if (rs.next()) {
                            bussinessid=String.valueOf(rs.getString("row"));
                            if (bussinessid.equals("0")){
                                UploadImage uploadImage = new UploadImage();
                                uploadImage.execute("");
                                alertDialog.dismiss();

                            }else {
                                Toast.makeText(CustomerMaster.this, "UserName Already exit", Toast.LENGTH_SHORT).show();
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

        String edit_bussname = null,edit_door = null,edit_street = null,edit_city = null,edit_state = null,edit_contry = null,edit_pincode = null,edit_email = null,edit_web = null,edit_mobile = null,edit_lbtno = null,teit_lbtdt = null,teit_wep_dt = null,edit_gstno = null,edit_panno = null,editimagestring = null,edit_creatby = null,edit_creatdate = null,isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.customermasadd, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Cust where ID='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {
                edit_bussname=String.valueOf(rs.getString("Cusname"));
                edit_door=String.valueOf(rs.getString("Addr1"));
                edit_street=String.valueOf(rs.getString("Addr2"));
                edit_city=String.valueOf(rs.getString("Addr3"));
                edit_state=String.valueOf(rs.getString("Addr4"));
                edit_contry=String.valueOf(rs.getString("Addr5"));
                edit_pincode=String.valueOf(rs.getString("Addr6"));
                edit_email=String.valueOf(rs.getString("Contactmail"));
                edit_mobile=String.valueOf(rs.getString("Contactmobile"));;
                teit_lbtdt=String.valueOf(rs.getString("DOB"));
                edit_gstno=String.valueOf(rs.getString("GSTno"));
                isactive=rs.getString("Isactive");
                edit_creatby=String.valueOf(rs.getString("Createby"));
                edit_creatdate=String.valueOf(rs.getString("Createdate"));
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }


        Customername=dialogView.findViewById(R.id.customername);
        doorno=dialogView.findViewById(R.id.doorno);
        street=dialogView.findViewById(R.id.street);
        city=dialogView.findViewById(R.id.city);
        state=dialogView.findViewById(R.id.state);
        contry=dialogView.findViewById(R.id.contry);
        pincode=dialogView.findViewById(R.id.pincode);
        email=dialogView.findViewById(R.id.email);
        editTextPhone=dialogView.findViewById(R.id.editTextPhone);
        dobdate=dialogView.findViewById(R.id.dobdate);
        gstno=dialogView.findViewById(R.id.gstno);
        checkBoxAgree=dialogView.findViewById(R.id.checkBoxAgree);
        creatby=""+edit_creatby;
        creatdate=""+edit_creatdate;





        Customername.setText(""+edit_bussname);
        doorno.setText(""+edit_door);
        street.setText(""+edit_street);
        city.setText(""+edit_city);
        state.setText(""+edit_state);
        contry.setText(""+edit_contry);
        pincode.setText(""+edit_pincode);
        email.setText(""+edit_email);
        editTextPhone.setText(""+edit_mobile);
        dobdate.setText(""+teit_lbtdt);
        gstno.setText(""+edit_gstno);



        te_lbtdt=teit_lbtdt;

        Log.e("vgvfgdhdhdhfghfg",""+te_lbtdt);
        Log.e("vgvfgdhdhdhfghfg",""+te_wep_dt);


        if (isactive.equals("1")){
            checkBoxAgree.setChecked(true);
        }else{
            checkBoxAgree.setChecked(false);
        }



        dobdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                picker = new DatePickerDialog(CustomerMaster.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dobdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                te_lbtdt=""+year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
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


                ed_customer = Customername.getText().toString();
                ed_door = doorno.getText().toString();
                ed_street = street.getText().toString();
                ed_city = city.getText().toString();
                ed_state = state.getText().toString();
                ed_contry = contry.getText().toString();
                ed_gstno = gstno.getText().toString();
                ed_email = email.getText().toString();
                ed_mobile =editTextPhone.getText().toString();


                if (te_lbtdt.equals("null")){
                    te_lbtdt="";
                    Log.e("1111111111111111111",""+te_lbtdt);
                }

                if(Customername.getText().toString().equals(""))
                {
                    Customername.requestFocus();
                    Customername.setError("FIELD CANNOT BE EMPTY");
                }
                else {


                    String query = "select count(*) as row,ID from Mas_Cust where Cusname='" + ed_customer + "'group by  ID";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            bussinessid = String.valueOf(rs.getString("row"));
                            BussID = String.valueOf(rs.getString("ID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID)) {
                                Update update = new Update();
                                update.execute("");

                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(CustomerMaster.this, "User already exit", Toast.LENGTH_SHORT).show();
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
                        String query = "DELETE FROM Mas_Cust WHERE ID='"+ID+"'";
                        try {

                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();



                        }catch (SQLException e) {
                            e.printStackTrace();
                        }




                        cuslist=(ListView)findViewById(R.id.customerlist);

                        List<Map<String,String>> MyData = null;
                        Customeraddmodel mydata =new Customeraddmodel();
                        MyData= mydata.doInBackground();
                        String[] fromwhere = { "Cusname","Contactmail","Contactmobile","DOB","ID"};

                        int[] viewswhere = {R.id.bussness ,R.id.email,R.id.phone,R.id.DOB,R.id.idview};

                        ADAhere = new SimpleAdapter(CustomerMaster.this, MyData,R.layout.customeraddlist, fromwhere, viewswhere){
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
                                        Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("ID");
                                        deleteCustomDialog();
                                        Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return view;
                            }
                        };

                        cuslist.setAdapter(ADAhere);


                        buildDialog("Deleted Sucessfully");
                        Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();

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


            cuslist=(ListView)findViewById(R.id.customerlist);

            List<Map<String,String>> MyData = null;
            Customeraddmodel mydata =new Customeraddmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Cusname","Contactmail","Contactmobile","DOB","ID"};

            int[] viewswhere = {R.id.bussness ,R.id.email,R.id.phone,R.id.DOB,R.id.idview};

            ADAhere = new SimpleAdapter(CustomerMaster.this, MyData,R.layout.customeraddlist, fromwhere, viewswhere){
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
                            Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                            Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };

            cuslist.setAdapter(ADAhere);



            buildDialog("Inserted Sucessfully");

        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ed_customer.trim().equals("")) {

                z = "Please enter Bussnessname";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query="";

                        if (te_lbtdt != ""){
                            query = "Insert into Mas_Cust(Cusname,Addr1,Addr2,Addr3,Addr4,Addr5,Addr6,GSTno,DOB,Contactmobile,Contactmail,Createby,Createdate) values ('" + ed_customer + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_gstno + "','" + te_lbtdt + "','" + ed_mobile + "','" + ed_email + "','" + userid + "','" + currentdate + "')";
                        }
                        else{
                            query = "Insert into Mas_Cust(Cusname,Addr1,Addr2,Addr3,Addr4,Addr5,Addr6,GSTno,Contactmobile,Contactmail,Createby,Createdate) values ('" + ed_customer + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_gstno + "','" + ed_mobile + "','" + ed_email + "','" + userid + "','" + currentdate + "')";
                        }


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


            cuslist=(ListView)findViewById(R.id.customerlist);

            List<Map<String,String>> MyData = null;
            Customeraddmodel mydata =new Customeraddmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Cusname","Contactmail","Contactmobile","DOB","ID"};

            int[] viewswhere = {R.id.bussness ,R.id.email,R.id.phone,R.id.DOB,R.id.idview};

            ADAhere = new SimpleAdapter(CustomerMaster.this, MyData,R.layout.customeraddlist, fromwhere, viewswhere){
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
                            Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                            Toast.makeText(CustomerMaster.this, ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };

            cuslist.setAdapter(ADAhere);

            buildDialog("Updated Sucessfully");

        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (ed_customer.trim().equals("")) {

                z = "Please enter Bussnessname";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "";
                        if (te_lbtdt == ""){
                            Log.e("1111111111111111111",""+te_lbtdt);
                            query = "UPDATE Mas_Cust SET Cusname='"+ed_customer+"', Addr1='"+ed_door+"',Addr2='"+ed_street+"',Addr3='"+ed_city+"',Addr4='"+ed_state+"',Addr5='"+ed_contry+"',Addr6='"+ed_pincode+"',GSTno='"+ed_gstno+"',Contactmobile='"+ed_mobile+"',Contactmail='"+ed_email+"',Createby='"+creatby+"',Createdate='"+creatdate+"',Updateby='"+userid+"',Updatedate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE ID='"+ID+"';";
                        }else{
                            query = "UPDATE Mas_Cust SET Cusname='"+ed_customer+"', Addr1='"+ed_door+"',Addr2='"+ed_street+"',Addr3='"+ed_city+"',Addr4='"+ed_state+"',Addr5='"+ed_contry+"',Addr6='"+ed_pincode+"',GSTno='"+ed_gstno+"',DOB='"+te_lbtdt+"',Contactmobile='"+ed_mobile+"',Contactmail='"+ed_email+"',Createby='"+creatby+"',Createdate='"+creatdate+"',Updateby='"+userid+"',Updatedate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE ID='"+ID+"';";

                        }

//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(CustomerMaster.this);
        builder.setTitle("Customer Master");
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
