package com.genn.info.restaurant.Fragment;


import android.app.AlertDialog;
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
import com.genn.info.restaurant.Activity.Varientmaster;
import com.genn.info.restaurant.Connection.ConnectionClass;
import com.genn.info.restaurant.Connection.DBHelper;
import com.genn.info.restaurant.Model.Bussnessaddmodel;
import com.genn.info.restaurant.Model.Categorymodel;
import com.genn.info.restaurant.Model.Varientmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AndroidRuntimeException;
import android.util.Base64;
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

import com.genn.info.restaurant.Adapter.CategoryListAdapter;
import com.genn.info.restaurant.Model.Categorylistmodel;
import com.genn.info.restaurant.R;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
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
public class Category extends Fragment {

    View view;
    ConnectionClass connectionClass;
    Connection connect;


    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mToggle;

    DBHelper dbHelper;
    String bussid;




    private static final int RESULT_LOAD_IMAGE = 1;

    byte[] byteArray;
    String encodedImage;
    private Validator validator;
    String logid, name, pass, currentdate,userid;
    CircleImageView circleImageView;

    @Length(min = 1, max = 50)
    EditText catename;

    @Length(min = 1, max = 50)
    EditText catesortid;

    String varinamestr, varisort;

    ResultSet rs;
    PreparedStatement stmt;


    ListView categorylist;
    SimpleAdapter ADAhere;


    CheckBox checkBoxAgree;

    String msg;
    String ID, status, ed_uom, edit_plant = null, creatby, creatdate,imagestring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_category, container, false);

        connectionClass = new ConnectionClass();

        dbHelper = new DBHelper(getActivity());

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


        categorylist=view.findViewById(R.id.listview);


        List<Map<String, String>> MyData = null;
        Categorymodel mydata = new Categorymodel();
        MyData = mydata.doInBackground();
        String[] fromwhere = {"Name", "ID"};

        int[] viewswhere = {R.id.rank,R.id.idview};

        ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.category_listitem, fromwhere, viewswhere) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                status = (String) obj.get("Isactive");

                View view = super.getView(position, convertView, parent);


                ImageView imageView = view.findViewById(R.id.editicon);
                ImageView deleteicon = view.findViewById(R.id.delicon);
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
                        ID = (String) obj.get("ID");
                      editCustomDialog();
                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                    }
                });

                deleteicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                        ID=(String)obj.get("ID");
                        deleteCustomDialog();
                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }
        };

        categorylist.setAdapter(ADAhere);


        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCustomDialog();

            }
        });




        return view;
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.categoryaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        catename = dialogView.findViewById(R.id.et_catename);

        circleImageView=dialogView.findViewById(R.id.category);

        checkBoxAgree = dialogView.findViewById(R.id.checkBoxAgree);

        checkBoxAgree.setVisibility(View.GONE);





        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage();

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
                // End Opening the Gallery and selecting media


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


                varinamestr = catename.getText().toString();
