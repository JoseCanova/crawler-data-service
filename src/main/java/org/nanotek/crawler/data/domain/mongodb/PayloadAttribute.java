package org.nanotek.crawler.data.domain.mongodb;

import java.util.List;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PayloadAttribute extends  Identity{

	private static final long serialVersionUID = -7692301069122718485L;

	protected String payloadAttribute;
	
	protected String payloadClassString;
	
	protected List<String> aliases;

	@PersistenceConstructor 
	public PayloadAttribute(String id , String payloadAttribute, String payloadClassString, List<String> aliases) {
		super(id);
		this.payloadAttribute = payloadAttribute;
		this.payloadClassString = payloadClassString;
		this.aliases = aliases;
	}

	public String getPayloadAttribute() {
		return payloadAttribute;
	}

	public void setPayloadAttribute(String payloadAttribute) {
		this.payloadAttribute = payloadAttribute;
	}

	public String getPayloadClassString() {
		return payloadClassString;
	}

	public void setPayloadClassString(String payloadClassString) {
		this.payloadClassString = payloadClassString;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	
	
}
