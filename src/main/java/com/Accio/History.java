package com.Accio;


import com.sun.net.httpserver.HttpPrincipal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/History") // adding route
public class History extends HttpServlet {
    protected void  doGet(HttpServletRequest request , HttpServletResponse response){
        Connection connection = DatabaseConnection.getConnection();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery("Select * from history;");
            ArrayList<HistoryResult> results = new ArrayList<HistoryResult>();
            while(resultSet.next()){
                HistoryResult historyResult = new HistoryResult();
                historyResult.setKeyword(resultSet.getString("keyword"));
                historyResult.setLink(resultSet.getString("Link"));
                results.add(historyResult);
            }

            request.setAttribute("results", results);
            request.getRequestDispatcher("history.jsp").forward(request, response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        catch (ServletException e){
            throw new RuntimeException(e);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
