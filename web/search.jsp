<%-- 
    Document   : search
    Created on : Feb 25, 2021, 9:06:42 AM
    Author     : ACER
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <form action="DispatcherServlet">
            Search value <input type="text" name="txtSearchValue" value=<%=request.getParameter("txtSearchValue")%> /><br/>
            <input type="submit" value="search" name="btnAction" />          
        </form>
        <%
            String searchValue = request.getParameter("txtSearchValue");
            int count = 0;
            if (searchValue != null) {
                List<person.PersonDTO> result = (List<person.PersonDTO>) request.getAttribute("searchResult");
                if (result == null) {%>
        <h2> No record is found </h2>
        <%
        } else {

        %>    
        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Last name</th>
                    <th>Is Admin</th>
                    <th>Delete</th>
                    <th>Update</th>
                </tr>
            </thead>  
            <tbody>
            <%for (person.PersonDTO person : result) {
                    String urlDeleteString = "DispatcherServlet"
                            + "?btnAction=delete"
                            + "&username=" + person.getUsername()
                            + "&txtSearchValue=" + searchValue;
            %>
            <form action="DispatcherServlet" method="POST">
            
            <tr>          
                <td><%=++count%></td>
                <td>
                    <%=person.getUsername()%>
                    <input type="hidden" name="txtUsername" value="<%=person.getUsername()%>" />
                </td>
                <td>
                    <input type="text" name="txtPassword" value="<%=person.getPassword()%>" />
                </td>
                <td><%=person.getLastName()%></td>
                <td>
                    <input type="checkbox" name="checkAdmin" value="ON" 
                           <%
                               if(person.isIsAdmin()){
                                   %>
                           checked ="checked"
                           <%
                               }
                           %>
                           />
                </td>
                <td>
                    <a href="<%=urlDeleteString%>">Delete</a>
                </td>  
                <td>
                    <input type="hidden" name="txtSearchValue" value="<%=searchValue%>" />
                    <input type="submit" value="update" name="btnAction" />
                </td>                            
            </tr>
            </form>
            <%
                    }
            %>
            </tbody>
        </table><%}
                } else{%>
        <h2>No record is found</h2>
        <% }
            
        %>
    </body>
</html>
