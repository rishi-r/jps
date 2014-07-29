/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import utils.CommonHelper;

/**
 *
 * @author cis
 */
@WebServlet(name = "CustomServlet")
public class CustomServlet extends HttpServlet implements GlobalConstants{

    private HttpServletRequest request;
    private HttpServletResponse response;
    protected JSONObject json_data = new JSONObject();
    protected CommonHelper ch = new CommonHelper();
    public Map<String, String> data = new java.util.HashMap<>();
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
            throws ServletException, IOException {
            this.request = request;
            this.response = response;
            String context = request.getContextPath();
            //System.out.println(context+" ");
            routelogin();
            //response.getWriter().flush();
            /*if ("GET".equals(request.getMethod()))
            {
                doGet(request, response);
            }
            if ("POST".equals(request.getMethod()))
            {
                doPost(request, response);
                
            }*/
    }
    
    protected void render(String view)
    {
        try
        {
            CommonHelper ch = new CommonHelper();
            System.out.println(ch.getTitle());
            request.setAttribute("centerpage",view);
            request.setAttribute("data",data);
            RequestDispatcher rd = request.getRequestDispatcher("/layouts/default.jsp");
            rd.forward(request, response);
        } 
        catch (Exception ee) {
            //System.out.println(ee);
        } 
    }
    
    protected void json(JSONObject json_data)
    {
        CommonHelper.json_encode(json_data, this.response);
    }
    
    protected boolean routelogin()
    {
        String servletPath = request.getServletPath();
        //System.out.println(servletPath);
        //return true;
        if ("/login".equals(servletPath)) {
            return true;
        }
        boolean isLoggedin = checklogin();
        if(!isLoggedin)
        {
            if(ch.isAjax(request))
            {
                json_data.put("error","logged_out");
                json(json_data);
            }
            else
            {
                try{
                    response.sendRedirect("login");
                }
                catch (Exception ee) {
                    System.out.println(ee);
                } 
            }
        }
        return true;
    }
    
    protected boolean checklogin()
    {
        javax.servlet.http.HttpSession session = request.getSession(false);
        if(session != null){
            if(null == session.getAttribute("user_id")){  
              // User is not logged in.  
                return false;
            }
            else
            {  
                return true;
              // User IS logged in.  
            }  
        }
        else
        {
            return false;
        }
    }
    protected void urls()
    {
        String scheme = request.getScheme();             // http
        String serverName = request.getServerName();     // hostname.com
        int serverPort = request.getServerPort();        // 80
        String contextPath = request.getContextPath();   // /mywebapp
        String servletPath = request.getServletPath();   // /servlet/MyServlet
        String pathInfo = request.getPathInfo();         // /a/b;c=123
        String queryString = request.getQueryString();          // d=789
        
        System.out.println(serverName);
        System.out.println(serverPort);
        System.out.println(contextPath);
        System.out.println(servletPath);
        System.out.println(pathInfo);
        System.out.println(queryString);
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
