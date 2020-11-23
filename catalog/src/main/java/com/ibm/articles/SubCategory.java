package com.ibm.articles;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Cacheable
public class SubCategory extends PanacheEntity {

    public SubCategory() {}
   
    public String name;

    public String parent;
}
