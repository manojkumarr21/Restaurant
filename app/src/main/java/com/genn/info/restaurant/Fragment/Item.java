package com.genn.info.restaurant.Fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.genn.info.restaurant.Activity.Bussinessmaster;
import com.genn.info.restaurant.Activity.Plantmaster;
import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Bussnessaddmodel;
import com.genn.info.restaurant.Model.Itemmodel;
import com.genn.info.restaurant.Model.Taxmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AndroidRuntimeException;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.genn.info.restaurant.Adapter.ListsearchViewAdapter;
import com.genn.info.restaurant.Model.Listviewsearch;
import com.genn.info.restaurant.R;
import com.mobsandgeeks.saripaar.Validator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Item extends Fragment {
View view;


    ConnectionClass connectionClass;
    Connection connect;
    ResultSet rs;
    PreparedStatement stmt;


    DBHelper dbHelper;
    String cateid,uomid,varientid,matetalid;

    private Validator validator;
    String logid, name, pass, currentdate;

    protected TextView selectColoursButton;

    protected CharSequence[] colours = { "ALL", "FAVORTE", "DRINK", "LUNCH", "DINNER", "ICE CREAM" };

    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    ListView list;
    SimpleAdapter ADAhere;

    ListsearchViewAdapter adapter;
    EditText editsearch;
    String[] rank;
    String[] country;
    String[] quantity;
    String[] population;
    int[] listviewImage;
   CircleImageView image;
    ArrayList<Listviewsearch> arraylist = new ArrayList<Listviewsearch>();

    String[] smaterialtype = {"Select One","Protect","Item"};

    String materialname;

        EditText etmaterialname;
        Spinner categoryspinner;
        Spinner materialtype ;
        Spinner uomtaxspinner ;
        Spinner varitaxspinner;

    private static final int RESULT_LOAD_IMAGE = 1;

    byte[] byteArray;
    String encodedImage;
    CheckBox checkBoxAgree;

String msg;
    String ID,edit_creatby,edit_creatdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_item, container, false);

        connectionClass = new ConnectionClass();

        dbHelper = new DBHelper(getActivity());
        Cursor res1 = dbHelper.getAllLoginData();


        while (res1.moveToNext()) {
            logid = res1.getString(0);
            name = res1.getString(1);
            pass = res1.getString(2);

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


        list = view.findViewById(R.id.listview);



        List<Map<String,String>> MyData = null;
        Itemmodel mydata =new Itemmodel();
        MyData= mydata.doInBackground();
        String[] fromwhere = { "Materialname","Uomname","Catgname","MaterialType","Variantname","MaterialID"};

        int[] viewswhere = {R.id.name , R.id.uom,R.id.Category,R.id.type,R.id.varient,R.id.idtexxt};

        ADAhere = new SimpleAdapter(getActivity(), MyData,R.layout.listviewsearch_item, fromwhere, viewswhere){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view=super.getView(position, convertView, parent);

                ImageView imageView=view.findViewById(R.id.editid);
               /* HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                String image=(String)obj.get("Image");

                Log.e("hhhhhhhhhhh",""+image);*/

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("MaterialID");
                        editCustomDialog();
                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        };
        list.setAdapter(ADAhere);




        return view;
    }


    private void showCustomDialog() {


        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.itemaddlist, null);
         etmaterialname = alertLayout.findViewById(R.id.et_materialmname);

        categoryspinner = (Spinner) alertLayout.findViewById(R.id.categoryspinner);
        materialtype = (Spinner) alertLayout.findViewById(R.id.materialtype);
        uomtaxspinner = (Spinner) alertLayout.findViewById(R.id.uomspinner);
        varitaxspinner = (Spinner) alertLayout.findViewById(R.id.variantsspinner);
        image = alertLayout.findViewById(R.id.image);

        checkBoxAgree=alertLayout.findViewById(R.id.checkBoxAgree);


