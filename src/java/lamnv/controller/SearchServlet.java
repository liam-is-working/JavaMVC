/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamnv.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import person.PersonDAO;
import person.PersonDTO;

/**
 *
 * @author ACER
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"}, loadOnStartup = 1)
public class SearchServlet extends HttpServlet {
        //private final String SEARCH_PAGE = "search.html";
        private final String SEARCH_PAGE = "/search.jsp";
        private final String SEARCH_RESULT_PAGE = "/search.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        response.setContentType("text/html;charset=UTF-8");
        String searchValue = request.getParameter("txtSearchValue");
        String url = SEARCH_PAGE;
        try  {
//            //check
//            System.out.println(searchValue);
            if(searchValue.trim().length() > 0){
                PersonDAO dao = new PersonDAO();
                dao.searchLastName(searchValue);                
                List<PersonDTO> rs = dao.getPersonList();
                request.setAttribute("searchResult", rs);
                url = SEARCH_RESULT_PAGE;
            }//when search value has valid value
   
        }   catch (SQLException | ClassNotFoundException | NamingException ex ) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
