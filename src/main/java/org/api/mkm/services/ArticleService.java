package org.api.mkm.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.User;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class ArticleService {
	private Logger logger = LogManager.getLogger(this.getClass());
	private AuthenticationServices auth;
	private XStream xstream;
	
	public ArticleService() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class,"article", Article.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	 		xstream.ignoreUnknownElements();
	}
	
	public List<Article> find(User u,Map<ARTICLES_ATT,String> atts) throws IOException 
	{
		String link = MkmConstants.MKM_API_URL+"/users/"+u.getUsername()+"/articles";
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		if(atts!=null && atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<>();
	 	        for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
		
		 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
         connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
         connection.connect() ;
         MkmAPIConfig.getInstance().updateCount(connection);
     	boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
     	if(!ret)
     		throw new MkmNetworkException(connection.getResponseCode());
         
         
         String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
         logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
  	   
         Response res = (Response)xstream.fromXML(xml);
		
     	if(isEmpty(res.getArticle()))
    		return new ArrayList<>();
    
         
         
		return res.getArticle();
	}
	
	
	private boolean isEmpty(List<Article> article) {
		
		return (article.get(0).getIdArticle()==0);
	}

	public List<Article> find(Product p,Map<ARTICLES_ATT,String> atts) throws IOException 
	{
		String link = MkmConstants.MKM_API_URL+"/articles/"+p.getIdProduct();
    	logger.debug(MkmConstants.MKM_LOG_LINK+link);
    	
    	if(atts!=null && atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<>();
	    		 for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
    	logger.debug(MkmConstants.MKM_LOG_LINK+link);
    	
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
			         	               
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
		  	  
		Response res = (Response)xstream.fromXML(xml);
		
		for(Article a : res.getArticle())
			a.setProduct(p);
		
		return res.getArticle();
	}
	
}
