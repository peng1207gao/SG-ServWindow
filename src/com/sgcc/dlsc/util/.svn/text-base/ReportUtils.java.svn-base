package com.sgcc.dlsc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReportUtils {
	static Properties propes;
    static{
    	 propes = new Properties();
    	try {
			InputStream fis =  ReportUtils.class.getClassLoader().getResourceAsStream("reports.properties");
				propes.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public ReportUtils(){
    	super();
    }
    
    public static String getTemplateId(String key ){
    	
		return propes.getProperty(key);
    }
}
