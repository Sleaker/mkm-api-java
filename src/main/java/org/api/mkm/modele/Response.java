package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Product> product;
	private List<Article> article;
	private List<LightArticle> lightArticles;
	private List<Link> links;
	private List<Wantslist> wantslist;
	private List<Game> game;
	private List<Expansion> expansion;

	private List<Product> single;
	private List<Order> order;
	private List<Thread> thread;
	private List<User> users;

	private Error errors;
	private User account;
	private String productsfile;
	private String mime;
	private String priceguidefile;
	private String stock;
	private List<Inserted> inserted;
	private List<Inserted> updatedArticles;

}
