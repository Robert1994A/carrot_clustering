package ro.inf.ucv.entity;

import javax.persistence.Id;

import org.bson.types.ObjectId;

public class Document {

	@Id
	private ObjectId id = null;

	private String title;

	private String contentUrl;

	private String language;

	public Document() {

	}

	public Document(ObjectId id, String title, String contentUrl, String language) {
		super();
		this.id = id;
		this.title = title;
		this.contentUrl = contentUrl;
		this.language = language;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
