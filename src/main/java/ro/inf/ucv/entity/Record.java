package ro.inf.ucv.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Record implements Serializable {

	private static final long serialVersionUID = 9093304836112847216L;

	@Id
	private ObjectId id;

	@NotNull
	private String record_id;

	@NotNull
	private String record_full;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getRecord_full() {
		return record_full;
	}

	public void setRecord_full(String record_full) {
		this.record_full = record_full;
	}

}
