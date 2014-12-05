package GUI_v2;

import java.io.IOException;
import java.io.PrintWriter;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // read form fields
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String k = request.getParameter("k");
         
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
        System.out.println("Number of results: " + k);
 
        // do some processing here...
         
        // get response writer
        PrintWriter writer = response.getWriter();
         
        // build HTML code
        String htmlResponse = "<html>";
        htmlResponse += "<h2>Latitude is: " + latitude + "<br/>";      
        htmlResponse += "Longitude is: " + longitude + "</h2>";
        htmlResponse += "Number of results is: " + k + "</h2>";
        htmlResponse += "</html>";
         
        // return response
        writer.println(htmlResponse);
    }
}