/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cis
 */
public class CommonHelper {

    private String title = "";

    public static String test() {
        return "asdasd";
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getTitle() {
        if ((this.title).isEmpty()) {
            this.title = "test";
        }
        return this.title;
    }

    public String path(String object) {
        URL url;
        try {
            URL baseUrl = new URL("http://localhost:8080/resumecreator/");
            url = new URL(baseUrl, object);
            return url.toString();
        } catch (MalformedURLException ee) {
            System.out.println(ee);
        }
        return "";
    }

    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } 
        catch (java.security.NoSuchAlgorithmException e) {
        
        }
        return null;
    }
    public static String timestamp()
    {
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp sqltimestamp = new java.sql.Timestamp(today.getTime());
        String timestamp = sqltimestamp.toString();
        return timestamp;
    }
    
    public static Map<String, String> constants()
    {
        Map<String, String> variables = new java.util.HashMap<>();
        variables.put("SITE_NAME", "Resume Creator");
        variables.put("SITE_TITLE", "best title");
        return variables;
    }
    
    /**
     * This method takes File Object as parameter and returns the extension of the file.
     * @param name - a String name of file.
     * @return -String -the extension of the file.
     */
    public static String getFileExtension(String name) {
        if (name == null) {
            return null;
        }

        int extIndex = name.lastIndexOf(".");

        if (extIndex == -1) {
            return "";
        } else {
            return name.substring(extIndex + 1);
        }
    }
    
    public static boolean isAjax(javax.servlet.http.HttpServletRequest req)
    {
        boolean ajax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
        return ajax;
    }
    
    public static void json_encode(org.json.simple.JSONObject json_data, javax.servlet.http.HttpServletResponse res)
    {
        try
        {
            res.setContentType("application/json; charset=UTF-8");
            java.io.PrintWriter printout = res.getWriter();
            printout.print(json_data);
            printout.flush();
            //res.setContentType("application/json");
            //System.out.println(json_data.toString());
            //res.getWriter().write(json_data.toString());
        }
        catch(Exception ee)
        {
            
        }
    }


}
