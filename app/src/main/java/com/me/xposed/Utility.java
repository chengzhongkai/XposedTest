package com.me.xposed;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Utility {
	static String dir="/data/data/com.me.xposed/";
	static void run(String cmd){
	    Process p;
		try {
			p = Runtime.getRuntime().exec("su");
			
			
	        DataOutputStream os = new DataOutputStream(p.getOutputStream());   
	        DataInputStream is = new DataInputStream(p.getInputStream());  
	                      
	        os.writeBytes(cmd+" \n");  
	        os.writeBytes("exit \n");  
	        os.flush();  
	          
	        p.waitFor();  
	          
	        byte[] buffer = new byte[is.available()];  
	        is.read(buffer);  
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
	}
	
	static void output(String name,String str){
	    Process p;
		try {
			p = Runtime.getRuntime().exec("su");
			
			
	        DataOutputStream os = new DataOutputStream(p.getOutputStream());   
	        DataInputStream is = new DataInputStream(p.getInputStream());  
	                      
	        os.writeBytes("echo "+str +" > "+dir+name+" \n");  
	        os.writeBytes("chmod 666 "+dir+name+" \n");
	        os.writeBytes("exit \n");  
	        os.flush();  
	          
	        p.waitFor();  
	          
	        byte[] buffer = new byte[is.available()];  
	        is.read(buffer);   
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
	}
	
	static String input(String name){
	    Process p;
	    String s = "";
		try {
			p = Runtime.getRuntime().exec("su");
			
			
	        DataOutputStream os = new DataOutputStream(p.getOutputStream());   
	        DataInputStream is = new DataInputStream(p.getInputStream());  
	                      
	        os.writeBytes("cat " + dir + name + " \n");
	        
	        os.writeBytes("exit \n");  
	        os.flush();  
	          
	        p.waitFor();  
	          
	        //byte[] buffer = new byte[is.available()];
	        //is.read(buffer);
		    //s = new String(buffer);


			byte[] bytes = new byte[1000];
			StringBuilder x = new StringBuilder();
			int numRead = 0;
			while ((numRead = is.read(bytes)) >= 0) {
				x.append(new String(bytes, 0, numRead));
			}
			s=x.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
		return s;
	}
}
