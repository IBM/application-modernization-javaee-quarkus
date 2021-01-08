package org.pwte.example.domain;

import com.ibm.cardinal.util.KluInterface;

import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonProperty;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;


@Entity
@NamedQuery(name="top.level.category",query="select c from Category c where c.parent IS NULL")
public class Category implements KluInterface, Serializable {
    //Cardinal generated attributes
    @Transient
    private String klu__referenceID = "";

    //Cardinal generated method
    public String getKlu__referenceID() {
        return klu__referenceID;
    }

    //Cardinal generated method
    public void setKlu__referenceID(String refID) {
        klu__referenceID = refID;
    }



	
	private static final long serialVersionUID = -2872694133550658771L;

	@Id
	@Column(name="CAT_ID")
	private int id;
	
	@Column(name="CAT_NAME")
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PARENT_CAT")
	private Category parent;
	
	
	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	private Collection<Category> subCategories;
	
	/*
	@ManyToMany(mappedBy="categories",fetch=FetchType.LAZY)
	private Collection<Product> products;
	*/

	@JsonbProperty(value="id")
	public int getId() {
		return id;
	}
	
	public void setId(int categoryID) {
		this.id = categoryID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	/*
	@JsonIgnore
	public Category getParent() {
		return parent;
	}
	
	@JsonIgnore
	public void setParent(Category parent) {
		this.parent = parent;
	}
	*/
	
	
	public Collection<Category> getSubCategories() {
		return subCategories;
	}
	
	public void setSubCategories(Collection<Category> subCategories) {
		this.subCategories = subCategories;
	}
	
	
	/*
	@JsonIgnore
	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	*/
}

