package com.ibm.catalog;

import java.math.BigDecimal;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.Collection;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.NamedNativeQuery;

@Entity
@Cacheable
@NamedNativeQuery(name="product.by.cat.or.sub",
		query="select distinct p.id,p.name,p.price,p.description,p.image from product as p,productcategory as pc, category as c where (c.id = ? or c.parent = ?) AND pc.categoryid = c.id AND pc.productid = p.id",
		resultClass=Product.class)
public class Product extends PanacheEntity {

    public Product() {}
   
	public BigDecimal price;

	public String name;
	
	public String description;
	
	public String image;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="productcategory",joinColumns={@JoinColumn(name="productid")},inverseJoinColumns={@JoinColumn(name="categoryid")})	
	public Collection<Category> categories;

	@JsonbTransient
	public Collection<Category> getCategories() {
		return categories;
	}
	
	@JsonbTransient
	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
}
