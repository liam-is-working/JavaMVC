/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package person;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import lamnv.untils.DBHelpers;

/**
 *
 * @author ACER
 */
public class PersonDAO implements Serializable{
    public boolean checkLogin(String username, String password)
        throws SQLException, ClassNotFoundException, NamingException{
        Connection con = null;
        PreparedStatement statement= null;
        ResultSet rs = null;
        //1.connect dtb
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                System.out.println("connect sucessfully");
                //2. create SQL string
                String sqlQuery = "SELECT username "
                        + "FROM Persons "
                        + "WHERE username = ? AND password = ? ";
                //3. create statement and assign parameter
                statement = con.prepareStatement(sqlQuery);
                statement.setString(1, username);
                statement.setString(2, password);
                //4.Execute Query
                rs = statement.executeQuery();
                //5. Process RS
                if(rs.next()){
                    return true;
                }              
            }else{
                System.out.println("cant connect to database");
            }
        }finally{
            if(rs != null)
                rs.close();
            if(statement != null)
                statement.close();
            if (con != null){
                con.close();
            }
        }
        return false;
        
    }
    
    private List<PersonDTO> personList;

    public List<PersonDTO> getPersonList() {
        return personList;
    }
    
    public boolean deleteAccountByUsername(String username) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        int result = 0;
        try{
            con = DBHelpers.makeConnection();
            String sqlString = "Delete from Persons "
                    + "where username = ?";
            stm = con.prepareStatement(sqlString);
            stm.setString(1, username);
            result = stm.executeUpdate();
        }
        finally{
            if(stm!=null)
                stm.close();
            if (con!=null)
                con.close();
        }
        return result!=0;
    }
    
    public boolean updatePasswordAndAdmin(String username, String password, boolean isAdmin) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        int result = 0;
        try{
            con = DBHelpers.makeConnection();
            String sqlString = "Update Persons "
                    + "set password = ?, isAdmin = ? "
                    + "where username = ?";
            stm = con.prepareStatement(sqlString);
            stm.setString(1, password);
            stm.setString(3, username);
            stm.setBoolean(2, isAdmin);
            result = stm.executeUpdate();
        }
        finally{
            if(stm!=null)
                stm.close();
            if (con!=null)
                con.close();
        }
        return result!=0;
    }
    
    public void searchLastName(String searchValue) 
            throws SQLException, ClassNotFoundException, NamingException{
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            con = DBHelpers.makeConnection();
            String sqlQuery = "Select username, password, lastname, isadmin "
                    + "FROM Persons "
                    + "WHERE lastname LIKE ?";
            statement = con.prepareStatement(sqlQuery);
            String searchString = "%"+searchValue+"%";
            statement.setString(1,searchString  );
            rs = statement.executeQuery();
            while (rs.next()){
                String username = rs.getString("username");
                String password = rs.getString("password");
                String lastName = rs.getNString("lastname");
                boolean isAdmin = rs.getBoolean("isAdmin");
                
                PersonDTO dto = new PersonDTO(
                        username, password, lastName, isAdmin);
                if (personList == null){
                    personList = new ArrayList<>();
                    personList.add(dto);
                }else
                    personList.add(dto);
                
            }//end when rs null
        }finally{
            if (rs!=null)
                rs.close();
            if (statement!=null)
                statement.close();
            if (con!=null)
                con.close();
            
            
        }
    }
}
