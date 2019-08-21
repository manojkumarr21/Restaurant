package com.genn.info.restaurant.Model;

import com.genn.info.restaurant.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usermodel {


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
                    String query = "select * from Mas_User as mu left outer join Mas_Employee as me on mu.Empno=me.Empno inner join Mas_Usergroup as mg on mu.Usergrpid=mg.UserGroupID";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Map<String,String> datanum=new HashMap<String,String>();
                    datanum.put("UserID",rs.getString("UserID"));
                    datanum.put("Empno",rs.getString("Empno"));
                    datanum.put("Name",rs.getString("Name"));
                    datanum.put("Username",rs.getString("Username"));
                    datanum.put("Userpwd",rs.getString("Userpwd"));
                    datanum.put("Mobileno",rs.getString("Mobileno"));
                    datanum.put("Groupname",rs.getString("Groupname"));
                    datanum.put("Mailid",rs.getString("Mailid"));
                    datanum.put("Usertype",rs.getString("Usertype"));
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
