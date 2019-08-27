package com.genn.info.restaurant.Model;

import com.genn.info.restaurant.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vendoraddmodel {


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
                String query = "select * from Mas_Vendor";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Map<String,String> datanum=new HashMap<String,String>();
                    datanum.put("SuppID",rs.getString("SuppID"));
                    datanum.put("Suppname",rs.getString("Suppname"));
                    datanum.put("Addr1",rs.getString("Addr1"));
                    datanum.put("Addr2",rs.getString("Addr2"));
                    datanum.put("Addr3",rs.getString("Addr3"));
                    datanum.put("Addr4",rs.getString("Addr4"));
                    datanum.put("Addr5",rs.getString("Addr5"));
                    datanum.put("Addr6",rs.getString("Addr6"));
                    datanum.put("GSTno",rs.getString("GSTno"));
                    datanum.put("VendorType",rs.getString("VendorType"));
//                    datanum.put("DOB",rs.getString("DOB"));
                    datanum.put("Contactmobile",rs.getString("Contactmobile"));
                    datanum.put("Contactname",rs.getString("Contactname"));
                    datanum.put("Contactmail",rs.getString("Contactmail"));
                    datanum.put("Isactive",rs.getString("Isactive"));

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
