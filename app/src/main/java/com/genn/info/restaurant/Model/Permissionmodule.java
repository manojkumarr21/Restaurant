package com.genn.info.restaurant.Model;

import android.util.Log;

import com.genn.info.restaurant.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Permissionmodule {


    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    public List<Map<String,String>> doInBackground(String moduleid) {

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
                String query = "select * from Mas_Menus  where ModuleId='"+moduleid+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                boolean menuright;
                while (rs.next()){
                    Map<String,String> datanum=new HashMap<String,String>();
                    datanum.put("MenuID",rs.getString("MenuID"));
                    datanum.put("Moduleid",rs.getString("Moduleid"));
                    datanum.put("Menuname",rs.getString("Menuname"));


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
