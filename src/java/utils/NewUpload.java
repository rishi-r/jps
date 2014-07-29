/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author cis
 */
public class NewUpload {

    private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
    public String saveDir = "uploadFiles";
    public long maxFileSize = 2;   // 2MB
    private String savePath;
    public int error = 0;  // 0 = no error, 1 = max limit exceed, 
    private String filename;
    private Iterator<FileItem> fileItemsIterator;
    public void doProcess(HttpServletRequest request) {
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        savePath = appPath + File.separator + saveDir;
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File fileSaveDir = new File(savePath);
        fileFactory.setRepository(fileSaveDir);

        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        this.uploader = new ServletFileUpload(fileFactory);

        try {
            List<FileItem> fileItemsList = uploader.parseRequest(request);
            fileItemsIterator = fileItemsList.iterator();
            
            fileValidate();
            if(error == 0)
            {
                fileItemsIterator = fileItemsList.iterator();
                upload();
            }
            
        } catch (FileUploadException e) {
            System.out.println("Exception in uploading file.");
        } catch (Exception e) {
            System.out.println("Exception in uploading file.");
        }
        
    }
    
    
    private void fileValidate()
    {
        maxFileSize = maxFileSize * 1024 * 1024;  // convert into MB size;
        while (fileItemsIterator.hasNext()) {
            FileItem fileItem = fileItemsIterator.next();
            if(maxFileSize < fileItem.getSize())
            {
                error = 1;
                break;
            }
           /* System.out.println("FieldName=" + fileItem.getFieldName());
            System.out.println("FileName=" + fileItem.getName());
            System.out.println("ContentType=" + fileItem.getContentType());
            System.out.println("Size in bytes=" + fileItem.getSize());*/
        }
    }
    
    private void upload()
    {
        try
        {
            while (fileItemsIterator.hasNext()) {
                
                FileItem fileItem = fileItemsIterator.next();
                newFileName(fileItem.getName());
                File file = new File(savePath + File.separator + filename);
                System.out.println("Absolute Path at server=" + file.getAbsolutePath());
                fileItem.write(file);
            }
        } catch (FileUploadException e) {
            System.out.println("Exception in uploading file." + e);
        } catch (Exception e) {
            System.out.println("Exception in uploading file. "+e);
        }
    }
    
    private void newFileName(String name)
    {
        String fileExt = CommonHelper.getFileExtension(name);
        String timestamp = CommonHelper.timestamp();
        filename =  CommonHelper.md5(timestamp)+"."+fileExt;
    }

}
