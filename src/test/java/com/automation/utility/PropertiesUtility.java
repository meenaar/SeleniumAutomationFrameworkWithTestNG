package com.automation.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtility {

	public  static String readDataFromPropertyFile(String path,String key) {
		//need property file
		// what property (key)
		
		File file = new File(path);
		FileInputStream fis=null;
		Properties pro=new Properties();
		
		String data=null;
		try {
			//reading a file
			fis=new FileInputStream(file);
			//loading the file in properties object
			pro.load(fis);
			data=pro.getProperty(key);
			fis.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("error in the file path.. please try again");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error in loading the file in properties object");
			e.printStackTrace();
		}		
		return data;
	}
	
	//Method to write the properties to properties file
	
	public static void writePropertiesToFile (String path, String key, String value) throws IOException {
		//need what property to write
		//need property file
		
		File file = new File(path);
		
		FileInputStream fis=new FileInputStream(file);
		
		FileOutputStream fos=null;
		Properties pro=new Properties();
		
		pro.load(fis);
		try {
			
			//loading the file in properties object
	
			
			fos = new FileOutputStream(file,true);
			
			pro.setProperty(key, value);
			
			pro.store(fos, "Information");
			
			fos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method to delete the properties for the given key from properties file
	
	public static void deletePropertiesFromFile (String path, String keyToRemove) {
		//need what property key to delete
		//need property file
		
		File file = new File(path);
		FileInputStream fis=null;
		FileOutputStream fos = null;
		Properties pro=new Properties();
		int flag=0;
		try {
			fis = new FileInputStream(file);
	
			pro.load(fis);				
		
			if (pro.containsKey(keyToRemove)) {
									
					//remove the properties from file when key is equal to keyToRemove
					pro.remove(keyToRemove);
					fos = new FileOutputStream(file);
					
					pro.store(fos, "deleted "+keyToRemove);
					flag=1;
			} 					
			if (flag==0)
				System.out.println(keyToRemove+" key not found in the file");		
			else System.out.println(keyToRemove+" key value pair found in the file");
			
			fos.close();	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		 
	}
	
	
	//Method to get all property names from given properties file
	
	public static void getAllPropertyNamesFromFile (String path) {
	
		File file = new File(path);
		FileInputStream fis=null;
		Properties pro=new Properties();
		Enumeration<?> en;
		
		try {
			fis = new FileInputStream(file);
	
			pro.load(fis);				
		
			//Returns the enumeration of all the keys(distinct)in the properties file  
			 en = pro.propertyNames();

		      // print the enumeration elements
		      while(en.hasMoreElements()) {
		         System.out.println("" + en.nextElement()); 	  
		      }
		    fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
	}	
}
