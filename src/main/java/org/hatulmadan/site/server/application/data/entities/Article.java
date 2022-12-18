package org.hatulmadan.site.server.application.data.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name="article")
public class Article extends BasicEntity implements Serializable{
	@Column(length = 80)
	private String titleA;
	private String textA;
	private String srvFileLink;
	private String type; //это префикс ссылок на странице
	@Column
	private ZonedDateTime dateA;
	private String link;
	public Article() {
		
	}
	
	public Article(String titleA, String textA, String srvFileLink) {
		this.titleA = titleA;
		this.textA = textA;
		this.srvFileLink = srvFileLink;
	}
}
