package org.api.mkm.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Basket;
import org.api.mkm.modele.ShoppingCart;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class CartServices {

	private XStream xstream;
	private Logger logger = LogManager.getLogger(this.getClass());

	public CartServices() {
			xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Basket.class);
	 		xstream.addImplicitCollection(Basket.class,"shoppingCart",ShoppingCart.class);
	 		xstream.addImplicitCollection(ShoppingCart.class, "article", Article.class);
	 		xstream.ignoreUnknownElements();
	}
	
	public boolean addArticle(Article a) throws IOException
	{
		List<Article> list = new ArrayList<>();
		list.add(a);
		return addArticles(list);
	}
	
	
	public boolean addArticles(List<Article> articles) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/shoppingcart";
		StringBuilder temp = new StringBuilder();

		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request><action>add</action>");

		for(Article a : articles)
		{
			temp.append("<article>");
			temp.append("<idArticle>"+a.getIdArticle()+"</idArticle>");
			temp.append("<amount>"+a.getCount()+"</amount>");
			temp.append("</article>");
		}
		temp.append("</request>");
		
		logger.debug(MkmConstants.MKM_LOG_REQUEST+temp);
		
		Tools.postXMLResponse(link, "PUT", this.getClass(), temp.toString());
		
		return true;
	}
	
	
	public boolean empty() throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/shoppingcart";
		Tools.getXMLResponse(link, "DELETE", this.getClass());
		return true;
	}
	
	
	public boolean removeArticles(List<Article> articles) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/shoppingcart";
		StringBuilder temp = new StringBuilder();

		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request><action>remove</action>");

		for(Article a : articles)
		{
			temp.append("<article>");
			temp.append("<idArticle>"+a.getIdArticle()+"</idArticle>");
			temp.append("<amount>"+a.getCount()+"</amount>");
			temp.append("</article>");
		}
		temp.append("</request>");
		Tools.postXMLResponse(link, "PUT", this.getClass(), temp.toString());
		
		
		return true;
	}
	
	public Basket getBasket() throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/shoppingcart";
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
		return (Basket)xstream.fromXML(xml);
	}
	
	
	
	
	
	
}
