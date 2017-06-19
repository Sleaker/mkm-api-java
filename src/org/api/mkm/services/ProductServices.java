package org.api.mkm.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Expansion;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Localization;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class ProductServices {

	private AuthenticationServices auth;
	private XStream xstream;
	static final Logger logger = LogManager.getLogger(ProductServices.class.getName());

	
	public ProductServices() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class,"product", Product.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	 		xstream.addImplicitCollection(Product.class,"links",Link.class);
	 		xstream.addImplicitCollection(Product.class,"localization",Localization.class);
	 		xstream.addImplicitCollection(Product.class,"reprint",Expansion.class);
	 		xstream.addImplicitCollection(Response.class, "single",Product.class);
	 		xstream.addImplicitCollection(Response.class, "expansion",Expansion.class);
	 		
	 		xstream.ignoreUnknownElements();
	}
	
	public void exportPriceGuide(File f) throws IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		exportPriceGuide(f,null);
	}
	
	public void exportPriceGuide(File f,Integer idGame) throws IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/priceguide";
	
		if(idGame!=null)
			link="https://www.mkmapi.eu/ws/v2.0/priceguide?idGame="+idGame;
		
		logger.debug("LINK="+link);
	    
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		
		Response res = (Response)xstream.fromXML(xml);
		
		byte[] bytes = Base64.decodeBase64(res.getPriceguidefile());
		File temp =  new File("mkm_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
		temp.delete();
	}
	
	
	public void exportProductList(File f) throws IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/productlist";
		logger.debug("LINK="+link);
	    
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               
			               
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug("RESP="+xml);
		
		
		Response res = (Response)xstream.fromXML(xml);
		
		
		byte[] bytes = Base64.decodeBase64(res.getProductsfile());
		File temp =  new File("mkm_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
		temp.delete();
		
		
	}
	
	public List<Product> getProductByExpansion(Expansion e) throws InvalidKeyException, NoSuchAlgorithmException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/expansions/"+e.getIdExpansion()+"/singles";
		logger.debug("LINK="+link);
	    
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
        connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
        connection.connect() ;
        String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
        logger.debug("RESP="+xml);
     	Response res = (Response)xstream.fromXML(xml);
		return res.getSingle();
		
	}
	
	
	public List<Product> find(String name,Map<PRODUCT_ATTS,String> atts) throws InvalidKeyException, NoSuchAlgorithmException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		
		xstream.addImplicitCollection(Product.class,"name",Localization.class);
 		xstream.aliasField("expansion", Product.class, "expansionName");
 		
		String link = "https://www.mkmapi.eu/ws/v1.1/products/:name/:idGame/:idLanguage/:isExact";
		
		if(atts.containsKey(PRODUCT_ATTS.exact))
			link=link.replaceAll(":isExact", atts.get(PRODUCT_ATTS.exact));
		else
			link=link.replaceAll(":isExact", "false");
		
		if(atts.containsKey(PRODUCT_ATTS.idGame))
			link=link.replaceAll(":idGame", atts.get(PRODUCT_ATTS.idGame));
		else
			link=link.replaceAll(":idGame", "1");
		
		if(atts.containsKey(PRODUCT_ATTS.idLanguage))
			link=link.replaceAll(":idLanguage", atts.get(PRODUCT_ATTS.idLanguage));
		else
			link=link.replaceAll(":idLanguage", "1");
		
		link=link.replaceAll(":name", URLEncoder.encode(name,"UTF-8"));
		
		logger.debug("LINK="+link);
	    
		
		/*String link = "https://www.mkmapi.eu/ws/v2.0/products/find?search="+URLEncoder.encode(name,"UTF-8");
		if(atts.size()>0)
    	{
			link+="&";
    		List<String> paramStrings = new ArrayList<String>();
 	        for(PRODUCT_ATTS parameter:atts.keySet())
	             paramStrings.add(parameter + "=" + atts.get(parameter));
	        
 	        link+=Tools.join(paramStrings, "&");
    	}
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;*/
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
	    connection.addRequestProperty("Authorization", auth.generateOAuthSignature(link,"GET")) ;
        connection.connect();
    	String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
    	
    	//horrible, but too complicated to change for beanconverter
    	xml=xml.replaceAll("<countReprints></countReprints>", "<countReprints>0</countReprints>");
    	
    	logger.debug("RESP="+xml);
    	Response res = (Response)xstream.fromXML(xml);
		
    	
    	for(Product p : res.getProduct())
		{
			p.setEnName(p.getName().get(0).getProductName());
		}
	
		return res.getProduct();
	}
	
	public Product getById(String idProduct) throws InvalidKeyException, NoSuchAlgorithmException, IOException
	{
		xstream.aliasField("expansion", Product.class, "expansion"); //remove from V1.1 call
 		
    	String link = "https://www.mkmapi.eu/ws/v2.0/products/"+idProduct;
    	logger.debug("LINK="+link);
        
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug("RESP="+xml);
		Response res = (Response)xstream.fromXML(xml);
		return res.getProduct().get(0);
	}
	
	public void fusion(Product from, Product dest)
	{
		 try {
			 for (Map.Entry<String, Object> e : PropertyUtils.describe(from).entrySet()) 
			 	 if (e.getValue() != null && !e.getKey().equals("class"))
	             		PropertyUtils.setProperty(dest, e.getKey(), e.getValue());
		} catch (Exception e1) {
			
		}
	}
	
}
