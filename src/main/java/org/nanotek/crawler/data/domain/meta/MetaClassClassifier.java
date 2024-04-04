package org.nanotek.crawler.data.domain.meta;

import java.util.ArrayList;
import java.util.List;
import org.nanotek.Classifier;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MetaClassClassifier implements Classifier<MetaClass> {

	@JsonIgnore
	protected boolean hasPrimaryKey;

	@JsonIgnore
	protected List<MetaRelationClass> metaRelationsClasses;
	
	public MetaClassClassifier() {
		super();
		metaRelationsClasses = new ArrayList<MetaRelationClass>();
	}

	public boolean isHasPrimaryKey() {
		return hasPrimaryKey;
	}

	public void setHasPrimaryKey(boolean hasPrimaryKey) {
		this.hasPrimaryKey = hasPrimaryKey;
	}

	public List<MetaRelationClass> getMetaRelationsClasses() {
		return metaRelationsClasses;
	}

	public void setMetaRelationsClasses(List<MetaRelationClass> metaRelationsClasses) {
		this.metaRelationsClasses = metaRelationsClasses;
	}
	
	


}
