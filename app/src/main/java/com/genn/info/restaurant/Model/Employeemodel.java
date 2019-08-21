package com.genn.info.restaurant.Model;

import com.genn.info.restaurant.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employeemodel {


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
                    String query = "select * from Mas_Employee";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Map<String,String> datanum=new HashMap<String,String>();
                    datanum.put("ID",rs.getString("ID"));
                    datanum.put("Empno",rs.getString("Empno"));
                    datanum.put("Cardno",rs.getString("Cardno"));
                    datanum.put("Name",rs.getString("Name"));
                    datanum.put("Doj",rs.getString("Doj"));
                    datanum.put("Mobile",rs.getString("Mobile"));
                    datanum.put("Mail",rs.getString("Mail"));
                    datanum.put("Salary",rs.getString("Salary"));
                    datanum.put("Aadhaarno",rs.getString("Aadhaarno"));
                    datanum.put("Status",rs.getString("Status"));

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
