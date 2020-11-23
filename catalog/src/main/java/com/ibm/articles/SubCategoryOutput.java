package com.ibm.articles;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;


public class SubCategoryOutput {

    public SubCategoryOutput() {}
   
    public String name;

    public int id;
}
