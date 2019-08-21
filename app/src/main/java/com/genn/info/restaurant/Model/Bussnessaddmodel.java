package com.genn.info.restaurant.Model;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.genn.info.restaurant.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bussnessaddmodel {


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
                String query = "select * from Mas_Business";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Map<String,String> datanum=new HashMap<String,String>();
                    datanum.put("BussID",rs.getString("BussID"));
                    datanum.put("Bussname",rs.getString("Bussname"));
                    datanum.put("Add1",rs.getString("Add1"));
                    datanum.put("Add2",rs.getString("Add2"));
                    datanum.put("Add3",rs.getString("Add3"));
                    datanum.put("Add4",rs.getString("Add4"));
                    datanum.put("Add5",rs.getString("Add5"));
                    datanum.put("Pincode",rs.getString("Pincode"));
                    datanum.put("Email",rs.getString("Email"));
                    datanum.put("Web",rs.getString("Web"));
                    datanum.put("Mobile",rs.getString("Mobile"));
                    datanum.put("LBTno",rs.getString("LBTno"));
                    datanum.put("LBTdt",rs.getString("LBTdt"));
                    datanum.put("Expdt",rs.getString("Expdt"));
                    datanum.put("GSTno",rs.getString("GSTno"));
                    datanum.put("panno",rs.getString("panno"));
                    datanum.put("Isactive",rs.getString("Isactive"));
                    datanum.put("Image",rs.getString("Image"));
                    datanum.put("Createdby",rs.getString("Createdby"));
                    datanum.put("Createddate",rs.getString("Createddate"));
                    datanum.put("Updatedby",rs.getString("Updatedby"));
                    datanum.put("Updateddate",rs.getString("Updateddate"));
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
