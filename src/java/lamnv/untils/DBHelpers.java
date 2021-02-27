/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamnv.untils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ACER
 */
public class DBHelpers implements Serializable{
    public static Connection makeConnection()
        throws /*ClassNotFoundException,*/ SQLException, NamingException{
        
        Context currentContext = new InitialContext();
        Context tomcatContext = (Context)currentContext.lookup("java:comp/env");
        javax.sql.DataSource ds = (javax.sql.DataSource)tomcatContext.lookup("trial");
        Connection con = ds.getConnection();
        
//        //1 Load driver--> add driver into project
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //2 Create connection string to determine sqlserver container address
//        String url = "jdbc:sqlserver://localhost:1433;databaseName=spring2021";
//        //3 Open connection
//        Connection con = DriverManager.getConnection(url, "sa", "sa");       
        return con;
    }
}
