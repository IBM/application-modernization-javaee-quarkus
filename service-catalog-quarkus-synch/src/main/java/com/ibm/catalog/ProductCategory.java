package com.ibm.catalog;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Cacheable
public class ProductCategory extends PanacheEntity {

    public ProductCategory() {}
   
	public int productid;
	
	public int categoryid;	
}
