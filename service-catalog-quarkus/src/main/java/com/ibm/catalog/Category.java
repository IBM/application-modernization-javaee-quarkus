package com.ibm.catalog;

import java.util.Collection;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.json.bind.annotation.JsonbTransient;

@Entity
@Cacheable
@NamedQuery(name="top.level.category",query="select c from Category c where c.parent IS NULL")
public class Category extends PanacheEntity {

    public Category() {}
   
    public String name;

    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent")
    public Category parent;

    @OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	private Collection<Category> subCategories;

    public Collection<Category> getSubCategories() {
		return subCategories;
	}
	
	public void setSubCategories(Collection<Category> subCategories) {
		this.subCategories = subCategories;
    }
    
    @JsonbTransient
	public Category getParent() {
		return parent;
	}
	
	public void setParent(Category parent) {
		this.parent = parent;
	}
}