//                varisort = catesortid.getText().toString();
                imagestring=encodedImage;

                if (catename.getText().toString().equals("")) {
                    catename.requestFocus();
                    catename.setError("FIELD CANNOT BE EMPTY");
                }else if (imagestring == null) {
                    buildDialog("Please select the image");
                }
                else {

                    String query = "select count(*) as row from Mas_Ledgergroup where Ledgergrpname='" + varinamestr +"Sales"+ "'";
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
                                Toast.makeText(getActivity(), "ledgergroup  Already exit", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            UploadImage uploadImage = new UploadImage();
                            uploadImage.execute("");
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
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.categoryaddlist, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);


        String query = "select * from Mas_Prodcatg where ID='" + ID + "'";
        try {

            connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
            stmt = connect.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {

                bussid = String.valueOf(rs.getString("ID"));
                edit_plant = String.valueOf(rs.getString("Name"));
                encodedImage = String.valueOf(rs.getString("Image"));
                edit_creatby = String.valueOf(rs.getString("Createdby"));
                edit_creatdate = String.valueOf(rs.getString("Createddate"));

                isactive = rs.getString("Isactive");

            }

            Log.e("ffryutfffffff", "" + isactive);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        catename = dialogView.findViewById(R.id.et_catename);
        catename.setEnabled(false);
        circleImageView=dialogView.findViewById(R.id.category);
        checkBoxAgree = dialogView.findViewById(R.id.checkBoxAgree);


        checkBoxAgree.setVisibility(View.VISIBLE);




        byte[] encodeByte = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        circleImageView.setImageBitmap(bitmap);
        catename.setText("" + edit_plant);
//        catesortid.setText("" + edit_sort);



        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage();

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
                // End Opening the Gallery and selecting media


            }
        });


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

        builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = "DELETE FROM Mas_Category WHERE CatgID='" + ID + "'";
                try {
                    connect = connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                    stmt = connect.prepareStatement(query);
                    rs = stmt.executeQuery();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                List<Map<String, String>> MyData = null;
                Categorymodel mydata = new Categorymodel();
                MyData = mydata.doInBackground();
                String[] fromwhere = {"Catgname", "Sortorder", "CatgID"};

                int[] viewswhere = {R.id.rank, R.id.country, R.id.idview};

                ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.category_listitem, fromwhere, viewswhere) {
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {

                        HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                        status = (String) obj.get("Isactive");

                        View view = super.getView(position, convertView, parent);


                        ImageView imageView = view.findViewById(R.id.editicon);
                        CheckBox uomstatus = view.findViewById(R.id.quantity);

                        if (status.equals("1")) {
                            uomstatus.setChecked(true);
                        } else {
                            uomstatus.setChecked(false);
                        }


                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
                                ID = (String) obj.get("CatgID");
//                        editCustomDialog();
                                Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                            }
                        });
                        return view;
                    }
                };

                categorylist.setAdapter(ADAhere);
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



                varinamestr = catename.getText().toString();
                imagestring=encodedImage;

                if (catename.getText().toString().equals("")) {
                    catename.requestFocus();
                    catename.setError("FIELD CANNOT BE EMPTY");
                }else if (imagestring == null) {
                    buildDialog("Please select the image");
                }
                else {
                    String query = "select count(*) as row,ID from Mas_Prodcatg where Name='" + varinamestr + "'group by  ID";
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
                            String BussID = String.valueOf(rs.getString("ID"));

                            if (bussinessid.equals("1") && BussID.equals("" + ID))
                            {
                                Update update = new Update();
                                update.execute("");
                                alertDialog.dismiss();

                            }
                            else
                                {
                                Toast.makeText(getActivity(), "User already exit", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
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
            Categorymodel mydata = new Categorymodel();
            MyData = mydata.doInBackground();
            String[] fromwhere = {"Name", "ID"};
            int[] viewswhere = {R.id.rank,R.id.idview};

            ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.category_listitem, fromwhere, viewswhere) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status = (String) obj.get("Isactive");

                    View view = super.getView(position, convertView, parent);


                    ImageView imageView = view.findViewById(R.id.editicon);
                    ImageView deleteicon = view.findViewById(R.id.delicon);
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
                            ID = (String) obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return view;
                }
            };

            categorylist.setAdapter(ADAhere);

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

                        // Get db connection first.
                        Connection dbConn = connectionClass.CONN();
                        // Create CallableStatement object.
                        String storedProcudureCall = "{call sp_Insert_Productcatg(?,?,?)};";
                        CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
                        final ArrayList list = new ArrayList();
                        // Set input parameters value.
                        cStmt.setString(1,varinamestr);
                        cStmt.setInt(2, Integer.parseInt(userid));
                        cStmt.setString(3,imagestring);
                        // Execute stored procedure.
                        rs = cStmt.executeQuery();
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
            Categorymodel mydata = new Categorymodel();
            MyData = mydata.doInBackground();
            String[] fromwhere = {"Name", "ID"};
            int[] viewswhere = {R.id.rank,R.id.idview};

            ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.category_listitem, fromwhere, viewswhere) {
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {

                    HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                    status = (String) obj.get("Isactive");

                    View view = super.getView(position, convertView, parent);


                    ImageView imageView = view.findViewById(R.id.editicon);
                    ImageView deleteicon = view.findViewById(R.id.delicon);
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
                            ID = (String) obj.get("ID");
                            editCustomDialog();
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });

                    deleteicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                            ID=(String)obj.get("ID");
                            deleteCustomDialog();
                            Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return view;
                }
            };

            categorylist.setAdapter(ADAhere);

            buildDialog( "Updated Sucessfully");
        }

        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            msg = "";

            if (varinamestr.trim().equals("") || varisort.trim().equals("")) {

                z = "Please enter category and sortorder";

            } else {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {

//                        String query = "Insert into Mas_Business(Bussname,Add1,Add2,Add3,Add4,Add5,Pincode,Email,Web,Mobile,LBTno,LBTdt,Expdt,GSTno,panno,Image,Createdby,Createddate) values ('" + ed_bussname + "','" + ed_door + "','" + ed_street + "','" + ed_city + "','" + ed_state + "','" + ed_contry + "','" + ed_pincode + "','" + ed_email + "','" + ed_web + "','" + ed_mobile + "','" + ed_lbtno + "','" + te_lbtdt + "','" + te_wep_dt + "','" + ed_gstno + "','" + ed_panno + "','" + encodedImage + "','" + name + "','" + currentdate + "')";
                        String query = "UPDATE Mas_Prodcatg SET Name='" + varinamestr + "',Image='" + imagestring + "',Createdby='" + creatby + "',Createdate='" + creatdate + "',Updateby='" + userid + "',Updatedate='" + currentdate + "' ,Isactive='" + checkBoxAgree.isChecked() + "' WHERE ID='" + ID + "';";
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
        builder.setTitle("Category Master");
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
                imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e.getMessage().toString());
            }
            if (originBitmap != null)
            {
                this.circleImageView.setImageBitmap(originBitmap);
                Log.w("Image Setted in", "Done Loading Image");
                try
                {
                    Bitmap image = ((BitmapDrawable) circleImageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
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

    private void deleteCustomDialog() {

        //Uncomment the below code to Set the message and title from the strings.xml file

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("DELETE VARIENT");

        //Setting message manually and performing action on button click
        builder.setMessage("If You Want To Delete")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String query = "DELETE FROM Mas_Prodcatg WHERE ID='"+ID+"'";
                        try {

                            connect=connectionClass.CONN();
//                    stmt = connect.prepareStatement(query);
                            stmt = connect.prepareStatement(query);
                            rs = stmt.executeQuery();



                        }catch (SQLException e) {
                            e.printStackTrace();
                        }

                        List<Map<String, String>> MyData = null;
                        Categorymodel mydata = new Categorymodel();
                        MyData = mydata.doInBackground();
                        String[] fromwhere = {"Name", "ID"};

                        int[] viewswhere = {R.id.rank,R.id.idview};

                        ADAhere = new SimpleAdapter(getActivity(), MyData, R.layout.category_listitem, fromwhere, viewswhere) {
                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {

                                HashMap<String, Object> obj = (HashMap<String, Object>) ADAhere.getItem(position);
//                ID=(String)obj.get("UomID");
                                status = (String) obj.get("Isactive");

                                View view = super.getView(position, convertView, parent);


                                ImageView imageView = view.findViewById(R.id.editicon);
                                ImageView deleteicon = view.findViewById(R.id.delicon);
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
                                        ID = (String) obj.get("ID");
                                        editCustomDialog();
                                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                deleteicon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
                                        ID=(String)obj.get("ID");
                                        deleteCustomDialog();
                                        Toast.makeText(getActivity(), ID, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return view;
                            }
                        };

                        categorylist.setAdapter(ADAhere);



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

}
