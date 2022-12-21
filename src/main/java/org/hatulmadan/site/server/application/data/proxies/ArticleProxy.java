package org.hatulmadan.site.server.application.data.proxies;

import java.util.List;

import org.hatulmadan.site.server.application.data.entities.Article;
import org.hatulmadan.site.server.application.data.entities.security.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

   
public class ArticleProxy {
	private Long id;
	private String titleA;
	private String textA;
	private String srvFileLink;
	private byte[] image;
	private String type;
	private String link;
	
	public ArticleProxy(String titleA, String textA) {
		this.titleA = titleA;
		this.textA = textA;

	};
	public Article createArticle() {
		Article a=	 new Article(titleA, textA, srvFileLink);
		if (id!=null)a.setId(id);
		if (link!=null) a.setLink(link);
		if (type!=null)a.setType(type);
		return a;
	}
}	
	