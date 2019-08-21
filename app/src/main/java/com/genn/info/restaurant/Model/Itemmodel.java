package com.genn.info.restaurant.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.genn.info.restaurant.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Itemmodel {


    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    public List<Map<String,String>> doInBackground() {

        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try
        {
            ConnectionClass conStr=new ConnectionClass();
            connect =conStr.CONN();        // Connect to database
            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                String query = "select * from Mas_Material as ma inner join Mas_Uom as u on ma.UomID = u.UomID inner join Mas_Category as c on ma.CatgID=c.CatgID inner join Mas_Variant as v on ma.VariantID=v.VariantID";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Map<String,String> datanum=new HashMap<String,String>();
                    datanum.put("MaterialID",rs.getString("MaterialID"));
                    datanum.put("Materialname",rs.getString("Materialname"));
                    datanum.put("UomID",rs.getString("UomID"));
                    datanum.put("Uomname",rs.getString("Uomname"));
                    datanum.put("CatgID",rs.getString("CatgID"));
                    datanum.put("Catgname",rs.getString("Catgname"));
                    datanum.put("MaterialType",rs.getString("MaterialType"));
                    datanum.put("Image", rs.getString("Image"));
                    datanum.put("VariantID",rs.getString("VariantID"));
                    datanum.put("Variantname",rs.getString("Variantname"));
                    datanum.put("Isactive",rs.getString("Isactive"));
                    datanum.put("Createdby",rs.getString("Createdby"));
                    datanum.put("Createddate",rs.getString("Createddate"));
                    data.add(datanum);
                }


                ConnectionResult = " successful";
                isSuccess=true;
                connect.close();
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            ConnectionResult = ex.getMessage();
        }

        return data;
    }



}
