/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author cis
 */


public class FileUploadOld {
    public String saveDir = "uploadFiles";
    private static final long serialVersionUID = 2857847752169838915L;
    int BUFFER_LENGTH = 4096;
    public boolean process(HttpServletRequest request) throws IOException
    {
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + saveDir;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
        }
        try
        {
            for (Part part : request.getParts()) {
                    InputStream is = request.getPart(part.getName()).getInputStream();
                    String fileName = extractFileName(part);
                    File file = new File(savePath+File.separator+fileName);
                    FileOutputStream os = new FileOutputStream(file);
                    System.out.println(file);
                    byte[] bytes = new byte[BUFFER_LENGTH];
                    int read = 0;
                    while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    os.flush();
                    is.close();
                    os.close();
                    System.out.println(fileName + " was uploaded to " + saveDir);
            }
            return true;
        }
        catch(IOException | ServletException ee)
        {
            System.out.println(ee);
            return false;
        }
		
    }
    
    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

}
