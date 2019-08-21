package com.genn.info.restaurant.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.genn.info.restaurant.Activity.Usergroup;
import com.genn.info.restaurant.Activity.Varientmaster;
import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Taxmodel;
import com.genn.info.restaurant.Model.Usergroupmodel;
import com.genn.info.restaurant.Model.Varientmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.genn.info.restaurant.Adapter.TaxAdapter;
import com.genn.info.restaurant.Model.TaxViewsearh;
import com.genn.info.restaurant.R;
import com.mobsandgeeks.saripaar.Validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
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
import java.util.Locale;
import java.util.Map;



import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tax extends Fragment{

    View view;


    ConnectionClass connectionClass;
    Connection connect;
    ResultSet rs;
    PreparedStatement stmt;


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
    LayoutInflater inflater;
    ViewGroup container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_tax, container, false);

        connectionClass = new ConnectionClass();

        dbHelper = new DBHelper(getActivity());
        Cursor res1 = dbHelper.getAllLoginData();
/*
        editicon=view.findViewById(R.id.editicon);
        id=view.findViewById(R.id.idview);

        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("fdgfgdhdhgdgdthf",""+id.getText().toString());

            }
        });*/

        while (res1.moveToNext()) {
            logid = res1.getString(0);
            name = res1.getString(1);
            pass = res1.getString(2);
            userid = res1.getString(3);

        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        currentdate = dateFormat.format(date);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomDialog();
            }
        });



        // Locate the ListView in listview_main.xml
        list = view.findViewById(R.id.listview);


        editsearch=view.findViewById(R.id.search);

        List<Map<String, String>> MyData = null;
        Taxmodel mydata = new Taxmodel();
        MyData = mydata.doInBackground();



        String[] fromwhere = {"Taxname", "Taxper", "TaxID"};

        int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};

        ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
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
                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                    }
                });
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                        ID = (String) obj.get("TaxID");
                        deleteCustomDialog();
                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }
        };

        list.setAdapter(ADAhere);

     /*   selectColoursButton =view.findViewById(R.id.select_colours);

        selectColoursButton.setOnClickListener(this);*/
        return view;
    }


    public void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.taxaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                                Toast.makeText(getActivity(), "Sorting order  Already exit", Toast.LENGTH_SHORT).show();
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
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.taxaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

        builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
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
               /* Intent i = new Intent(getActivity(), Tax.class);
                startActivity(i);*/



                List<Map<String, String>> MyData = null;
                Taxmodel mydata = new Taxmodel();
                MyData = mydata.doInBackground();



                String[] fromwhere = {"Taxname", "Taxper", "TaxID"};

                int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};

                ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
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
                                Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                            }
                        });
                        return view;
                    }
                };

                list.setAdapter(ADAhere);

                buildDialog("Deleted Sucessfully");
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
                                Toast.makeText(getActivity(), "User already exit", Toast.LENGTH_SHORT).show();
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

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
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

                        ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
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
                                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                imageView1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                                        ID = (String) obj.get("TaxID");
                                        deleteCustomDialog();
                                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return view;
                            }
                        };

                        list.setAdapter(ADAhere);

                        buildDialog("Deleted Sucessfully");
                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();

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

            ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
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
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("TaxID");
                            deleteCustomDialog();
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
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


            ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.taxviewseaechlist, fromwhere, viewswhere) {
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
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                            ID = (String) obj.get("TaxID");
                            deleteCustomDialog();
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
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



/*
    @Override

    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.select_colours:

                // TODO: Show the colours dialog

                showSelectColoursDialog();

                break;

            default:

                break;

        }

    }
    protected void showSelectColoursDialog() {

        boolean[] checkedColours = new boolean[colours.length];

        int count = colours.length;

        for(int i = 0; i < count; i++)

            checkedColours[i] = selectedColours.contains(colours[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked)

                    selectedColours.add(colours[which]);

                else

                    selectedColours.remove(colours[which]);

                onChangeSelectedColours();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Category");

        builder.setMultiChoiceItems(colours, checkedColours, coloursDialogListener);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CLEAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedColours.clear();
                selectColoursButton.setText("");
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();




        dialog.show();

    }

    protected void onChangeSelectedColours() {

        StringBuilder stringBuilder = new StringBuilder();

        for(CharSequence colour : selectedColours)

            stringBuilder.append(colour + ",");

        selectColoursButton.setText(stringBuilder.toString());

    }*/

  /*  private void selectImage() {
        final CharSequence[] options = { "Choose from Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    image.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                image.setImageBitmap(thumbnail);
            }
        }
    }*/



    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