checkBoxAgree.setVisibility(View.GONE);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&& !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING))
                {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE );
                    // this will jump to onActivity Function after selecting image
                }
                else
                {
                    Toast.makeText(getActivity(), "No activity found to perform this task", Toast.LENGTH_SHORT).show();
                }
            }
        });






        String query = "select CatgID,Catgname from Mas_Category";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("CatgID");
                String Bussnessname = rs.getString("Catgname");
                data.add(Bussnessname);

            }


            data.add(0,"select one");
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoryspinner.setAdapter(adapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }


        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {


                if(categoryspinner.getSelectedItem() == "select one")
                {

                    //Do nothing.
                }
                else{

                    String name = categoryspinner.getSelectedItem().toString();


                    String query = "select CatgID,Catgname from Mas_Category where Catgname='"+name+"'";
                    try {
                        connect=connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            cateid = rs.getString("CatgID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(getActivity(), ""+cateid, Toast.LENGTH_SHORT)

                            .show();


                }


            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String query1 = "select UomID,Uomname from Mas_Uom";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("UomID");
                String Bussnessname = rs.getString("Uomname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");

            ArrayAdapter<String> adapter1 =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,data);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            uomtaxspinner.setAdapter(adapter1);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        uomtaxspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = uomtaxspinner.getSelectedItem().toString();


                if (uomtaxspinner.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {


                    String query = "select UomID,Uomname from Mas_Uom where Uomname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            uomid = rs.getString("UomID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(getActivity(), "" + uomid, Toast.LENGTH_SHORT)

                            .show();

                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, smaterialtype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialtype.setAdapter(adapter2);


materialtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (materialtype.getSelectedItem() == "Select One") {

            //Do nothing.
        } else {

            Toast.makeText(getActivity(), ""+materialtype.getSelectedItem(), Toast.LENGTH_SHORT).show();
            matetalid=""+materialtype.getSelectedItem();

        }
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});





        String query2 = "select VariantID,Variantname from Mas_Variant";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query2);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("VariantID");
                String Bussnessname = rs.getString("Variantname");
                data.add(Bussnessname);

            }
            data.add(0,"select one");


            ArrayAdapter<String> adapter3 =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, data);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            varitaxspinner.setAdapter(adapter3);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        varitaxspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = varitaxspinner.getSelectedItem().toString();


                if (varitaxspinner.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {


                    String query = "select VariantID,Variantname from Mas_Variant where Variantname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            varientid = rs.getString("VariantID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(getActivity(), "" + varientid, Toast.LENGTH_SHORT)

                            .show();

                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = alert.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialname = etmaterialname.getText().toString();



                if (etmaterialname.getText().toString().equals("")) {
                    etmaterialname.requestFocus();
                    etmaterialname.setError("FIELD CANNOT BE EMPTY");
                }else if (categoryspinner.getSelectedItem() == "select one"){
                    categoryspinner.requestFocus();

                }else if (uomtaxspinner.getSelectedItem() == "select one"){
                    uomtaxspinner.requestFocus();
                }else if (materialtype.getSelectedItem() == "select one"){
                    materialtype.requestFocus();
                }
                else {

                    String query = "select count(*) as row from Mas_Material where Materialname='" +materialname+ "'";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            if (bussinessid.equals("0")) {
                                UploadImage uploadImage =new UploadImage();
                                uploadImage.execute("");
                                dialog.dismiss();

                            } else {
                                Toast.makeText(getActivity(), "Sorting order  Already exit", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            UploadImage uploadImage = new UploadImage();
                            uploadImage.execute("");
                            dialog.dismiss();
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


    }



    private void editCustomDialog() {

        String edit_materialname = null,edit_materialtype = null,edit_uomname = null,edit_categoryname = null,edit_varientname = null,edit_contry = null,edit_pincode = null,edit_email = null,edit_web = null,edit_mobile = null,edit_lbtno = null,teit_lbtdt = null,teit_wep_dt = null,edit_gstno = null,edit_panno = null,editimagestring = null,isactive = null;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.itemaddlist, null);
        etmaterialname = alertLayout.findViewById(R.id.et_materialmname);

        categoryspinner = (Spinner) alertLayout.findViewById(R.id.categoryspinner);
        materialtype = (Spinner) alertLayout.findViewById(R.id.materialtype);
        uomtaxspinner = (Spinner) alertLayout.findViewById(R.id.uomspinner);
        varitaxspinner = (Spinner) alertLayout.findViewById(R.id.variantsspinner);
        image = alertLayout.findViewById(R.id.image);

        checkBoxAgree=alertLayout.findViewById(R.id.checkBoxAgree);


        checkBoxAgree.setVisibility(View.VISIBLE);








        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&& !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING))
                {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE );
                    // this will jump to onActivity Function after selecting image
                }
                else
                {
                    Toast.makeText(getActivity(), "No activity found to perform this task", Toast.LENGTH_SHORT).show();
                }
            }
        });


        String query = "select * from Mas_Material as ma inner join Mas_Uom as u on ma.UomID = u.UomID inner join Mas_Category as c on ma.CatgID=c.CatgID inner join Mas_Variant as v on ma.VariantID=v.VariantID where ma.MaterialID='"+ID+"'";
        try {

            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {
                edit_materialname=String.valueOf(rs.getString("Materialname"));
                edit_materialtype=String.valueOf(rs.getString("MaterialType"));
                edit_uomname=String.valueOf(rs.getString("Uomname"));
                edit_categoryname=String.valueOf(rs.getString("Catgname"));
                edit_varientname=String.valueOf(rs.getString("Variantname"));
                encodedImage=String.valueOf(rs.getString("Image"));
                isactive=rs.getString("Isactive");
                edit_creatby=String.valueOf(rs.getString("Createdby"));
                edit_creatdate=String.valueOf(rs.getString("Createddate"));
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }





        byte[] encodeByte = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        image.setImageBitmap(bitmap);   //MyPhoto is image contro
        etmaterialname.setText(""+edit_materialname);


        if (isactive.equals("1")){
            checkBoxAgree.setChecked(true);
        }else{
            checkBoxAgree.setChecked(false);
        }


        String query4 = "select CatgID,Catgname from Mas_Category where Catgname !='"+edit_categoryname+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query4);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("CatgID");
                String Bussnessname = rs.getString("Catgname");
                data.add(Bussnessname);

            }

