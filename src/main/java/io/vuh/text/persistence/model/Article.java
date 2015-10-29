package io.vuh.text.persistence.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@Entity
@Table(name="Article")
@NamedQueries({@NamedQuery(name="Article.getAllArticles",query="SELECT a FROM Article a")})
public class Article implements Serializable {
	
	private static final long serialVersionUID = 1308923780846174471L;

	@Column(name="id")
	@Id
	private String id;
	
	@Column(name="title")
    private String title; 
	
	@Column(name="source")
    private String source;
	
	@Column(name="text")
    private String text;
	
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
    private Date date;
	
	@Column(name="url")
    private String url;
    
	public String getId() {
		return id;
	}
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String value) {
		this.source = value;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String value) {
		this.text = value;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date value) {
		this.date = value;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String value) {
		this.url = value;
	}
    
}
