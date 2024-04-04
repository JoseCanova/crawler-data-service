package org.nanotek.crawler.data.domain.meta;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import schemacrawler.schema.Table;

/*
 * TODO: refactor code of definition for IdPrimaryKey classification.
 */
@JsonInclude(value = Include.NON_NULL)
public class MetaClass implements IClass {

	@JsonProperty("tableName")
	protected String tableName;
	
	@JsonProperty("className")
	protected String className; 
	
	protected List<MetaDataAttribute> metaAttributes = new ArrayList<>();

	protected MetaClassClassifier classifier;
	
	/*
	 * @JsonIgnore private boolean hasPrimraryKey;
	 * 
	 * @JsonIgnore protected List<MetaRelationClass> metaRelationsClasses;
	 */
	protected MetaIdentity identity;
	
	@JsonIgnore
	protected RdbmsClass rdbmsClass;
	
	public MetaClass() {
		super();
		classifier = new MetaClassClassifier ();
		this.rdbmsClass = new RdbmsClass();
	}

	public MetaClass(String tableName, String className, 
			List<MetaDataAttribute> metaAttributes) {
		super();
		this.tableName = tableName;
		this.className = className;
		this.metaAttributes = metaAttributes;
		this.rdbmsClass = new RdbmsClass();
		classifier = new MetaClassClassifier ();
	}

	
	
	public MetaClass(String tableName, String className, 
					Table table) {
		super();
		this.tableName = tableName;
		this.className = className;
		this.rdbmsClass = new RdbmsClass(table);
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public List<MetaDataAttribute> getMetaAttributes() {
		return metaAttributes;
	}

	@Override
	public boolean  addMetaAttribute(MetaDataAttribute attr) {
		return metaAttributes.add(attr);
	}

	public boolean isHasPrimaryKey() {
		return  metaAttributes !=null && metaAttributes.stream().filter(a -> a.isId()).count() > 0;
	}
	
	public RdbmsClass getRdbmsClass() {
		return rdbmsClass;
	}

	public void setRdbmsClass(RdbmsClass rdbmsClass) {
		this.rdbmsClass = rdbmsClass;
	}

	public void addMetaRelationClass(MetaRelationClass mrc) {
		this.classifier.addMetaRelationClass(mrc);
		
	}

	public List<MetaRelationClass> getMetaRelationsClasses() {
		return this.classifier.getMetaRelationsClasses();
	}

	public MetaIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(MetaIdentity identity) {
		this.identity = identity;
	}

	public void setMetaAttributes(List<MetaDataAttribute> metaAttributes) {
		this.metaAttributes = metaAttributes;
	}

	public void setMetaRelationsClasses(List<MetaRelationClass> metaRelationsClasses) {
		this.classifier.setMetaRelationsClasses(metaRelationsClasses);
	}
	
}