//            categoryspinner.set
            data.add(0,""+edit_categoryname);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categoryspinner.setAdapter(adapter);

        } catch (SQLException e) {

            e.printStackTrace();

        }


        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {


                if(categoryspinner.getSelectedItem() == "select one")
                {

                    //Do nothing.
                }
                else{

                    String name = categoryspinner.getSelectedItem().toString();


                    String query = "select CatgID,Catgname from Mas_Category where Catgname='"+name+"'";
                    try {
                        connect=connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            cateid = rs.getString("CatgID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(getActivity(), ""+cateid, Toast.LENGTH_SHORT)

                            .show();


                }


            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });





        String query1 = "select UomID,Uomname from Mas_Uom where Uomname != '"+edit_uomname+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query1);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("UomID");
                String Bussnessname = rs.getString("Uomname");
                data.add(Bussnessname);

            }
            data.add(0,""+edit_uomname);

            ArrayAdapter<String> adapter1 =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,data);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            uomtaxspinner.setAdapter(adapter1);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        uomtaxspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = uomtaxspinner.getSelectedItem().toString();


                if (uomtaxspinner.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {


                    String query = "select UomID,Uomname from Mas_Uom where Uomname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            uomid = rs.getString("UomID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(getActivity(), "" + uomid, Toast.LENGTH_SHORT)

                            .show();

                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        ArrayList<String> data11 = new ArrayList<String>();

        for (int i=0;i<smaterialtype.length;i++) {


     data11.add(i, smaterialtype[i]);


 }

        data11.add(0,""+edit_materialtype);

        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, data11);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialtype.setAdapter(adapter2);


        materialtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (materialtype.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {
                    Toast.makeText(getActivity(), ""+materialtype.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    matetalid=""+materialtype.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        String query2 = "select VariantID,Variantname from Mas_Variant where Variantname != '"+edit_varientname+"'";
        try {
            connect=connectionClass.CONN();
            stmt = connect.prepareStatement(query2);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("VariantID");
                String Bussnessname = rs.getString("Variantname");
                data.add(Bussnessname);

            }
            data.add(0,""+edit_varientname);

            ArrayAdapter<String> adapter3 =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, data);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            varitaxspinner.setAdapter(adapter3);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        varitaxspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view,

                                       int position, long id) {

                String name = varitaxspinner.getSelectedItem().toString();


                if (varitaxspinner.getSelectedItem() == "select one") {

                    //Do nothing.
                } else {


                    String query = "select VariantID,Variantname from Mas_Variant where Variantname='" + name + "'";
                    try {
                        connect = connectionClass.CONN();
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();
                        ArrayList<String> data = new ArrayList<String>();

                        if (rs.next()) {
                            varientid = rs.getString("VariantID");
                        }


                    } catch (SQLException e) {

                        e.printStackTrace();

                    }


                    Toast.makeText(getActivity(), "" + varientid, Toast.LENGTH_SHORT)

                            .show();

                }
            }
            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "DELETE FROM Mas_Material WHERE MaterialID='"+ID+"'";
                try {

                    connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();



                }catch (SQLException e) {
                    e.printStackTrace();
                }
//                delete();


                List<Map<String,String>> MyData = null;
                Itemmodel mydata =new Itemmodel();
                MyData= mydata.doInBackground();
                String[] fromwhere = { "Materialname","Uomname","Catgname","MaterialType","Variantname","MaterialID"};

                int[] viewswhere = {R.id.name , R.id.uom,R.id.Category,R.id.type,R.id.varient,R.id.idtexxt};

                ADAhere = new SimpleAdapter(getActivity(), MyData,R.layout.listviewsearch_item, fromwhere, viewswhere){
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {

                        View view=super.getView(position, convertView, parent);

                        ImageView imageView=view.findViewById(R.id.editid);
               /* HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                String image=(String)obj.get("Image");

                Log.e("hhhhhhhhhhh",""+image);*/

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                ID=(String)obj.get("MaterialID");
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


        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = alert.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialname = etmaterialname.getText().toString();



                if (etmaterialname.getText().toString().equals("")) {
                    etmaterialname.requestFocus();
                    etmaterialname.setError("FIELD CANNOT BE EMPTY");
                }else if (categoryspinner.getSelectedItem() == "select one"){
                    categoryspinner.requestFocus();

                }else if (uomtaxspinner.getSelectedItem() == "select one"){
                    uomtaxspinner.requestFocus();
                }else if (materialtype.getSelectedItem() == "select one"){
                    materialtype.requestFocus();
                }
                else {

                    String query = "select count(*) as row,MaterialID from Mas_Material where Materialname='" +materialname+ "'  group by MaterialID";
                    try {

                        connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                        stmt = connect.prepareStatement(query);
                        rs = stmt.executeQuery();


                        if (rs.next()) {
                            String bussinessid = String.valueOf(rs.getString("row"));
                            String materialid = String.valueOf(rs.getString("MaterialID"));
                            if (bussinessid.equals("0") || materialid.equals(""+ID)) {
                                Update update = new Update();
                                update.execute("");
                                dialog.dismiss();

                            } else {
                                Toast.makeText(getActivity(), "Sorting order  Already exit", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Update update = new Update();
                            update.execute("");
                            dialog.dismiss();
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            }
        });




    }




    private void selectImage() {
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






    public class UploadImage extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPostExecute(String r) {
            List<Map<String,String>> MyData = null;
            Itemmodel mydata =new Itemmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Materialname","Uomname","Catgname","MaterialType","Variantname","MaterialID"};

            int[] viewswhere = {R.id.name , R.id.uom,R.id.Category,R.id.type,R.id.varient,R.id.idtexxt};

            ADAhere = new SimpleAdapter(getActivity(), MyData,R.layout.listviewsearch_item, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editid);
               /* HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                String image=(String)obj.get("Image");

                Log.e("hhhhhhhhhhh",""+image);*/

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("MaterialID");
                            editCustomDialog();
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

            if (materialname.trim().equals("")) {

                z = "Please enter materialname ";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

                        String query = "Insert into Mas_Material(Materialname,UomID,CatgID,MaterialType,VariantID,Image,Createdby,Createddate) values ('" + materialname + "','" + uomid + "','" + cateid + "','" + matetalid + "','" + varientid + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK  && null != data)
        {
            // getting the selected image, setting in imageview and converting it to byte and base 64

            Bitmap originBitmap = null;
            Uri selectedImage = data.getData();
            Toast.makeText(getActivity(), selectedImage.toString(), Toast.LENGTH_LONG).show();
            InputStream imageStream;
            try
            {
                imageStream =getActivity().getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e.getMessage().toString());
            }
            if (originBitmap != null)
            {
                this.image.setImageBitmap(originBitmap);
                Log.w("Image Setted in", "Done Loading Image");
                try
                {
                    Bitmap image1 = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image1.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    // Calling the background process so that application wont slow down


                    //End Calling the background process
                    //
                    // so that application wont slow down
                }
                catch (Exception e)
                {
                    Log.w("OOooooooooo","exception");
                }
                Toast.makeText(getActivity(), "Conversion Done",Toast.LENGTH_SHORT).show();
            }
            // End getting the selected image, setting in imageview and converting it to byte and base 64
        }
        else
        {
            System.out.println("Error Occured");
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
            Itemmodel mydata =new Itemmodel();
            MyData= mydata.doInBackground();
            String[] fromwhere = { "Materialname","Uomname","Catgname","MaterialType","Variantname","MaterialID"};

            int[] viewswhere = {R.id.name , R.id.uom,R.id.Category,R.id.type,R.id.varient,R.id.idtexxt};

            ADAhere = new SimpleAdapter(getActivity(), MyData,R.layout.listviewsearch_item, fromwhere, viewswhere){
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    View view=super.getView(position, convertView, parent);

                    ImageView imageView=view.findViewById(R.id.editid);
               /* HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                String image=(String)obj.get("Image");

                Log.e("hhhhhhhhhhh",""+image);*/

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("MaterialID");
                            editCustomDialog();
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return view;
                }
            };
            list.setAdapter(ADAhere);

            buildDialog("Updated Sucessfully");

        }
        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (materialname.trim().equals("")) {

                z = "Please enter materialname ";

            } else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {



//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Material SET Materialname='"+materialname+"', UomID='"+uomid+"',CatgID='"+cateid+"',MaterialType='"+matetalid+"',VariantID='"+varientid+"',Image='"+encodedImage+"',Createdby='"+edit_creatby+"',Createddate='"+edit_creatdate+"',Updatedby='"+name+"',Updateddate='"+currentdate+"' ,Isactive='"+checkBoxAgree.isChecked()+"' WHERE MaterialID='"+ID+"';";
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

    private void buildDialog(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
