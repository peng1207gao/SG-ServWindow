package com.sgcc.dlsc.contractManage.contracttemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

public class DownContractFiles 
{
	public void downloadLocal(String url,HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
        String fileName = "Operator.doc".toString(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream(url);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
        	OutputStream os= response.getOutputStream();
            while ((len = inStream.read(b)) > 0){
                os.write(b, 0, len);
            }
            os.flush();
            os.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        out.clear();  
//        out = pageContext.pushBody();  

    }


	public void downloadFile(String url, String filePath) throws IOException
	 {
	 URL theURL = new URL(url);
	 URLConnection con = theURL.openConnection();
	 String urlPath = con.getURL().getFile();
	 String fileFullName = urlPath.substring(urlPath.lastIndexOf("/")+1);
	 if(fileFullName !=null){
	 byte[] buffer = new byte[4*1024];
	 int read;
	 String path = filePath + "/" +fileFullName;
	 File fileFolder = new File(filePath);
	 if(!fileFolder.exists()){
	 fileFolder.mkdirs();
	 }
	 InputStream in = con.getInputStream();
	 FileOutputStream os = new FileOutputStream(path);
	 while((read = in.read(buffer)) > 0){
	 os.write(buffer, 0, read);
	 }
	 os.close();
	 in.close();
	 }
	 }
}
