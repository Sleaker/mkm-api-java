package org.api.mkm.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.User;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class AuthenticationServices {

	
	private String appToken;
	private String appSecret;
	private String accessToken;
	private String accessSecret;
	private XStream xstream;
	
	static final Logger logger = LogManager.getLogger(AuthenticationServices.class.getName());

	public AuthenticationServices(String accessSecret,String accessToken,String appSecret,String appToken) {
		this.accessSecret=accessSecret;
		this.accessToken=accessToken;
		this.appSecret=appSecret;
		this.appToken=appToken;
	}
	
	public User getAuthenticatedUser() throws IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		xstream = new XStream(new StaxDriver());
		XStream.setupDefaultSecurity(xstream);
 		xstream.addPermission(AnyTypePermission.ANY);
 		xstream.alias("response", Response.class);
 		xstream.ignoreUnknownElements();
 		xstream.addImplicitCollection(Response.class,"links",Link.class);
 		
		String link="https://www.mkmapi.eu/ws/v2.0/account";
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", generateOAuthSignature(link,"GET")) ;
			               connection.connect() ;
			               
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		
		Response res = (Response)xstream.fromXML(xml);
		return res.getAccount();
	}
	
	
	
	private Map<String,String> parseQueryString(String query)
	 {
	        Map<String,String> queryParameters = new TreeMap<String, String>();
	        
	        String[] querySegments = query.split("&");
	        for (String segment : querySegments)
	        {
	            String[] parts = segment.split("=");
	            if (parts.length > 0)
	            {
	                String key = parts[0].replaceAll("\\?", " ").trim();
	                String val = parts[1].trim();
	                queryParameters.put(key, val);
	            }
	        }
	        return queryParameters;
	 }
	 
	 
	    
	    
	    public String generateOAuthSignature(String url,String method) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
	    	
	    	 Map<String,String> headerParams = new HashMap<String,String>();
	         Map<String,String> encodedParams = new TreeMap<String, String>();
	         int index = url.indexOf("?");
	         String signatureMethod = "HMAC-SHA1";
	         String version = "1.0";
	         String encode="UTF-8";
	         String nonce="" + System.currentTimeMillis();
	         String timestamp=""+ (System.currentTimeMillis()/1000);
	         String baseUri = (index>0?url.substring(0,index):url);
	         String signatureKey = URLEncoder.encode(appSecret,encode) + "&" + URLEncoder.encode(accessSecret,encode);
	         
	         headerParams = new TreeMap<String, String>();
	         headerParams.put("oauth_consumer_key", appToken);
	         headerParams.put("oauth_token", accessToken);
	         headerParams.put("oauth_nonce", nonce);
	         headerParams.put("oauth_timestamp", timestamp);
	         headerParams.put("oauth_signature_method", signatureMethod);
	         headerParams.put("oauth_version", version);
	         headerParams.put("realm", baseUri);
	         
	         
	         String baseString = method.toUpperCase()
	                 + "&"
	                 + URLEncoder.encode(baseUri, encode)
	                 + "&";
	         
	         if (index > 0)
	         {
	             String urlParams = url.substring(index+1);
	             Map<String,String> args = parseQueryString(urlParams);

	             for (String k : args.keySet())
	            	 headerParams.put(k, args.get(k));
	         }
	         
	         for (String k : headerParams.keySet())
	             if (false == k.equalsIgnoreCase("realm"))
	                 encodedParams.put(URLEncoder.encode(k,encode), URLEncoder.encode(headerParams.get(k),encode));
	         
	         List<String> paramStrings = new ArrayList<String>();
	        
	         for(String parameter:encodedParams.keySet())
	         {
	        	 paramStrings.add(parameter + "=" + encodedParams.get(parameter));
	         }
	         
	         String paramString = URLEncoder.encode(Tools.join(paramStrings, "&"),encode);
	         
	         baseString += paramString;
	         
	         logger.debug("baseString = " + baseString);
		     logger.debug("paramString = " + paramString);
	    	     
	         
	      
	         
	         Mac mac = Mac.getInstance("HmacSHA1");
	         SecretKeySpec secret = new SecretKeySpec(signatureKey.getBytes(), mac.getAlgorithm());
	         mac.init(secret);
	         byte[] digest = mac.doFinal(baseString.getBytes());
	         String oAuthSignature = DatatypeConverter.printBase64Binary(digest);    
	         headerParams.put("oauth_signature", oAuthSignature);
	         
	         List<String> headerParamStrings = new ArrayList<String>();
	    
	         for(String parameter:headerParams.keySet())
	             headerParamStrings.add(parameter + "=\"" + headerParams.get(parameter) + "\"");
	         
	         String authHeader = "OAuth " + Tools.join(headerParamStrings,", ");
	     	return authHeader;
	    }

	    
	    
	   


	
}
