package org.api.mkm.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Properties;

import org.api.mkm.exceptions.MkmException;
import org.api.mkm.services.AuthenticationServices;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MkmAPIConfig {

	private static MkmAPIConfig instance;
	private int maxCall=0;
	private int countCall;

	AuthenticationServices auth;
	
	
	public void init( String accessSecret ,String accessToken ,String appSecret,String appToken) throws MkmException
	{
		auth=new AuthenticationServices(accessSecret, accessToken, appSecret, appToken);
	}
	
	public void init(Properties magicCardMaketPropFile) throws MkmException
	{
		init(magicCardMaketPropFile.getProperty("APP_ACCESS_TOKEN_SECRET"),
										magicCardMaketPropFile.getProperty("APP_ACCESS_TOKEN"),
										magicCardMaketPropFile.getProperty("APP_SECRET"),
										magicCardMaketPropFile.getProperty("APP_TOKEN"));
	}
	
	
	public void init(File magicCardMaketPropFile) throws IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream(magicCardMaketPropFile));
		init(p);
	}
	
	
	public static MkmAPIConfig getInstance()
	{
		if(instance==null)
			instance=new MkmAPIConfig();
		return instance;
	}

		
	
	public AuthenticationServices getAuthenticator() {
		return auth;
	}
	
	public void updateCount(HttpURLConnection connection) 
	{
       String limit = connection.getHeaderField("X-Request-Limit-Max");
       String count = connection.getHeaderField("X-Request-Limit-Count");

       if(maxCall==0 && limit!=null)
    	      maxCall=Integer.parseInt(limit);
       
       
       if(count!=null)
    	   countCall=Integer.parseInt(count);
       
       
		   log.debug("call : " + countCall+ "/" + maxCall);
       
 	}
	
	public int getCountCall() {
		return countCall;
	}
	
	public int getMaxCall() {
		return maxCall;
	}
}
