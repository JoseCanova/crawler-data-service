package org.nanotek.crawler.data.domain.meta;

import java.util.List;

public interface IClass {

	String getClassName();

	void setClassName(String className);

	String getTableName();

	void setTableName(String tableName);

	List<MetaDataAttribute> getMetaAttributes();

	boolean addMetaAttribute(MetaDataAttribute attr);


}