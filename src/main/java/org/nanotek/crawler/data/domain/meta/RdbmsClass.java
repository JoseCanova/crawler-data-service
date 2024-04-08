package org.nanotek.crawler.data.domain.meta;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import schemacrawler.schema.Table;

public class RdbmsClass {

	@JsonIgnore
	protected Table table;
	
	@JsonIgnore
	private List<Table> referencedTables;
	
	public RdbmsClass() {
		super();
		this.referencedTables=new ArrayList<>();
	}
	
	public RdbmsClass(Table table) {
		super();
		this.table = table;
		this.referencedTables=new ArrayList<>();
	}
	
	
	public void addReferencedTable(Table referencedTable) {
		this.referencedTables.add(referencedTable);
	}

	public List<Table> getReferencedTables() {
		return referencedTables;
	}
	
	public void setTable(Table t) {
		this.table = t;
	}

	public Table getTable() {
		return table;
	}

}
