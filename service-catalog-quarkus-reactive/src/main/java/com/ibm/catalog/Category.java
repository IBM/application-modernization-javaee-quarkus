package com.ibm.catalog;

import java.util.Collection;

import javax.json.bind.annotation.JsonbTransient;

public class Category {

    public Category() {}
   
	public String name;
	
	public Long id;

	@JsonbTransient
	public Long parent;

	private Collection<Category> subCategories;

	public Collection<Category> getSubCategories() {
		return subCategories;
	}
	
	public void setSubCategories(Collection<Category> subCategories) {
		this.subCategories = subCategories;
    }
}
