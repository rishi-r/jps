package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.NewUpload;

/**
 *
 * @author cis
 */
public class LoginController extends CustomServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
        try {
            data.put("title","Login panel");
            render("home/login");
        } catch (Exception ee) {
            System.out.println(ee);
        } 
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
        if(ch.isAjax(req))
        {
            doAjax(req, res);
        }
        else
        {
            processPost(req, res);
        }
    }
    
    protected void processPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            NewUpload nfp = new NewUpload();
            nfp.saveDir = "newdir";
            //boolean result = fp.process(req);
            nfp.doProcess(req);
            
            if(nfp.error == 0)
            {
                System.out.println("passed");
            }
            else
            {
                System.out.println("error: "+nfp.error);
            }
            req.setAttribute("centerpage","home/index");
            render("home/index");
            
        } catch (Exception ee) {
            System.out.println(ee);
        } 
    }
    
    public void doAjax(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        try {
            json_data.put("city", "Mumbai");
            json_data.put("city", "Indore");
            json(json_data);
        } catch (Exception ee) {
            System.out.println(ee);
        } 
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
